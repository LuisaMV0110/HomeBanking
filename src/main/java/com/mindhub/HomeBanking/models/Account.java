package com.mindhub.HomeBanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
public class Account{
@Id
@GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
@GenericGenerator(name = "native", strategy = "native")
private long id;
private String number;
private LocalDateTime creationDate;
private double balance;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id",nullable = false)
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    public Account(){}
    public Account(String number, LocalDateTime date, double balance){
        this.number = number;
        this.creationDate = date;
        this.balance = balance;
    }
    public static String randomNumber(){
        Random randomNumber = new Random();
        int min = 0;
        int max = 999999;
        return ("VIN-" + randomNumber.nextInt(max + min));
    }

    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {this.balance = balance;}
    @JsonIgnore
    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}
    public Set<Transaction> getTransactions() {return transactions;}
    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", client=" + client +
                '}';
    }
}
