package com.hb.maq.homebanking.services;

import com.hb.maq.homebanking.dtos.CardDTO;

import com.hb.maq.homebanking.models.Card;
import com.hb.maq.homebanking.models.Client;



import java.util.List;

public interface CardService {

    /** CREATE ACCOUNT & UPDATE ACCOUNT: SAVE */
    void save(Card card);

    /** READ ACCOUNTS: FIND ALL */

    /** READ ACCOUNT: FIND BY */
    Card findByNumber(String number);
    List<CardDTO> findByClientToCardDTO(Client client);
}
