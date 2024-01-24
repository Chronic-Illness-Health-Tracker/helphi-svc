package com.helphi.helphisvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganisationDTO {
    private String id;
    private String name;
    private String countryCode;
}
