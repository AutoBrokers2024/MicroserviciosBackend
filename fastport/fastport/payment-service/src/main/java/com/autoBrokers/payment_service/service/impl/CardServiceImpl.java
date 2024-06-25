package com.autoBrokers.payment_service.service.impl;

import com.autoBrokers.payment_service.entities.Card;
import com.autoBrokers.payment_service.repository.ICardRepository;
import com.autoBrokers.payment_service.service.ICardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)

public class CardServiceImpl implements ICardService {

    private ICardRepository cardRepository;

    public CardServiceImpl(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public Card save(Card card) throws Exception {
        return cardRepository.save(card);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        cardRepository.deleteById(id);
    }

    @Override
    public Optional<Card> getById(Long id) throws Exception {
        return cardRepository.findById(id);
    }

    @Override
    public List<Card> getAll() throws Exception {
        return cardRepository.findAll();
    }
    @Override
    public List<Card> findByDriverId(Long driverId) throws Exception {
        return cardRepository.findByIdDriver(driverId);
    }

    @Override
    public List<Card> findByClientId(Long clientId) throws Exception {
        return cardRepository.findByIdClient(clientId);
    }


}
