package com.autoBrokers.driver_service.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Experience {

    private Long idDriver;

    private String job;

    private int years;
}
