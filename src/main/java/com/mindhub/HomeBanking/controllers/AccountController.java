package com.mindhub.HomeBanking.controllers;
import com.mindhub.HomeBanking.dtos.AccountDTO;
import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.services.AccountServices;
import com.mindhub.HomeBanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mindhub.HomeBanking.controllers.ClientController.randomNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ClientServices clientServices;

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts (Authentication authentication) {
        return new ClientDTO(clientServices.findByEmail(authentication.getName())).getAccounts().stream().collect(toList());
    }
    @GetMapping("/clients/current/accounts/{id}")
    public Optional<AccountDTO> getAccount(@PathVariable Long id){
        Optional<Account> optionalAccount = accountServices.findById(id);
        return optionalAccount.map(account -> new AccountDTO(account));
    };
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication){
        String number;
        do{
            number = randomNumber();
        }
        while (accountServices.findByNumber(number) != null);

        if (clientServices.findByEmail(authentication.getName()).getAccounts().size() <= 2){
            Account newAccount = new Account(number, LocalDateTime.now(), 0.00);
            clientServices.findByEmail(authentication.getName()).addAccount(newAccount);
            accountServices.saveAccount(newAccount);
        } else {
            return new ResponseEntity<>("You cannot create more than 3 accounts", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
