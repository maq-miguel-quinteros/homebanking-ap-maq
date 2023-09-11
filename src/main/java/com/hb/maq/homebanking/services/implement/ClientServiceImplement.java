package com.hb.maq.homebanking.services.implement;

import com.hb.maq.homebanking.dtos.ClientDTO;
import com.hb.maq.homebanking.models.Client;
import com.hb.maq.homebanking.repositories.ClientRepository;
import com.hb.maq.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /** CREATE CLIENT & UPDATE CLIENT: SAVE */
    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    /** READ CLIENTS: FIND ALL */
    @Override
    public List<ClientDTO> findAllToListClientDTO() {
        return clientRepository.findAll().stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }

    /** READ CLIENT: FIND BY */
    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    @Override
    public ClientDTO findByEmailToClientDTO(String email) {
        return new ClientDTO(clientRepository.findByEmail(email));
    }
}
