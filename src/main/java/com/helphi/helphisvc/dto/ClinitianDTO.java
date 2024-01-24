package com.helphi.helphisvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClinitianDTO {
    private String id;
    private String organisationId;
    private String email;
    private String title;
    private String forename;
    private String middlenames;
    private String lastname;
    private String contactNumber;
    private String alternateContactNumber;
}
