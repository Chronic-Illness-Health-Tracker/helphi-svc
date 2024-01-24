package com.helphi.helphisvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTO {
    private String id;
    private String addresslineOne;
    private String addresslineTwo;
    private String postcode;
}
