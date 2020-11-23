package com.accounting.accountingapi.repository.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    private String street;

    private String number;

    private String neighborhood;

    @Column(name = "zip_code")
    private String zipCode;

    private String city;

    private String state;

    @Column(name = "aditional_information")
    private String aditionalInformation;

}
