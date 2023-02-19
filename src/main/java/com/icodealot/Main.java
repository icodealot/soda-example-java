package com.icodealot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.icodealot.noaa.model.ForecastResponse;
import com.icodealot.noaa.model.PointResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

public class Main {
    // Setup:
    //    1. Create a new JSON collection called 'points' in your ADB schema
    //    2. Ensure the schema/user has REST access to .../soda/latest/points
    //    3. Update the following two String variables with your own details:
    //        - SODA_ENDPOINT_POINTS: is the endpoint for the JSON collection
    //        - SODA_AUTH_HEADER: is your user:pass encoded as "Basic Base64-String")
    private static final String SODA_ENDPOINT_POINTS =
            "https://your.adb.us-phoenix-1.oraclecloudapps.com/ords/yourschema/soda/latest/points";
    private static final String SODA_AUTH_HEADER = "Basic ExampleAuthHeaderBase64String==";

    private static final String ACCEPT_LD_JSON = "application/ld+json";
    private static final String CHICAGO = "41.837,-87.685";
    private static final Client CLIENT = ClientBuilder.newClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static void main(String[] args) throws Exception {

        PointResponse point = CLIENT.target("https://api.weather.gov")
                .path("points")
                .path(CHICAGO)
                .request(ACCEPT_LD_JSON)
                .get(PointResponse.class);

        ForecastResponse forecast = CLIENT.target(point.getForecast())
                .request(ACCEPT_LD_JSON)
                .header("feature-flags", "forecast_temperature_qv, forecast_wind_speed_qv")
                .get(ForecastResponse.class);

        // iterate over forecast periods and do something interesting...
        // for (var period : forecast.getPeriods()) { ... }

        URI sodaPointUri = postPointToSoda(point);
        System.out.println("Point stored in SODA: \n\t"
                + (sodaPointUri != null ? sodaPointUri.toString() : null));

        PointResponse pointResponse = getPointFromSoda(sodaPointUri);
        System.out.println("Point retrieved from SODA: \n\t"
                + MAPPER.writeValueAsString(pointResponse));

        // comment this out if you want to keep the document in SODA for inspection
        deletePointFromSoda(sodaPointUri);
        System.out.println("Point deleted from SODA: \n\t"
                + sodaPointUri.toString());
    }

    private static URI postPointToSoda(PointResponse point) {
        Response response = CLIENT.target(SODA_ENDPOINT_POINTS)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", SODA_AUTH_HEADER)
                .post(Entity.entity(point, MediaType.APPLICATION_JSON));

        if (response != null && response.getStatus() != 201) {
            throw new RuntimeException("unexpected soda response: " + response.toString());
        }

        return response != null
                ? response.getLocation()
                : null;
    }

    private static PointResponse getPointFromSoda(URI sodaUri) {
        if (sodaUri == null) {
            throw new RuntimeException("cannot get unspecified point");
        }

        return CLIENT.target(sodaUri)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", SODA_AUTH_HEADER)
                .get(PointResponse.class);
    }

    private static void deletePointFromSoda(URI sodaUri) {
        if (sodaUri == null) {
            throw new RuntimeException("cannot delete unspecified point");
        }

        Response response = CLIENT.target(sodaUri)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", SODA_AUTH_HEADER)
                .delete();

        if (response != null && response.getStatus() != 200) {
            throw new RuntimeException("unexpected soda response: " + response.toString());
        }
    }
}