package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.dtos.AccountDTO;
import com.hb.maq.homebanking.dtos.CardDTO;
import com.hb.maq.homebanking.models.Card;
import com.hb.maq.homebanking.models.CardColor;
import com.hb.maq.homebanking.models.CardType;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.CardRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
import com.hb.maq.homebanking.services.CardService;
import com.hb.maq.homebanking.services.ClientService;
import com.hb.maq.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    /** INYECCIÓN DE DEPENDENCIAS */
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;


    /** ####### CRUD: CREATE | READ | UPDATE | DELETE ####### */
    /** CREATE */
    @PostMapping(value = "/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor,
                                           Authentication authentication){

        if ( cardType == null || cardColor == null ){
            return new ResponseEntity<>("Tiene que seleccionar un tipo y color de tarjeta", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findByEmail(authentication.getName());

        if ( client.getCards()
                .stream().filter(card -> card.getType().equals(cardType))
                .collect(Collectors.toList()).size() < 3 ){

            Card card = new Card(
                            (client.getFirstName() + " " + client.getLastName()), cardType, cardColor,
                            CardUtils.getCardNumber(),  CardUtils.getCardCvv(),
                            LocalDate.now(), LocalDate.now().plusYears(5));

            while ( cardService.findByNumber(card.getNumber()) != null ){
                card.setNumber( CardUtils.getCardNumber());
            }

            client.addCards(card);
            cardService.save(card);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Solo se pueden generar 3 tarjetas de cada tipo", HttpStatus.FORBIDDEN);
        }
    }



    /** READ */
    @GetMapping(value = "/clients/current/cards")
    public List<CardDTO> readCards (Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return cardService.findByClientToCardDTO(client);
    }

    /** Eliminar tarjetas	Agregar botón para eliminar una tarjeta, se debe crear el servicio y llamarlo desde javascript */
    @RequestMapping(value = "/clients/current/cards/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCard(@PathVariable Long id){
        if (cardService.findById(id) != null){
            cardService.deleteCard(id);
            return new ResponseEntity<>(HttpStatus.PROCESSING);
        }else {
            return new ResponseEntity<>("La tarjeta a borrar no existe", HttpStatus.FORBIDDEN);
        }

    }



}
