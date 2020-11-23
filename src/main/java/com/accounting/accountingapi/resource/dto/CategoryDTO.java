package com.accounting.accountingapi.resource.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CategoryDTO {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

}
