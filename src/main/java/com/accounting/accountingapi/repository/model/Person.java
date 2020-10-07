package com.accounting.accountingapi.repository.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "person")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String street;

    private String number;

    private String neighborhood;

    @Column(name = "zip_code")
    private String zipCode;

    private String city;

    private String state;

    @Column(name = "aditional_information")
    private String aditionalInformation;

    private Boolean active;

}
