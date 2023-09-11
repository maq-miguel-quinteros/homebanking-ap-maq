package com.hb.maq.homebanking.services.implement;

import com.hb.maq.homebanking.dtos.CardDTO;
import com.hb.maq.homebanking.models.Card;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.CardRepository;
import com.hb.maq.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    /** CREATE ACCOUNT & UPDATE ACCOUNT: SAVE */
    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }

    /** READ ACCOUNTS: FIND ALL */

    /** READ ACCOUNT: FIND BY */
    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }
    @Override
    public List<CardDTO> findByClientToCardDTO(Client client) {
        return cardRepository.findByClient(client)
                .stream().map(card -> new CardDTO(card))
                .collect(Collectors.toList());
    }

}
