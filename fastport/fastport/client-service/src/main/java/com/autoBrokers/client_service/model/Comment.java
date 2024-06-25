package com.autoBrokers.client_service.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {


    private Long idClient;


    private Long idDriver;


    private String comment;


    private int star;



}
