package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.services.AccountServices;
import com.mindhub.HomeBanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;



@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    Método número aleatorio
    public static String randomNumber(){
        Random randomNumber = new Random();
        return ("VIN-" + randomNumber.nextInt(999989 + 10));
    }
    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        return clientServices.getClientDTOAuth(authentication);
    }
    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientServices.getClientsDTO();
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientServices.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        String number;
        do{
            number = randomNumber();
        }
        while (accountServices.findByNumber(number) != null);

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientServices.saveClient(newClient);
        Account newAccount = new Account(number, LocalDateTime.now(), 0.00);
        newClient.addAccount(newAccount);
        accountServices.saveAccount(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
