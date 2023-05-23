package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.LoanApplicationDTO;
import com.mindhub.HomeBanking.dtos.LoanDTO;
import com.mindhub.HomeBanking.models.*;
import com.mindhub.HomeBanking.services.*;
import com.mindhub.HomeBanking.utils.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        Set<ClientLoan> clientLoans =  client.getClientLoans().stream().filter(clientLoan -> clientLoan.getLoan().getName().equalsIgnoreCase(loan.getName()) && clientLoan.getFinalAmount() > 0).collect(Collectors.toSet());

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
        if (!account.getClient().equals(client)){
            return new ResponseEntity<>("Destination account does not belong to authenticated user",HttpStatus.FORBIDDEN);
        }
        if (clientLoans.size() == 5){
            return new ResponseEntity<>("You cannot have 5 active loans",HttpStatus.FORBIDDEN);
        }
        if (clientLoans.size() > 0){
            return new ResponseEntity<>("You already have a loan of this type",HttpStatus.FORBIDDEN);
        }

        double totalAmount = loanApplicationDTO.getAmount() * LoanUtils.calculateInterest(loanApplicationDTO.getPayments(), loan.getInterest());
        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount(),loanApplicationDTO.getPayments(),loan.getName(), totalAmount);
        clientLoan.setClient(client);
        clientLoan.setLoan(loan);
        clientLoanServices.saveClientLoan(clientLoan);

        double balance = account.getBalance();
        double newBalance = balance + amount;

        Transaction creditTLoan = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loan.getName()+" Loan Approved", LocalDateTime.now(),newBalance,true);
        account.addTransaction(creditTLoan);
        transactionServices.saveTransaction(creditTLoan);
        account.setBalance(newBalance);
        accountServices.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("manager/loans")
    public ResponseEntity<Object> newLoan(@RequestBody Loan loan){

        if (loan.getMaxAmount() <= 0){
            return new ResponseEntity<>("Please, enter a valid amount",HttpStatus.FORBIDDEN);
        }
        if (loan.getName().isBlank()){
            return new ResponseEntity<>("Please, enter a name",HttpStatus.FORBIDDEN);
        }
        if (loanServices.findByName(loan.getName()) != null){
            return new ResponseEntity<>("There is already a loan with this name, choose another",HttpStatus.FORBIDDEN);
        }
        if (loan.getPayments().isEmpty()){
            return new ResponseEntity<>("Please, Enter a minimum number of payments",HttpStatus.FORBIDDEN);
        }
        for (int payment:loan.getPayments()){
            if (payment > 60){
                return new ResponseEntity<>("The maximum number of payments is 60",HttpStatus.FORBIDDEN);
            }
        }
        if (loan.getInterest() <= 1.0){
            return new ResponseEntity<>("Please, select the interest rate of your loan",HttpStatus.FORBIDDEN);
        }
        Loan newLoan = new Loan(loan.getName(),loan.getMaxAmount(),loan.getPayments(),loan.getInterest());
        loanServices.saveLoan(newLoan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Transactional
    @PostMapping("loan/pay")
    public ResponseEntity<Object> payLoan(Authentication auth, @RequestParam Long id, @RequestParam String account, @RequestParam Double amount){
        Client client = clientServices.findByEmail(auth.getName());
        ClientLoan clientLoan = clientLoanServices.getClientLoan(id);
        Account authAccount = accountServices.findByNumber(account);
        String description = "Pay " + clientLoan.getLoan().getName().toLowerCase() + " loan";

        if (client == null){
            return new ResponseEntity<>("Sorry, this client does not exist",HttpStatus.FORBIDDEN);
        }
        if (account.isBlank()){
            return new ResponseEntity<>("Please, enter an account",HttpStatus.FORBIDDEN);
        } else if (client.getAccounts().stream().filter(account1 -> account1.getNumber().equalsIgnoreCase(account)).collect(toList()).size() == 0) {
            return new ResponseEntity<>("Sorry, this account is not yours",HttpStatus.FORBIDDEN);
        }
        if (clientLoan.getFinalAmount() <= 0){
            return new ResponseEntity<>("Sorry, this loan has already been paid",HttpStatus.FORBIDDEN);
        }
        if (amount < 1){
            return new ResponseEntity<>("Please, enter an amount bigger than 0",HttpStatus.FORBIDDEN);
        } else if (authAccount.getBalance() < amount){
            return new ResponseEntity<>("You do not have enough balance in your account",HttpStatus.FORBIDDEN);
        }
        authAccount.setBalance(authAccount.getBalance() - amount);

        Transaction newTransaction = new Transaction(TransactionType.DEBIT, amount, description, LocalDateTime.now(), authAccount.getBalance(),true);
        authAccount.addTransaction(newTransaction);
        transactionServices.saveTransaction(newTransaction);

        clientLoan.setPayments((clientLoan.getPayments()) - 1);
        if (clientLoan.getPayments() == 0){
            clientLoan.setFinalAmount(0.0);

        } else{
            clientLoan.setFinalAmount(clientLoan.getFinalAmount() - amount);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
