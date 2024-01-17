package com.example.SecurityDemo.entity;

import jdk.jfr.Name;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "springsecurity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmpModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name(value = "id")
    private int id;
    @Name(value = "name")
    private String name;
    @Name(value = "email")
    private String email;
    @Name(value = "city")
    private String city;
}
