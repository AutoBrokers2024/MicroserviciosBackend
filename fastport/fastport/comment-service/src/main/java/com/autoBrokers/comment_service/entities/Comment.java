package com.autoBrokers.comment_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Column(name = "id_driver", nullable = false)
    private Long idDriver;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "star", nullable = false)
    private int star;

}
