package ru.otus.atm;

import ru.otus.atm.exception.InsufficientBanknoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static ru.otus.atm.Nominal.HUNDRED;

public class SimpleBanknotesPicker implements BanknotePicker{
    public List<Banknote> pick(int amount, Map<Nominal, Stack<Banknote>> banknotesCellMap) {
        var banknotesWithdrawal = new ArrayList<Banknote>();

        for (var nominal : banknotesCellMap.keySet()) {
            if (amount >= nominal.getValue()) {
                Stack<Banknote> banknoteCell = banknotesCellMap.get(nominal);
                int banknotesNeedCount = amount / nominal.getValue();
                int banknotesInCellCount = banknoteCell.size();
                int banknotesWithdrawalCount = 0;

                if (nominal == HUNDRED && banknotesNeedCount > banknotesInCellCount) {
                    throw new InsufficientBanknoteException();
                }

                banknotesWithdrawalCount = Math.min(banknotesInCellCount, banknotesNeedCount);

                for (int i = 0; i < banknotesWithdrawalCount; i++) {
                    banknotesWithdrawal.add(banknoteCell.pop());
                }
                amount -= nominal.getValue() * banknotesWithdrawalCount;
            }
        }
        return banknotesWithdrawal;
    }
}
