package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.dtos.ClientDTO;
import com.hb.maq.homebanking.models.Account;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.AccountRepository;
import com.hb.maq.homebanking.repositories.ClientRepository;
import com.hb.maq.homebanking.services.AccountService;
import com.hb.maq.homebanking.services.ClientService;
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
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    /** CREATE CLIENTE */
    @PostMapping(value = "/clients")
    public ResponseEntity<Object> createClient(@RequestParam String firstName, @RequestParam String lastName,
                                            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.save(client);

        /** CREATE ACCOUNT */
        Account account = new Account("VIN-" + ((int)(Math.random() * 99999999 + 1)), LocalDate.now(), 0.0);
        client.addAccounts(account);
        accountService.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    /** READ CLIENTS */
    @GetMapping("/clients")
    public List<ClientDTO> readClients(){
        return clientService.findAllToListClientDTO();
    }

    /** READ CLIENT: CURRENT */
    @GetMapping("/clients/current")
    public ClientDTO readCurrent(Authentication authentication){
        return clientService.findByEmailToClientDTO(authentication.getName());
    }

    /*
    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id)
                .map(client -> new ClientDTO(client))
                .orElse(null);

    }
    */

}
