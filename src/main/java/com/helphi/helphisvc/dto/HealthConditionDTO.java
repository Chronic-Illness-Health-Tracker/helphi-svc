package com.helphi.helphisvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthConditionDTO {
    private String id;
    private String organisationId;
    private String name;
    private String shortName;
}
