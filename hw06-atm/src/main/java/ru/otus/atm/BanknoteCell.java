package ru.otus.atm;

import java.util.*;

public class BanknoteCell {
    private final Nominal nominal;
    private final Deque<Banknote> stack = new ArrayDeque<>();

    public BanknoteCell(Nominal nominal) {
        this.nominal = nominal;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public int getSize() {
        return stack.size();
    }

    public void push(Banknote banknote) {
        stack.push(banknote);
    }

    public void push(Collection<Banknote> banknotes) {
        stack.addAll(banknotes);
    }

    public Banknote pop() {
        return stack.pop();
    }

    public List<Banknote> popBanknotes(int count) {
        List<Banknote> result = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            result.add(stack.pop());
        }
        return result;
    }
}
