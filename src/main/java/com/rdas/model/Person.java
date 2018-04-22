package com.rdas.model;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor @NoArgsConstructor
@Data
public class Person {
    private String id;

    private String name;

    private int age;

    private PersonType type;
}
