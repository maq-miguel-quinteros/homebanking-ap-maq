package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.dtos.AccountDTO;
import com.hb.maq.homebanking.dtos.CardDTO;
import com.hb.maq.homebanking.models.Card;
import com.hb.maq.homebanking.models.CardColor;
import com.hb.maq.homebanking.models.CardType;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.CardRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    /** INYECCIÓN DE DEPENDENCIAS */
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;


    /** ####### CRUD: CREATE | READ | UPDATE | DELETE ####### */
    /** CREATE */
    @RequestMapping(value = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor,
                                           Authentication authentication){

        if ( cardType == null || cardColor == null ){
            return new ResponseEntity<>("Tiene que seleccionar un tipo y color de tarjeta", HttpStatus.FORBIDDEN);
        }

        Client client = clientRepository.findByEmail(authentication.getName());

        if ( client.getCards()
                .stream().filter(card -> card.getType().equals(cardType))
                .collect(Collectors.toList()).size() < 3 ){

            Card card = new Card(
                            (client.getFirstName() + " " + client.getLastName()), cardType, cardColor,
                    ((int)(Math.random() * 9999 + 1)) + "-" + ((int)(Math.random() * 9999 + 1)) + "-" +
                            ((int)(Math.random() * 9999 + 1)) + "-" + ((int)(Math.random() * 9999 + 1)),
                            (int)(Math.random() * 999 + 1), LocalDate.now(), LocalDate.now().plusYears(5));

            while ( cardRepository.findByNumber(card.getNumber()) != null ){
                card.setNumber(
                        ((int)(Math.random() * 9999 + 1)) + "-" + ((int)(Math.random() * 9999 + 1)) + "-" +
                        ((int)(Math.random() * 9999 + 1)) + "-" + ((int)(Math.random() * 9999 + 1))
                );
            }

            client.addCards(card);
            cardRepository.save(card);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Solo se pueden generar 3 tarjetas de cada tipo", HttpStatus.FORBIDDEN);
        }
    }

    /** READ */
    @RequestMapping(value = "/clients/current/cards")
    public List<CardDTO> readCards (Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());

        return cardRepository.findByClient(client)
                .stream().map(card -> new CardDTO(card))
                .collect(Collectors.toList());
    }


}
