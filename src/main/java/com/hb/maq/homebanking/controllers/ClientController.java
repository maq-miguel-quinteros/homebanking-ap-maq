package com.hb.maq.homebanking.controllers;

import com.hb.maq.homebanking.dtos.ClientDTO;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName,
                                            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

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
