package com.helphi.helphisvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class PatientDTO {
    private String id;
    private String email;
    private String title;
    private String forename;
    private String middlenames;
    private String lastname;
    private String contactNumber;
    private String alternateContactNumber;
    private Date dateOfBirth;
    private String nhsNumber;
    private String gpId;
    private String addressId;
}
