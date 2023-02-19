package com.icodealot.noaa.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantitativeValue {
    private double value;
    private double maxValue;
    private double minValue;
    private String unitCode;
    private String qualityControl;
}
