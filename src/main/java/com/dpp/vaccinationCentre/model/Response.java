package com.dpp.vaccinationCentre.model;

import com.dpp.vaccinationCentre.entity.VaccinationCentre;
import lombok.Data;

import java.util.List;

@Data
public class Response {

    private VaccinationCentre vaccinationCentre;

    private List<Citizen> citizens;
}
