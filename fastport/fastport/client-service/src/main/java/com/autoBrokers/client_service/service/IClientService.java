package com.autoBrokers.client_service.service;

import com.autoBrokers.client_service.entities.Client;
import com.autoBrokers.client_service.model.Card;
import com.autoBrokers.client_service.model.Comment;
import com.autoBrokers.client_service.model.Contract;

import java.util.List;
import java.util.Map;

public interface IClientService extends CrudService<Client>{

    Client findByEmailAndPassword(String email, String password) throws Exception;

    Comment saveComment(Long clientId, Comment comment);
    Map<String, Object> getClientAndComment(Long clientId);

    List<Comment> getComment(int commentId);
    List<Contract> getContract(int contractId);
    Contract saveContract(Long clientId, Contract contract);
    Map<String, Object> getClientAndContract(Long clientId);

    Card saveCard(Long clientId, Card card);
    Map<String, Object> getClientAndCard(Long clientId);

    List<Card> getCard(int cardId);


}
