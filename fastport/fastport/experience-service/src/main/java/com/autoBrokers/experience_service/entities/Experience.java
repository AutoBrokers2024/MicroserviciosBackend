package com.autoBrokers.experience_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "experience")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Experience implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_driver", nullable = false)
    private Long idDriver;



    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "years", nullable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    private int years;
}