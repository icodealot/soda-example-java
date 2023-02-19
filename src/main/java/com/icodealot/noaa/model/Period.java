package com.icodealot.noaa.model;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@JsonIgnoreProperties({ "temperatureUnit", "icon" })
public class Period {
    private int number;
    private String name;
    private String startTime;
    private String endTime;

    @JsonProperty("isDaytime")
    private boolean daytime;

    private String temperatureTrend;
    private QuantitativeValue probabilityOfPrecipitation;
    private QuantitativeValue dewpoint;
    private QuantitativeValue relativeHumidity;
    private QuantitativeValue temperature;
    private QuantitativeValue windSpeed;
    private QuantitativeValue windGust;
    private String windDirection;
    private String shortForecast;
    private String detailedForecast;
}
