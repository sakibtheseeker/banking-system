package System;
public class BankAccount {
    private int accountNumber;
    private String name;
    private String cnic;
    private String gender;
    private String accountType;
    private double balance;

    public BankAccount(int accountNumber, String name, String cnic, String gender, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.cnic = cnic;
        this.gender = gender;
        this.accountType = accountType;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }
}
