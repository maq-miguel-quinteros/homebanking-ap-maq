package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.dtos.ClientDTO;
import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.AccountRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    /** INYECCIÓN DE DEPENDENCIAS */
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;


    /** ####### CRUD: CREATE | READ | UPDATE | DELETE ####### */
    /** CREATE */
    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName,
                                            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);

        Account account = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)), LocalDate.now(), 0);
        client.addAccounts(account);
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /** READ */
    @RequestMapping("/clients/current")
    public ClientDTO getCurrent(Authentication authentication){
        ClientDTO clientDTO = new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        return clientDTO;
    }

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientRepository.findAll()
                .stream().map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id)
                .map(client -> new ClientDTO(client))
                .orElse(null);
    }

}
