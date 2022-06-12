package ru.otus;

import ru.otus.testprocessor.annotations.After;
import ru.otus.testprocessor.annotations.Befor;
import ru.otus.testprocessor.annotations.Test;

public class TestExample {

    @Befor
    public void beforTest() {
        System.out.print("beforTest. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Test
    public void setterCustomerTest() {
        System.out.print("setterCustomerTest. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Test
    public void customerAsKeyTest() {
        System.out.print("customerAsKeyTest. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    @Test
    public void customerException() {
        System.out.print("customerException. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
        throw new IllegalArgumentException();
    }

    @After
    public void afterTest() {
        System.out.print("afterTest. ");
        System.out.println("Экземпляр тестового класса: " + Integer.toHexString(hashCode()));
    }

    public void withoutAnnotation() {
        System.out.println("withoutAnnotation");
    }
}
