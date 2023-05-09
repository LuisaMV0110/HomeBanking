package com.mindhub.HomeBanking.services.implement;

import com.mindhub.HomeBanking.models.Card;
import com.mindhub.HomeBanking.repositories.CardRepository;
import com.mindhub.HomeBanking.services.CardServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServicesImplement implements CardServices {
    @Autowired
    CardRepository cardRepository;
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
    @Override
    public Card findByNumberCard(String number) {
        return cardRepository.findByNumber(number);
    }
}
