package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.dtos.TransactionDTO;
import com.mindhub.HomeBanking.models.Transaction;

import java.util.List;

public interface TransactionServices {
    void saveTransaction (Transaction transaction);
    List<TransactionDTO> getTransactionDTO();
    List <Transaction> findByAccountId(Long accountId);
}
