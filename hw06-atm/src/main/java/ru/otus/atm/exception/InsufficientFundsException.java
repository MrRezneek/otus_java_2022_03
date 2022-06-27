package ru.otus.atm.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Недостаточно средст на счёте");
    }
}
