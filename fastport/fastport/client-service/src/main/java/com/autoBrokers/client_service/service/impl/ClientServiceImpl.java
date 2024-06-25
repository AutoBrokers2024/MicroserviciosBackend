package com.autoBrokers.client_service.service.impl;

import com.autoBrokers.client_service.entities.Client;
import com.autoBrokers.client_service.feignclients.CardFeignClient;
import com.autoBrokers.client_service.feignclients.CommentFeignClient;
import com.autoBrokers.client_service.feignclients.ContractFeignClient;
import com.autoBrokers.client_service.model.Card;
import com.autoBrokers.client_service.model.Comment;
import com.autoBrokers.client_service.model.Contract;
import com.autoBrokers.client_service.repository.IClientRepository;
import com.autoBrokers.client_service.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements IClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommentFeignClient commentFeignClient;

    @Autowired
    private CardFeignClient cardFeignClient;
    @Autowired
    private ContractFeignClient contractFeignClient;

    private final IClientRepository clientRepository;

    public ClientServiceImpl(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public Client save(Client client) throws Exception {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAll() throws Exception {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getById(Long id) throws Exception {
        return clientRepository.findById(id);
    }

    @Override
    public Client findByEmailAndPassword(String email, String password) throws Exception {
        return clientRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Comment> getComment(int commentId) {
        return restTemplate.getForObject("http://localhost:8080/api/v1/comments/" + commentId, List.class);
    }

    @Override
    @Transactional
    public Comment saveComment(Long clientId, Comment comment) {
        comment.setIdClient(clientId);
        return commentFeignClient.save(comment);
    }

    @Override
    public Map<String, Object> getClientAndComment(Long clientId) {
        Map<String, Object> resultado = new HashMap<>();
        Client client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
            resultado.put("Mensaje", "El cliente no existe");
            return resultado;
        }

        resultado.put("Cliente", client);
        List<Comment> comments = commentFeignClient.getComments(clientId);
        if (comments.isEmpty()) {
            resultado.put("Comentarios", "El cliente no tiene comentarios");
        } else {
            resultado.put("Comentarios", comments);
        }

        return resultado;
    }


    // contract
    @Override
    public List<Contract> getContract(int contractId) {
        return restTemplate.getForObject("http://localhost:8080/api/v1/contracts/" + contractId, List.class);
    }

    @Override
    @Transactional
    public Contract saveContract(Long clientId, Contract contract) {
        contract.setIdClient(clientId);
        return contractFeignClient.save(contract);
    }

    @Override
    public Map<String, Object> getClientAndContract(Long clientId) {
        Map<String, Object> resultado = new HashMap<>();
        Client client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
            resultado.put("Mensaje", "El cliente no existe");
            return resultado;
        }

        resultado.put("Cliente", client);
        List<Contract> contracts = contractFeignClient.getContracts(clientId);
        if (contracts.isEmpty()) {
            resultado.put("Comentarios", "El cliente no tiene comentarios");
        } else {
            resultado.put("Comentarios", contracts);
        }

        return resultado;
    }

    @Override
    public List<Card> getCard(int cardId) {
        return restTemplate.getForObject("http://localhost:8080/api/v1/cards/" + cardId, List.class);
    }

    @Override
    @Transactional
    public Card saveCard(Long clientId, Card card) {
        card.setIdClient(clientId);
        return cardFeignClient.save(card);
    }

    @Override
    public Map<String, Object> getClientAndCard(Long clientId) {
        Map<String, Object> resultado = new HashMap<>();
        Client client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
            resultado.put("Mensaje", "El cliente no existe");
            return resultado;
        }

        resultado.put("Cliente", client);
        List<Card> cards = cardFeignClient.getCards(clientId);
        if (cards.isEmpty()) {
            resultado.put("Comentarios", "El cliente no tiene comentarios");
        } else {
            resultado.put("Comentarios", cards);
        }

        return resultado;
    }




}
