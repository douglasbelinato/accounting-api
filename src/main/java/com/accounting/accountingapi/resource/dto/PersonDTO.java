package com.accounting.accountingapi.resource.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonDTO {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 60)
    private String street;

    @NotBlank
    @Size(min = 1, max = 8)
    private String number;

    @NotBlank
    @Size(min = 3, max = 40)
    private String neighborhood;

    @NotBlank
    @Size(min = 8, max = 10)
    private String zipCode;

    @NotBlank
    @Size(min = 3, max = 30)
    private String city;

    @NotBlank
    @Size(min = 2, max = 2)
    private String state;

    @Size(min = 3, max = 50)
    private String aditionalInformation;

    @NotNull
    private Boolean active;

}
