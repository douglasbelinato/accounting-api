package com.accounting.accountingapi.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
public class PersonPartialUpdateDTO {

    private String name;
    private AddressDTO address;
    private Boolean active;

}
