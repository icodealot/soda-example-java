package com.icodealot.noaa.model;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@JsonIgnoreProperties({ "@context", "updated" })
public class ForecastResponse extends NoaaResponseBase {
    private String geometry;
    private String units;
    private String forecastGenerator;
    private String generatedAt;
    private String updateTime;
    private String validTimes;
    private Elevation elevation;
    private Period[] periods;
}
