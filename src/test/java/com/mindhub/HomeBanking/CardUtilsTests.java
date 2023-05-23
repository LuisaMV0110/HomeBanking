package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }
    @Test
    public void cardCvvIsCreated(){
        int cardCvv = CardUtils.getCvv();
        assertThat(cardCvv, notNullValue());
    }


}
