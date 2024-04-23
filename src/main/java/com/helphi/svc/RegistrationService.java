package com.helphi.svc;

import com.helphi.api.user.*;
import com.helphi.exception.NotFoundException;
import com.helphi.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class RegistrationService {
    private final UserRegistrationRepository userRegistrationRepository;
    private final PatientService patientService;
    private final ClinicianService clinicianService;

    @Autowired
    RegistrationService(UserRegistrationRepository userRegistrationRepository, PatientService patientService, ClinicianService clinicianService){
        this.userRegistrationRepository = userRegistrationRepository;
        this.patientService = patientService;
        this.clinicianService = clinicianService;
    }

    public Optional<Registration> getRegistration(UUID userId) {
        return userRegistrationRepository.getbyUserId(userId).stream().findFirst();
    }

    public Optional<Registration> getRegistration(String code) {
        return userRegistrationRepository.getbyCode(code);
    }

    public BaseUser linkAuthToUser(String userId, String code) {
         Optional<Registration> userRegistrationOptional = this.getRegistration(code);

         if(userRegistrationOptional.isEmpty()) {
            throw new NotFoundException();
         }

        Registration userRegistration = userRegistrationOptional.get();


         Optional<Patient> patientOptional = this.patientService.getPatient(userRegistration.getReferenceId());

         if(patientOptional.isPresent()) {
             Patient patient = patientOptional.get();
             patient.setUserId(userId);

             return patientService.updatePatient(patient);
         }

         Clinician clinician = this.clinicianService.getClinician(userRegistration.getReferenceId());
         clinician.setUserId(userId);
         return clinicianService.updateClinician(clinician);
    }

    public Registration createNewRegistration(UUID userId, UserTypes userType) {
        Optional<Registration> existingRegistration = this.getRegistration(userId);

        if(existingRegistration.isPresent()) {
            return existingRegistration.get();
        }

        if(userType == UserTypes.PATIENT) {
            Optional<Patient> patient = patientService.getPatient(userId);
            if(patient.isEmpty()) {
                throw new NotFoundException(String.format("User with id %s cannot be found", userId.toString()));
            }
        } else if(userType == UserTypes.CLINITIAN) {
            clinicianService.getClinician(userId);
        }

        String SEEDCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Registration existingCode;

        String generatedCode;
        do {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < 12; i++) {
                Random random = new Random();
                int rand = random.nextInt(SEEDCHARS.length() -1);
                builder.append(SEEDCHARS.charAt(rand));
            }
            generatedCode = builder.toString();

            existingCode = this.getRegistration(generatedCode).orElse(null);
        }
        while(existingCode != null);

        Registration registration = new Registration();
        registration.setReferenceId(userId);
        registration.setCode(generatedCode);

        return userRegistrationRepository.save(registration);
    }

    public BaseUser validCode(String registrationCode) {
        Optional<Registration> registrationOptional = this.userRegistrationRepository.getbyCode(registrationCode);

        if(registrationOptional.isEmpty()) {
            throw new NotFoundException();
        }

        Registration registration = registrationOptional.get();

        Optional<Patient> patientOptional = this.patientService.getPatient(registration.getReferenceId());

        if(patientOptional.isPresent()) {
            return patientOptional.get();
        }

        //clinicians throw error if not found
        return this.clinicianService.getClinician(registration.getReferenceId());
    }
}
