package com.autoBrokers.payment_service.service;

import com.autoBrokers.payment_service.entities.Card;

import java.util.List;

public interface ICardService extends CrudService<Card>{
    List<Card> findByDriverId(Long driverId) throws Exception;
    List<Card> findByClientId(Long clientId) throws Exception;
}
