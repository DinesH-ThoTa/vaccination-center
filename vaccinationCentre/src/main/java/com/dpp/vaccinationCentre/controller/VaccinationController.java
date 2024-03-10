package com.dpp.vaccinationCentre.controller;

import com.dpp.vaccinationCentre.entity.VaccinationCentre;
import com.dpp.vaccinationCentre.model.Citizen;
import com.dpp.vaccinationCentre.model.Response;
import com.dpp.vaccinationCentre.repository.VaccinationCentreRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/vaccination")
@RequiredArgsConstructor
public class VaccinationController {

    private final VaccinationCentreRepository vaccinationCentreRepository;

    private final RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<VaccinationCentre> createCitizen(@RequestBody VaccinationCentre vaccinationCentre){
        VaccinationCentre savedVaccinationCentre =  vaccinationCentreRepository.save(vaccinationCentre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVaccinationCentre);
    }

    @GetMapping("/getAllDetails/{centreId}")
    @CircuitBreaker(name = "vaccinationService", fallbackMethod = "handleCitiZenClientTimeOut")
    public ResponseEntity<Response> getVaccinationCentre(@PathVariable Integer centreId) {

        Response response = new Response();
        response.setVaccinationCentre(vaccinationCentreRepository.findById(centreId).
                orElseThrow(() -> new IllegalArgumentException("Vaccination centre not found")));
        List<Citizen> citizens = restTemplate.getForObject(
                "http://CITIZEN-SERVICE/citizen/vaccinationId/"+centreId,List.class
        );
        response.setCitizens(citizens);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    public ResponseEntity<Response> handleCitiZenClientTimeOut(@PathVariable Integer centreId, Exception exception){
        Response response = new Response();
        response.setVaccinationCentre(vaccinationCentreRepository.findById(centreId).
                orElseThrow(() -> new IllegalArgumentException("Vaccination centre not found")));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
