package com.mindhub.HomeBanking.controllers;
import com.mindhub.HomeBanking.dtos.AccountDTO;
import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.AccountType;
import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.models.Transaction;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.TransactionRepository;
import com.mindhub.HomeBanking.services.AccountServices;
import com.mindhub.HomeBanking.services.ClientServices;
import com.mindhub.HomeBanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mindhub.HomeBanking.controllers.ClientController.randomNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private TransactionServices transactionServices;

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
    public ResponseEntity<Object> newAccount(Authentication authentication, @RequestParam AccountType accountType){
        Client client = clientServices.findByEmail(authentication.getName());
        String number;
        do{
            number = randomNumber();
        }
        while (accountServices.findByNumber(number) != null);

        int totalAccounts = client.getAccounts().size();
        int activeAccounts = (int) client.getAccounts().stream().filter(Account::isAccountActive).count();

        if (totalAccounts >= 8 || activeAccounts >= 3) {
            return new ResponseEntity<>("Client already has the maximum number of accounts allowed.",HttpStatus.FORBIDDEN);
        }
            Account newAccount = new Account(number, LocalDateTime.now(), 0.00,true,accountType);
            clientServices.findByEmail(authentication.getName()).addAccount(newAccount);
            accountServices.saveAccount(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping ("/accounts/{id}")
    public ResponseEntity<Object> deleteAccount(Authentication auth,@PathVariable long id) {
        Client client = clientServices.findByEmail(auth.getName());
        if (client == null) {
            return new ResponseEntity<>("You can't create an account because you're not a client.", HttpStatus.NOT_FOUND);
        }
        Account account = accountRepository.findById(id);
        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
        if (account.getBalance() != 0.0) {
            return new ResponseEntity<>("The account can't be deleted because it has a balance different from 0.", HttpStatus.FORBIDDEN);
        }
        account.setAccountActive(false);
        accountServices.saveAccount(account);
        List<Transaction> transactions = transactionRepository.findByAccountId(id);
        transactions.forEach(transaction -> {
            transaction.setTransactionActive(false);
            transactionServices.saveTransaction(transaction);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
