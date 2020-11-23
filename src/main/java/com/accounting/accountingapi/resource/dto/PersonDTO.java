package com.accounting.accountingapi.resource.dto;

import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PersonDTO {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @JsonMerge
    @Valid
    @NotNull
    private AddressDTO address;

    @NotNull
    private Boolean active;

}
