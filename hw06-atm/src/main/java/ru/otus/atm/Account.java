package ru.otus.atm;

public class Account {
    private String owner;

    private long amount;

    public String getOwner() {
        return owner;
    }

    public long getAmount() {
        return amount;
    }

    public void add(long amountAdd) {
        amount += amountAdd;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Account(String owner, long amount) {
        this.owner = owner;
        this.amount = amount;
    }
}
