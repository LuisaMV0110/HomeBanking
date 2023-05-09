package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.Loan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LoanDTO {
    private long id;
    private String name;
    private double maxAmount;
    private Set<Integer> payments = new HashSet<>();

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    public long getId() {return id;}
    public String getName() {return name;}
    public double getMaxAmount() {return maxAmount;}
    public Set<Integer> getPayments() {return payments;}
}
