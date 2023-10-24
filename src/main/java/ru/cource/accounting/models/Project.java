package ru.cource.accounting.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class Project {


//    id serial NOT NULL CONSTRAINT projects_pk PRIMARY KEY,
//    name VARCHAR(200) NOT NULL,
//    cost NUMERIC(18, 2) NOT NULL,
//    department_id integer,
//    date_beg timestamp NOT NULL,
//    date_end timestamp,
//    date_end_real timestamp,
//    FOREIGN KEY (department_id) REFERENCES departments (id)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    @Column(name = "date_beg")
    private Timestamp dateBeg;
    @Column(name = "date_end")
    private Timestamp dateEnd;
    @Column(name = "date_end_real")
    private Timestamp dateEndReal;

}
