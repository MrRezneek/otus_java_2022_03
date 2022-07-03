package ru.otus.atm;

import ru.otus.atm.exception.InsufficientBanknoteException;
import ru.otus.atm.exception.InsufficientFundsException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Demo {
    public static void main(String[] args) {

        var atm = new Atm(
                new HashSet<>(Arrays.asList(Nominal.values())),
                new SimpleBanknotesPicker()
        );

        System.out.println("Сумма наличных в банкомате: " + atm.getAtmBalance());
        System.out.println("Баланс счета: " + atm.getAccountBalance());
        System.out.println();

        System.out.println("Пополнение:");
        var cashIn = Arrays.asList(
                new Banknote(Nominal.THOUSAND),
                new Banknote(Nominal.TWO_HUNDRED)
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


        int amountWithdrawal = 230;
        System.out.println("Снятие: " + amountWithdrawal);
        try {
            var cash = atm.cashWithdrawal(amountWithdrawal); //3100, 150
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
