package com.hb.maq.homebanking.services;

import com.hb.maq.homebanking.dtos.ClientDTO;
import com.hb.maq.homebanking.models.Client;


import java.util.List;


public interface ClientService{

    /** CREATE CLIENT & UPDATE CLIENT: SAVE */
    void save(Client client);

    /** READ CLIENTS: FIND ALL */
    List<ClientDTO> findAllToListClientDTO();

    /** READ CLIENT: FIND BY */
    Client findByEmail(String email);
    ClientDTO findByEmailToClientDTO(String email);

}
