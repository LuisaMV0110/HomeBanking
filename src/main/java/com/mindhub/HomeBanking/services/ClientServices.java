package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface ClientServices {
    void saveClient(Client client);
    List<ClientDTO> getClientsDTO();
    ClientDTO getClientDTOAuth(Authentication authentication);
    Client findByEmail(String email);

}
