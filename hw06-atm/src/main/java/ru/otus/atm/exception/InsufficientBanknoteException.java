package ru.otus.atm.exception;

public class InsufficientBanknoteException extends RuntimeException {
    public InsufficientBanknoteException() {
        super("Недостаточно купюр в банкомате");
    }
}
