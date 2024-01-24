package com.helphi.helphisvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GpSurgeryDTO {
    private String id;
    private String name;
    private AddressDTO address;
}
