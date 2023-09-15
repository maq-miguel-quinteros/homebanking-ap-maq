package com.hb.maq.homebanking.utils;

public final class CardUtils {

    private CardUtils() {
    }

    public static int getCardCvv() {
        return (int) (Math.random() * 999 + 1);
    }

    public static String getCardNumber() {
        return ((int) (Math.random() * 9999 + 1)) + "-" + ((int) (Math.random() * 9999 + 1)) + "-" +
                ((int) (Math.random() * 9999 + 1)) + "-" + ((int) (Math.random() * 9999 + 1));
    }
}
