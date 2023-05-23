package com.mindhub.HomeBanking.controllers;

import com.mindhub.HomeBanking.dtos.CardDTO;
import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.*;
import com.mindhub.HomeBanking.services.CardServices;
import com.mindhub.HomeBanking.services.ClientServices;
import com.mindhub.HomeBanking.utils.CardUtils;
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
    CardServices cardServices;
    @Autowired
    private ClientServices clientServices;
    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCards (Authentication authentication) {
        return new ClientDTO(clientServices.findByEmail(authentication.getName())).getCards().stream().collect(toList());
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
            cardNumber = CardUtils.getCardNumber();
        }
        while (cardServices.findByNumberCard(cardNumber) != null);
//        CVV
        int cvvNumber = CardUtils.getCvv();

        Client client = clientServices.findByEmail(authentication.getName());
        int allCards = client.getCards().size();
        int activeCards = (int) client.getCards().stream().filter(Card::isCardActivate).count();
        if(allCards >= 10 || activeCards >= 10){
            return new ResponseEntity<>("You have reached the maximum number of cards to create",HttpStatus.FORBIDDEN);
        }
        if (client.getCards().size() <= 5){
            for (Card card : client.getCards()) {
                if (card.getType().equals(CardType.valueOf(type)) && card.getColor().equals(CardColor.valueOf(color)) && card.isCardActivate()) {
                    return new ResponseEntity<>("You already have a card of the same type and color", HttpStatus.FORBIDDEN);
                }
            }
        Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), CardType.valueOf(type), CardColor.valueOf(color), cardNumber, cvvNumber, LocalDate.now(), LocalDate.now().plusYears(5),true);
            client.addCard(newCard);
        cardServices.saveCard(newCard);
    }
        else{
            return new ResponseEntity<>("You cannot create more than 6 cards", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard(Authentication auth, @PathVariable long id){
        Client client = clientServices.findByEmail((auth.getName()));
        Card card = cardServices.findById(id);
        if(!client.getCards().contains(card)){
            return new ResponseEntity<>("Sorry, this card is not yours", HttpStatus.FORBIDDEN);
        }
        if (card == null){
            return new ResponseEntity<>("Sorry, this card is not found", HttpStatus.FORBIDDEN);
        }
        if (!card.isCardActivate()){
            return new ResponseEntity<>("Sorry, this card is inactive", HttpStatus.FORBIDDEN);
        }
        card.setCardActivate(false);
        cardServices.saveCard(card);
        return new ResponseEntity<>("Card removed", HttpStatus.ACCEPTED);
    }
}
