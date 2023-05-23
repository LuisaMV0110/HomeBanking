package com.mindhub.HomeBanking.utils;

public final class LoanUtils {
    public  static double calculateInterest(int payment, double baseInterest) {
        double incrementFactor = 0.01; // 1%

        return baseInterest + (payment * incrementFactor);
    }
}
