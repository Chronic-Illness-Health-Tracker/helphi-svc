package com.helphi.svc;


import com.helphi.api.user.Clinician;
import com.helphi.api.user.Patient;
import com.helphi.exception.NotFoundException;
import com.helphi.repository.ClinicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClinicianService {

    private final ClinicianRepository clinicianRepository;

    @Autowired
    ClinicianService(ClinicianRepository clinicianRepository) {
        this.clinicianRepository = clinicianRepository;
    }


    public Clinician getClinician(UUID id) throws IllegalArgumentException {

        Optional<Clinician> clinician = this.clinicianRepository.findById(id);
        if(clinician.isEmpty()) {
            throw new NotFoundException(String.format("Clinician with id %s cannot be found", id.toString()));
        }

        return clinician.get();
    }

    public Clinician createClinician(Clinician clinician) {
        return this.clinicianRepository.save(clinician);
    }


    public Clinician updateClinician(Clinician clinician) throws IllegalArgumentException {
        return this.clinicianRepository.findById(clinician.getId())
                .map(existingPatient -> this.clinicianRepository.save(clinician))
                .orElseThrow(() -> new NotFoundException(String.format("clinician with id %s cannot be found", clinician.getId().toString())));
    }

    public void deleteClinician(UUID clinicianId) {
        Optional<Clinician> clinician = this.clinicianRepository.findById(clinicianId);
        if(clinician.isEmpty()) {
            throw new NotFoundException();
        }
        this.clinicianRepository.delete(clinician.get());
    }

    public Optional<Clinician> getByUserId(String userId) {
        return this.clinicianRepository.findByUserId(userId);
    }
    
}

