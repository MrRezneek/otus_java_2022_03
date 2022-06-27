package ru.otus.atm;

import ru.otus.atm.exception.InsufficientBanknoteException;
import ru.otus.atm.exception.InsufficientFundsException;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        var atm = new Atm(new SimpleBanknotesPicker());

        System.out.println("Сумма наличных в банкомате: " + atm.getAtmBalance());
        System.out.println("Баланс счета: " + atm.getAccountBalance());
        System.out.println();

        System.out.println("Пополнение:");
        var cashIn = Arrays.asList(
                new Banknote(Nominal.THOUSAND),
                new Banknote(Nominal.FIVE_HUNDRED),
                new Banknote(Nominal.FIVE_HUNDRED),
                new Banknote(Nominal.HUNDRED),
                new Banknote(Nominal.FIVE_THOUSAND)
        );
        for (var banknote : cashIn) {
            System.out.println(banknote.getNominal());
        }
        System.out.println();

        var returnedBanknotes = atm.cashIn(cashIn);

        System.out.println("Сумма наличных в банкомате: " + atm.getAtmBalance());
        System.out.println("Баланс счета: " + atm.getAccountBalance());
        System.out.println();

        System.out.println("В банкомате:");
        System.out.println(atm.getBanknotesCount());
        System.out.println();

        System.out.println("Снятие:");
        try {
            var cash = atm.cashWithdrawal(1100); //3100, 150
            for (var banknote : cash) {
                System.out.println(banknote.getNominal());
            }
            System.out.println();
        } catch (InsufficientFundsException | IllegalArgumentException | InsufficientBanknoteException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("В банкомате:");
        System.out.println(atm.getBanknotesCount());
    }
}
