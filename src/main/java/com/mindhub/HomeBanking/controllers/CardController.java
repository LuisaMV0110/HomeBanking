package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.CardDTO;
import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.*;
import com.mindhub.HomeBanking.repositories.CardRepository;
import com.mindhub.HomeBanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    public static String randomCardNumber(){
        String cardNumber = "";
        for (int i = 0; i < 4; i++) {
            cardNumber += (int) (Math.random() * 8999 + 1000) + " ";
        }
        return cardNumber;
    }
    public static int randomCvv(){
        int min = 100;
        int max = 899;
        return (int) (Math.random() * 899 + 100);
    }
    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCards (Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName())).getCards().stream().collect(toList());
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication, @RequestParam String type, @RequestParam String color) {
        if ( !type.equals("CREDIT")  && !type.equals("DEBIT")) {
            return new ResponseEntity<>(type + " is an incorrect type of card", HttpStatus.FORBIDDEN);
        }
        if ( !color.equals("TITANIUM") && !color.equals("GOLD") && !color.equals("SILVER")) {
            return new ResponseEntity<>(color + " is an incorrect color of card", HttpStatus.FORBIDDEN);
        }
        String cardNumber;
//        Evitar que se repitan números de tarjeta
        do {
            cardNumber = randomCardNumber();
        }
        while (cardRepository.findByNumber(cardNumber) != null);
//        CVV
        int cvvNumber = randomCvv();

        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getCards().size() <= 5){
            for (Card card : client.getCards()) {
                if (card.getType().equals(CardType.valueOf(type)) && card.getColor().equals(CardColor.valueOf(color))) {
                    return new ResponseEntity<>("You already have a card of the same type and color", HttpStatus.FORBIDDEN);
                }
            }
        Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), CardType.valueOf(type), CardColor.valueOf(color), cardNumber, cvvNumber, LocalDate.now(), LocalDate.now().plusYears(5));
            client.addCard(newCard);
        cardRepository.save(newCard);
    }
        else{
            return new ResponseEntity<>("You cannot create more than 6 cards", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
