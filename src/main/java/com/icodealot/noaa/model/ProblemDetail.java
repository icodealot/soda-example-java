package com.icodealot.noaa.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemDetail {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
    private String correlationId;
}
