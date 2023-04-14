package com.skypro.pastebinanalog.cryptograph;

import java.util.Random;

public class RandomHashGenerator {

    private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+[]{}|;:,.<>?/~`";
    private static final int HASH_LENGTH = 8;

    public static String generateHash() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int charIndex = 0;
        for (int i = 0; i < HASH_LENGTH; i++) {
            charIndex = random.nextInt(CHARSET.length());
            sb.append(CHARSET.charAt(charIndex));
        }
        return sb.toString();
    }

}