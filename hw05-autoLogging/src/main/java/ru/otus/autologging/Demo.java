package ru.otus.autologging;

public class Demo {
    public static void main(String[] args) {
        Ioc.createMyClass().calculation(6);
        Ioc.createMyClass().calculation(6, 7);
        Ioc.createMyClass().calculation(6, 7, "8");

    }
}
