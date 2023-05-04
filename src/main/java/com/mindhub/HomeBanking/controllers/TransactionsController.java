package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.Client;
import com.mindhub.HomeBanking.models.Transaction;
import com.mindhub.HomeBanking.models.TransactionType;
import com.mindhub.HomeBanking.repositories.AccountRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import com.mindhub.HomeBanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> newTransaction(
            Authentication auth, @RequestParam Double amount, @RequestParam String description,
            @RequestParam String initialAccount, @RequestParam String destinateAccount){

//        Auth
        Client client = clientRepository.findByEmail((auth.getName()));
        Account initialAccountAuth = accountRepository.findByNumber(initialAccount.toUpperCase());
        Account destinateAccountAuth = accountRepository.findByNumber(destinateAccount.toUpperCase());

//        Initial Account
        if(initialAccount.isBlank()){
            return new ResponseEntity<>("Please, select one of your accounts", HttpStatus.FORBIDDEN);
        } else if (initialAccountAuth == null) {
            return new ResponseEntity<>("This account" + initialAccount + "doesn't exist", HttpStatus.FORBIDDEN);
        } else if (!client.getAccounts().contains(initialAccountAuth)){
            return new ResponseEntity<>("You are not the account owner", HttpStatus.FORBIDDEN);
        }
//       Destinate Account
        if(destinateAccount.isBlank()){
            return new ResponseEntity<>("Please, enter the account number to make the transfer", HttpStatus.FORBIDDEN);
        } else if (destinateAccountAuth == null) {
            return new ResponseEntity<>("This account" + destinateAccount + "doesn't exist", HttpStatus.FORBIDDEN);
        } else if (destinateAccountAuth.getNumber().equals(initialAccountAuth.getNumber())){
            return new ResponseEntity<>("You cannot send to the same account", HttpStatus.FORBIDDEN);
        }
//        Amount
        if (amount == null|| amount.isNaN()){
            return new ResponseEntity<>("Please, enter an amount", HttpStatus.FORBIDDEN);
        } else if( amount < 1 ){
            return new ResponseEntity<>("Please, enter an amount bigger than 0", HttpStatus.FORBIDDEN);
        } else if (initialAccountAuth.getBalance() < amount) {
            return new ResponseEntity<>("You do not have enough balance for this transaction", HttpStatus.FORBIDDEN);
        }
//        Description
        if(description.isBlank()){
            description = "Transaction to " + destinateAccount;
        }
//        Add transactions
        initialAccountAuth.setBalance(initialAccountAuth.getBalance() - amount);
        destinateAccountAuth.setBalance(destinateAccountAuth.getBalance() + amount);
//        Debit
        Transaction newTransactionD = new Transaction(TransactionType.DEBIT, amount, description, LocalDateTime.now());
        initialAccountAuth.addTransaction((newTransactionD));
        transactionRepository.save(newTransactionD);
//        Credit
        Transaction newTransactionC = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now());
        destinateAccountAuth.addTransaction((newTransactionC));
        transactionRepository.save(newTransactionC);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
