package com.icodealot.noaa.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Elevation {
    private int value;
    private int maxValue;
    private int minValue;
    private String unitCode;
    private String qualityControl;
}