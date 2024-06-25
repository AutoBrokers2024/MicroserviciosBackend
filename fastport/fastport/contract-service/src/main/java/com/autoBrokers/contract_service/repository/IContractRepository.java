package com.autoBrokers.contract_service.repository;

import com.autoBrokers.contract_service.entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContractRepository   extends JpaRepository<Contract, Long> {
    List<Contract> findByDriverId(Long driverId);

    List<Contract> findByClientId(Long clientId);

    List<Contract> findByDriverIdAndClientId(Long driverId, Long clientId);

    //List<Contract> findByDriverIdAndStatus(Long driverId, StatusContract statusContract);
}