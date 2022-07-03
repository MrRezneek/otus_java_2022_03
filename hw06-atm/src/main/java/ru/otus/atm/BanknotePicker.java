package ru.otus.atm;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public interface BanknotePicker {
    List<Banknote> pick(int amount, Map<Nominal, BanknoteCell> banknotesCellMap);
}
