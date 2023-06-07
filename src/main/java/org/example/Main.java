package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger prettyNickLength3 = new AtomicInteger(0);
    static AtomicInteger prettyNickLength4 = new AtomicInteger(0);
    static AtomicInteger prettyNickLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread1 = new Thread(() -> {
            thisPalindromes(texts);
        });
        Thread thread2 = new Thread(() -> {
            sameChars(texts);
        });
        Thread thread3 = new Thread(() -> {
            charIncrease(texts);
        });

        thread1.start();
        thread2.start();
        thread3.start();


        thread3.join();
        thread2.join();
        thread1.join();

        System.out.format("Красивых ников с длиной 3: %d\n" +
                "Красивых ников с длиной 4: %d\n" +
                "Красивых ников с длиной 5: %d\n", prettyNickLength3.get(), prettyNickLength4.get(), prettyNickLength5.get());


    }

    public static void thisPalindromes(String[] text) {
        for (String nick : text) {
            if (nick.equals(new StringBuilder(nick).reverse().toString())) {
                addToLength(nick);
            }
        }
    }

    public static void sameChars(String[] text) {
        for (String nick : text) {
            char[] same = new char[nick.length()];
            Arrays.fill(same, (char) nick.indexOf(0));
            if (nick.equals(same.toString())) {
                addToLength(nick);
            }
        }
    }

    public static void charIncrease(String[] text) {
        for (String nick : text) {
            char[] nickSort = nick.toCharArray();
            Arrays.sort(nickSort);
            if (nick.equals(nickSort.toString())) {
                addToLength(nick);
            }

        }
    }

    public static void addToLength(String text) {
        switch (text.length()) {
            case 3:
                prettyNickLength3.getAndIncrement();
                break;
            case 4:
                prettyNickLength4.getAndIncrement();
                break;
            case 5:
                prettyNickLength5.getAndIncrement();
                break;
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}