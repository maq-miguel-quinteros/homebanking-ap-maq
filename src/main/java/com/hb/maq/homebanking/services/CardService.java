package com.hb.maq.homebanking.services;

import com.hb.maq.homebanking.dtos.CardDTO;

import com.hb.maq.homebanking.models.Card;
import com.hb.maq.homebanking.models.Client;



import java.util.List;

public interface CardService {

    /** CREATE CARDS & UPDATE CARDS: SAVE */
    void save(Card card);

    /** READ CARDS: FIND ALL */

    /** READ CARD: FIND BY */
    Card findByNumber(String number);
    List<CardDTO> findByClientToCardDTO(Client client);
    Card findById(Long id);

    /** DELETE CARDS */
    void deleteCard(Long id);
}
