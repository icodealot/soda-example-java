package com.icodealot.noaa.model;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@JsonIgnoreProperties({ "@context", "relativeLocation" })
public class PointResponse extends NoaaResponseBase {
    // See:
    //   https://www.weather.gov/documentation/services-web-api#/default/point
    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type;

    private String geometry;
    private String cwa;
    private String forecastOffice;
    private String gridId;
    private int gridX;
    private int gridY;
    private String forecast;
    private String forecastHourly;
    private String observationStations;
    private String forecastGridData;
    private String forecastZone;
    private String county;
    private String fireWeatherZone;
    private String timeZone;
    private String radarStation;
}