package com.autoBrokers.contract_service.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "contracts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "service_from", nullable = false)
    private String from;

    @Column(name = "service_to", nullable = false)
    private String to;

    @Column(name = "date", nullable = false)
    //@Temporal(TemporalType.DATE)
    private LocalDate contractDate;

    @Column(name = "time_departure", nullable = false)
    private LocalTime timeDeparture;

    @Column(name = "time_arrival", nullable = false)
    private LocalTime timeArrival;

    @Column(name = "amount", nullable = false)
    private String amount;

    @Column(name = "quantity", nullable = false)
    private String quantity;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "visible", nullable = true)
    private boolean visible;


    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status_contract_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@JsonIgnore
    private StatusContract status;



    /*
    @Transient
    private Driver driverTmp;

    @Transient
    private Client clientTmp;
     */

}