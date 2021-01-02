package com.accounting.accountingapi.repository.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private Boolean active;

}
