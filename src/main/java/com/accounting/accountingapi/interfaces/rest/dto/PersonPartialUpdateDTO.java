package com.accounting.accountingapi.interfaces.rest.dto;

import lombok.Setter;

@Setter
public class PersonPartialUpdateDTO {

    private String name;
    private AddressDTO address;
    private Boolean active;

}
