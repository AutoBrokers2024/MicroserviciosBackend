package com.autoBrokers.contract_service.service;

import com.autoBrokers.contract_service.entities.Contract;

import java.util.List;

public interface IContractService extends CrudService<Contract> {

    List<Contract> findByDriverId(Long driverId) throws Exception;
    List<Contract> findByClientId(Long clientId) throws Exception;
    List<Contract> findByDriverIdAndClientId(Long driverId, Long clientId) throws Exception;
}
