package com.mindhub.HomeBanking.utils;

public final class CardUtils {
    private CardUtils() {
    }
    public static String getCardNumber() {
        String cardNumber = "";
        for (int i = 0; i < 4; i++) {
            cardNumber += (int) (Math.random() * 8999 + 1000) + " ";
        }
        return cardNumber;
    }
    public static int getCvv() {
        int min = 100;
        int max = 899;
        return (int) (Math.random() * 899 + 100);
    }
}
