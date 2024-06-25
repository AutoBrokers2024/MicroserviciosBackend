package com.autoBrokers.payment_service.repository;

import com.autoBrokers.payment_service.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICardRepository extends JpaRepository<Card, Long> {

    List<Card> findByIdDriver(Long idDriver);

    List<Card> findByIdClient(Long idClient);

}
