package com.helphi.svc;


import com.helphi.api.user.Clinician;
import com.helphi.api.user.Patient;
import com.helphi.exception.NotFoundException;
import com.helphi.repository.ClinicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClinicianService {

    private final ClinicianRepository clinicianRepository;

    @Autowired
    ClinicianService(ClinicianRepository clinicianRepository) {
        this.clinicianRepository = clinicianRepository;
    }


    public Optional<Clinician> getClinician(UUID id) throws IllegalArgumentException {
        return this.clinicianRepository.findById(id);
    }


    public Clinician updateClinician(Clinician clinician) throws IllegalArgumentException {
        return this.clinicianRepository.findById(clinician.getId())
                .map(existingPatient -> this.clinicianRepository.save(clinician))
                .orElseThrow(() -> new NotFoundException(String.format("clinician with id %s cannot be found", clinician.getId().toString())));
    }

    public Optional<Clinician> getByUserId(String userId) {
        return this.clinicianRepository.findByUserId(userId);
    }
    
}

