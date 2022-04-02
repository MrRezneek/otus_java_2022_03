package ru.otus;

import com.google.common.base.CharMatcher;

public class HelloOtus {
    public static void main(String[] args) {
        String input = "Hello-OTUS.";
        String result = CharMatcher.anyOf("-.").replaceFrom(input, '!');
        System.out.println(result);
    }
}
