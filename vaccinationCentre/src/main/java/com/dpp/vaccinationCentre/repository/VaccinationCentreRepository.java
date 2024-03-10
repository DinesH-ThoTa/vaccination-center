package com.dpp.vaccinationCentre.repository;

import com.dpp.vaccinationCentre.entity.VaccinationCentre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationCentreRepository extends JpaRepository<VaccinationCentre, Integer> {
}
