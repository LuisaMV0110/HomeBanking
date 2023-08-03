package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.AccountType;
import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.services.AccountServices;
import com.mindhub.HomeBanking.services.ClientServices;
import com.mindhub.HomeBanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        return clientServices.getClientDTOAuth(authentication);
    }
    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientServices.getClientsDTO();
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

//        Email
        if (clientServices.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        if (email.isBlank()) {
            return new ResponseEntity<>("Your email is missing.", HttpStatus.FORBIDDEN);
        }
        else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            return new ResponseEntity<>("Please enter a valid Email address.", HttpStatus.FORBIDDEN);
        }
//        First Name
        if (firstName.isBlank()) {
            return new ResponseEntity<>("Your name is missing.", HttpStatus.FORBIDDEN);
        } else if (!firstName.matches("^[a-zA-Z]*$")) {
            return new ResponseEntity<>("Please enter a valid FirstName. Only letters are allowed.", HttpStatus.FORBIDDEN);
        }
//        Last Name
        if (lastName.isBlank()) {
            return new ResponseEntity<>("Your last name is missing.", HttpStatus.FORBIDDEN);
        }
        else if (!lastName.matches("^[a-zA-Z]*$")) {
            return new ResponseEntity<>("Please enter a valid LastName. Only letters are allowed.", HttpStatus.FORBIDDEN);
        }
//        Password
        if (password.isBlank()) {
            return new ResponseEntity<> ("Your password is missing", HttpStatus.FORBIDDEN);
        }

        String number;
        do{
            number = AccountUtils.getRandomNumber();
        }
        while (accountServices.findByNumber(number) != null);

        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientServices.saveClient(newClient);
        Account newAccount = new Account(number, LocalDateTime.now(), 0.00,true, AccountType.CURRENT);
        newClient.addAccount(newAccount);
        accountServices.saveAccount(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
