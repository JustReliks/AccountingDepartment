package ru.cource.accounting.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {

//    id serial NOT NULL CONSTRAINT employees_pk PRIMARY KEY,
//    first_name VARCHAR(20) NOT NULL,
//    last_name VARCHAR(20) NOT NULL,
//    father_name VARCHAR(20) NOT NULL,
//    position VARCHAR(50) NOT NULL,
//    salary NUMERIC(18, 2) NOT NULL

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "position")
    private String position;

    @Column(name = "salary")
    private double salary;

    @ManyToMany
    @JoinTable(name = "departments_employees",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private Set<Department> departments;


}
