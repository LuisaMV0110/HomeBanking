package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.LoanApplicationDTO;
import com.mindhub.HomeBanking.dtos.LoanDTO;
import com.mindhub.HomeBanking.models.*;
import com.mindhub.HomeBanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class LoanController {
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private LoanServices loanServices;
    @Autowired
    private ClientLoanServices clientLoanServices;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private TransactionServices transactionServices;

    @GetMapping("loans")
    public List<LoanDTO> getLoansDTO(){
        return loanServices.getLoanDTO();
    }

    @Transactional
    @PostMapping("loans")
    public ResponseEntity<Object> newLoan(Authentication auth, @RequestBody LoanApplicationDTO loanApplicationDTO){
        double amount = loanApplicationDTO.getAmount();
        int payments = loanApplicationDTO.getPayments();
        Client client = clientServices.findByEmail(auth.getName());
        Account account = accountServices.findByNumber(loanApplicationDTO.getDestinateAccount());
        Loan loan = loanServices.findById(loanApplicationDTO.getLoanID());

        if (amount == 0 || payments == 0){
            return new ResponseEntity<>("Invalid loan application data", HttpStatus.FORBIDDEN);
        }
        if (loan == null){
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }
        if (amount > loan.getMaxAmount()){
            return new ResponseEntity<>("Loan amount exceeds maximum amount", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(payments)){
            return new ResponseEntity<>("Invalid payment",HttpStatus.FORBIDDEN);
        }
        if (account == null){
            return  new ResponseEntity<>("Account not found",HttpStatus.FORBIDDEN);
        }
        String authenticatedUsername = auth.getName();
        if (!account.getClient().equals(client)){
            return new ResponseEntity<>("Destination account does not belong to authenticated user",HttpStatus.FORBIDDEN);
        }
        ClientLoan existingClientLoan =clientLoanServices.findByLoanAndClient(loan,client);
        if (existingClientLoan != null){
            return new ResponseEntity<>("Loan Application already exists",HttpStatus.FORBIDDEN);
        }
        int totalAmount = (int)(amount*1.20);
        ClientLoan clientLoan = new ClientLoan(totalAmount,payments,loan.getName());
        clientLoan.setClient(client);
        clientLoan.setLoan(loan);
        clientLoanServices.saveClientLoan(clientLoan);

        Transaction creditTLoan = new Transaction(TransactionType.CREDIT,amount,loan.getName()+" loan approved", LocalDateTime.now());
        account.addTransaction(creditTLoan);
        transactionServices.saveTransaction(creditTLoan);

        double balance = account.getBalance();
        double newBalance = balance + amount;
        account.setBalance(newBalance);
        accountServices.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
