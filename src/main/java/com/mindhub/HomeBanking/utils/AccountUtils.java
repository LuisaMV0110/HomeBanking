package com.mindhub.HomeBanking.utils;

import java.util.Random;
public final class AccountUtils {
    private AccountUtils(){}
    public static String getRandomNumber(){
        Random randomNumber = new Random();
        return ("VIN-" + randomNumber.nextInt(999989 + 10));
    }
}
