package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private boolean accountActive;
    private AccountType accountType;
    private Set<TransactionDTO> transactions;
    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.accountActive = account.isAccountActive();
        this.accountType = account.getAccountType();
        this.transactions= account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(toSet());
    }
    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public double getBalance() {
        return balance;
    }
    public boolean isAccountActive() {return accountActive;}
    public AccountType getAccountType() {return accountType;}
    public Set<TransactionDTO> getTransactions() {return transactions;}
}
