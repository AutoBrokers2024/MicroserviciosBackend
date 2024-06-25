package com.autoBrokers.contract_service.repository;

import com.autoBrokers.contract_service.entities.StatusContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusContractRepository extends JpaRepository<StatusContract, Long> {


}
