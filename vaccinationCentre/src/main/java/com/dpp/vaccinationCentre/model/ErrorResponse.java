package com.dpp.vaccinationCentre.model;

import lombok.Data;

@Data
public class ErrorResponse {

    private int errorCode;
    private String message;

}
