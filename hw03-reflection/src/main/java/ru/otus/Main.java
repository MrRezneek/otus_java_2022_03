package ru.otus;

import ru.otus.testprocessor.TestProcessor;

public class Main {
    public static void main(String[] args) {

        try {
            TestProcessor.process(TestExample.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
