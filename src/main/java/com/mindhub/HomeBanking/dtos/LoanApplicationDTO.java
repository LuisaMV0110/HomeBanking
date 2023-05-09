package com.mindhub.HomeBanking.dtos;

public class LoanApplicationDTO {
    private long loanID;
    private Double amount;
    private Integer payments;
    private String destinateAccount;
    public LoanApplicationDTO() {
    }
    public LoanApplicationDTO(long loanID, Double amount, Integer payments, String destinateAccount) {
        this.loanID = loanID;
        this.amount = amount;
        this.payments = payments;
        this.destinateAccount = destinateAccount;
    }
    public long getLoanID() {return loanID;}
    public Double getAmount() {return amount;}
    public Integer getPayments() {return payments;}
    public String getDestinateAccount() {return destinateAccount;}
}

