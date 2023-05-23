package com.mindhub.HomeBanking.services.implement;

import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import com.mindhub.HomeBanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientServices {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
    @Override
    public List<ClientDTO> getClientsDTO() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }
    @Override
    public ClientDTO getClientDTOAuth(Authentication authentication) {
        return new ClientDTO(this.findByEmail(authentication.getName()));
    }
    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}
