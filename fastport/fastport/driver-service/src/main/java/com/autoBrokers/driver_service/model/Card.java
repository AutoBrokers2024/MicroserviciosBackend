package com.autoBrokers.driver_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Card {




    private String holderName;


    private String nickname;


    private String issuer;


    private Long number;


    private LocalDate expirationDate;


    private int cvv;


    private String email;


    private Long idClient;


    private Long idDriver;
}
