package ru.otus.atm;

import ru.otus.atm.exception.IllegalBanknoteException;
import ru.otus.atm.exception.InsufficientBanknoteException;
import ru.otus.atm.exception.InsufficientFundsException;

import java.util.*;

import static ru.otus.atm.Nominal.*;

public class Atm {

    private final Map<Nominal, Stack<Banknote>> banknotesCellMap = new TreeMap<>(Comparator.comparingInt(n -> -n.getValue()));
    private final BanknotePicker banknotesPicker;
    private Account account = new Account("Ivanov Ivan", 11_000);

    public Atm(BanknotePicker banknotesPicker) {
        banknotesCellMap.put(HUNDRED, new Stack<>());
        banknotesCellMap.put(TWO_HUNDRED, new Stack<>());
        banknotesCellMap.put(FIVE_HUNDRED, new Stack<>());
        banknotesCellMap.put(THOUSAND, new Stack<>());
        banknotesCellMap.put(FIVE_THOUSAND, new Stack<>());

        this.banknotesPicker = banknotesPicker;
    }

    public List<Banknote> cashIn(List<Banknote> banknotes) {
        int amountAdd = 0;
        List<Banknote> returnedBanknotes = new ArrayList<>();
        for (var banknote : banknotes) {
            try {
                addToCell(banknote, banknotesCellMap);
            } catch (IllegalBanknoteException e) {
                returnedBanknotes.add(banknote);
            }
            amountAdd += banknote.getNominal().getValue();
        }
        account.add(amountAdd);
        return returnedBanknotes;
    }

    public List<Banknote> cashWithdrawal(int amount) {
        checkWithdrawalAmount(amount);
        return banknotesPicker.pick(amount, banknotesCellMap);
    }

    public long getAccountBalance() {
        return account.getAmount();
    }

    public int getAtmBalance() {
        int sum = 0;
        for (var banknoteCellEntry : banknotesCellMap.entrySet()) {
            sum += banknoteCellEntry.getKey().getValue() * banknoteCellEntry.getValue().size();
        }
        return sum;
    }

    public Map<Nominal, Integer> getBanknotesCount() {
        HashMap<Nominal, Integer> banknotesCountMap = new HashMap<>();
        for (var banknoteCellEntry : banknotesCellMap.entrySet()) {
            banknotesCountMap.put(banknoteCellEntry.getKey(), banknoteCellEntry.getValue().size());
        }
        return banknotesCountMap;
    }

    private void checkWithdrawalAmount(int amount) {
        if (amount > account.getAmount()) {
            throw new InsufficientFundsException();
        }
        if (amount > getAtmBalance()) {
            throw new InsufficientBanknoteException();
        }
        if ((amount % 100) != 0) {
            throw new IllegalArgumentException("Некорректная сумма. Доступные номиналы купюр " + banknotesCellMap.keySet());
        }
    }

    private void addToCell(Banknote banknote, Map<Nominal, Stack<Banknote>> banknoteCellMap) {
        var cell = banknoteCellMap.get(banknote.getNominal());
        if (cell == null)
            throw new IllegalBanknoteException();
        cell.push(banknote);
    }
}
