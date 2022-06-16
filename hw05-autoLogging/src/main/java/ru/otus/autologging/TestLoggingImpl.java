package ru.otus.autologging;

public class TestLoggingImpl implements TestLogging {

    @Log
    public void calculation(int param1) {
        System.out.println(param1);
    }

    public void calculation(int param1, int param2) {
        System.out.println(param1 + ", " + param2);
    }

    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println(param1 + ", " + param2 + ", " + param3);
    }
}
