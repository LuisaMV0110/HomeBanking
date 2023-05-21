package com.mindhub.HomeBanking.dtos;
import com.mindhub.HomeBanking.models.Transaction;
import com.mindhub.HomeBanking.models.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {
private long id;
private TransactionType type;
private double amount;
private String description;
private LocalDateTime date;
private double totalBalance;
private boolean transactionActive;
public TransactionDTO(){}
public TransactionDTO(Transaction transaction){
    this.id = transaction.getId();
    this.type = transaction.getType();
    this.amount = transaction.getAmount();
    this.description = transaction.getDescription();
    this.date = transaction.getDate();
    this.totalBalance = transaction.getTotalBalance();
    this.transactionActive = transaction.isTransactionActive();
}
    public long getId() {return id;}
    public TransactionType getType() {return type;}
    public double getAmount() {return amount;}
    public String getDescription() {return description;}
    public LocalDateTime getDate() {return date;}
    public double getTotalBalance() {return totalBalance;}
    public boolean isTransactionActive() {return transactionActive;}
}
