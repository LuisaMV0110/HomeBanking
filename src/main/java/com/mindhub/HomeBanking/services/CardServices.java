package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.models.Card;

import java.util.List;

public interface CardServices {
    void saveCard(Card card);
    Card findByNumberCard(String number);
    Card findById(long id);
}
