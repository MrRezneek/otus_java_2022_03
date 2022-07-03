package ru.otus.atm;

import ru.otus.atm.exception.InsufficientBanknoteException;

import java.util.*;

public class SimpleBanknotesPicker implements BanknotePicker {
    public List<Banknote> pick(int amount, Map<Nominal, BanknoteCell> banknotesCellMap) {
        var banknotesWithdrawal = new ArrayList<Banknote>();
        Map<Nominal, Integer> banknotesWithdrawCountMap = new HashMap<>();
        var nominalIterator = banknotesCellMap.keySet().iterator();

        while (nominalIterator.hasNext()) {
            var nominal = nominalIterator.next();
            if (amount >= nominal.getValue()) {
                BanknoteCell banknoteCell = banknotesCellMap.get(nominal);
                int banknotesNeedCount = amount / nominal.getValue();
                int banknotesInCellCount = banknoteCell.getSize();
                int banknotesWithdrawalCount = 0;

                if (!nominalIterator.hasNext()) {
                    if (banknotesNeedCount > banknotesInCellCount) {
                        throw new InsufficientBanknoteException();
                    } else if ((amount % nominal.getValue()) != 0) {
                        throw new IllegalArgumentException("Некорректная сумма. Доступные номиналы купюр " + banknotesCellMap.keySet());
                    }
                }

                banknotesWithdrawalCount = Math.min(banknotesInCellCount, banknotesNeedCount);
                banknotesWithdrawCountMap.put(nominal, banknotesWithdrawalCount);
                amount -= nominal.getValue() * banknotesWithdrawalCount;
            } else if (!nominalIterator.hasNext()) {
                throw new IllegalArgumentException("Некорректная сумма. Доступные номиналы купюр " + banknotesCellMap.keySet());
            }
        }

        banknotesWithdrawCountMap.forEach((k, v) -> banknotesWithdrawal.addAll(banknotesCellMap.get(k).popBanknotes(v)));
        return banknotesWithdrawal;
    }
}
