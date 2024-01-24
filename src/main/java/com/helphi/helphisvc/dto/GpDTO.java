package com.helphi.helphisvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GpDTO {
    private String id;
    private String addressId;
    private String forename;
    private String middlenames;
    private String lastname;
    private String contactNumber;
    private String contactEmail;
    private String surgeryId;
}
