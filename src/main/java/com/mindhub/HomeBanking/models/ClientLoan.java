package com.mindhub.HomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String name;
    private double amount;
    private int payments;
    private double finalAmount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id",nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id",nullable = false)
    private Loan loan;
    public ClientLoan(){}
    public ClientLoan(double amount, int payments, double finalAmount) {
        this.amount = amount;
        this.payments = payments;
        this.finalAmount = finalAmount;
    }
    public ClientLoan(double amount, int payments, String name, double finalAmount) {
        this.amount = amount;
        this.payments = payments;
        this.finalAmount = finalAmount;
    }
    public long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public int getPayments() {
        return payments;
    }
    public void setPayments(int payments) {
        this.payments = payments;
    }
    public double getFinalAmount() {return finalAmount;}
    public void setFinalAmount(double finalAmount) {this.finalAmount = finalAmount;}
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", amount=" + amount +
                ", payments=" + payments +
                ", client=" + client +
                ", loan=" + loan +
                '}';
    }
}
