package com.helphi.svc;

import com.helphi.api.user.*;
import com.helphi.exception.NotFoundException;
import com.helphi.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
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
        return userRegistrationRepository.getbyCode(code).stream().findFirst();
    }

    public BaseUser linkAuthToUser(String userId, String code) {
         Registration userRegistration = this.getRegistration(code).orElse(null);

         if(userRegistration != null) {
             Optional<Patient> patientOptional =this.patientService.getPatient(userRegistration.getReferenceId());

             if(patientOptional.isPresent()) {
                 Patient patient = patientOptional.get();
                 patient.setUserId(userId);

                 return patientService.updatePatient(patient);
             }

             Optional<Clinician> clinicianOptional = this.clinicianService.getClinician(userRegistration.getReferenceId());

             if(clinicianOptional.isPresent()) {
                 Clinician clinician = clinicianOptional.get();
                 clinician.setUserId(userId);

                 return clinicianService.updateClinician(clinician);
             }
         }
        return null;
    }

    public Registration createNewRegistration(UUID userId, UserTypes userType) {
        Optional<Registration> existingRegistration = this.getRegistration(userId);

        if(existingRegistration.isPresent()) {
            return existingRegistration.get();
        }

        Optional<? extends BaseUser> user = Optional.empty();
        if(userType == UserTypes.PATIENT) {
            user = patientService.getPatient(userId);
        } else if( userType == UserTypes.CLINITIAN) {
            user = clinicianService.getClinician(userId);
        }

        if(user.isEmpty()) {
            throw new NotFoundException("User with id %s cannot be found", userId.toString());
        }

        String SEEDCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Registration existingCode;

        String generatedCode;
        do {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < 12; i++) {
                int random = (int) (Math.random() * SEEDCHARS.length()) - 1;
                builder.append(SEEDCHARS.charAt(random));
            }
            generatedCode = builder.toString();

            existingCode = this.getRegistration(generatedCode).orElse(null);
        }
        while(existingCode != null);

        Registration registration = new Registration();
        registration.setId(userId);
        registration.setCode(generatedCode);

        return userRegistrationRepository.save(registration);
    }
}
