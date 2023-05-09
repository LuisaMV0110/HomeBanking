package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.models.Transaction;

public interface TransactionServices {
    void saveTransaction (Transaction transaction);
}
