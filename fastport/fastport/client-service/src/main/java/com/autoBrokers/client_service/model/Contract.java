package com.autoBrokers.client_service.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Contract {



    private String subject;


    private String from;


    private String to;


    //@Temporal(TemporalType.DATE)
    private LocalDate contractDate;


    private LocalTime timeDeparture;


    private LocalTime timeArrival;


    private String amount;


    private String quantity;


    private String description;


    private boolean visible;



    private Long idClient;


    private Long idDriver;



}
