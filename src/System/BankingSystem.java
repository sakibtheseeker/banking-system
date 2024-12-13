package System;

import java.util.*;
import javax.swing.*;

public class BankingSystem {
    private static BankingSystem instance; // Singleton instance
    private Map<String, Account> accounts;
    private List<String> deletedAccountsLogs;
    private Map<String, String> users;
    private Map<String, User> userObjects;
    private Map<String, List<String>> userLogs;  // To store logs for each user

    private BankingSystem() {
        accounts = new HashMap<>();
        deletedAccountsLogs = new ArrayList<>();
        users = new HashMap<>();
        userObjects = new HashMap<>();
        userLogs = new HashMap<>();  // Initialize the userLogs map

        // Default users
        users.put("admin", "admin123");
        users.put("staff", "staff123");

        userObjects.put("admin", new User("admin", "Admin User"));
        userObjects.put("staff", new User("staff", "Staff User"));
    }

    // Singleton instance getter
    public static synchronized BankingSystem getInstance() {
        if (instance == null) {
            instance = new BankingSystem();
        }
        return instance;
    }

    public User authenticateUser(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            return userObjects.get(username);
        }
        return null;
    }

    public void createAccount(String accountId, String holderName, double balance) {
        Account newAccount = new Account(accountId, holderName, balance);
        accounts.put(accountId, newAccount);
        logUserAction(holderName, "Created a new account with ID: " + accountId);  // Log the action
    }

    // Method to edit account information
    public void editAccount(String accountId) {
        Account accountToEdit = accounts.get(accountId);
        if (accountToEdit != null) {
            String newHolderName = JOptionPane.showInputDialog("Enter new holder name for account " + accountId + ": ");
            if (newHolderName != null && !newHolderName.trim().isEmpty()) {
                accountToEdit.setHolderName(newHolderName);
                logUserAction(accountToEdit.getHolderName(), "Edited account " + accountId + " holder name.");  // Log the action
                JOptionPane.showMessageDialog(null, "Account edited successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid holder name.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    // Method to deposit amount into an account
    public void depositAmount(String accountId, double amount, String userId) {
        Account account = accounts.get(accountId);
        if (account != null) {
            account.deposit(amount);
            logUserAction(userId, "Deposited " + amount + " into account: " + accountId);  // Log the action
            JOptionPane.showMessageDialog(null, "Amount deposited successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Account not found!");
        }
    }

    // Method to withdraw amount from an account
    public void withdrawAmount(String accountId, double amount, String userId) {
        Account account = accounts.get(accountId);
        if (account != null) {
            if (account.withdraw(amount)) {
                logUserAction(userId, "Withdrew " + amount + " from account: " + accountId);  // Log the action
                JOptionPane.showMessageDialog(null, "Amount withdrawn successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient balance!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Account not found!");
        }
    }

    // Method to delete an account
    public void deleteAccount(String accountId) {
        Account accountToDelete = accounts.remove(accountId);
        if (accountToDelete != null) {
            deletedAccountsLogs.add("Deleted account: " + accountToDelete.toString());
            logUserAction(accountToDelete.getHolderName(), "Deleted account: " + accountId);  // Log the action
        }
    }

    public Account searchAccount(String accountId) {
        return accounts.get(accountId);
    }

    public Collection<Account> showAllAccounts() {
        return accounts.values();
    }

    public List<String> showDeletedLogs() {
        return deletedAccountsLogs;
    }

    // Method to log actions for a specific user
    public void logUserAction(String userId, String action) {
        userLogs.putIfAbsent(userId, new ArrayList<>());  // Initialize the log list for the user if it's not already initialized
        userLogs.get(userId).add(action);  // Add the action to the user's log
        System.out.println("Logged action for user " + userId + ": " + action);  // Debugging
    }

    // Method to fetch logs for a specific user
    public List<String> getUserLogs(String userId) {
        return userLogs.getOrDefault(userId, new ArrayList<>());  // Return the user's logs or an empty list if no logs exist
    }

    // Method to show all logs for a specific user in a dialog box
    public void showUserLogs(String userId) {
        List<String> logs = getUserLogs(userId);
        if (logs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No logs found for user: " + userId);
        } else {
            String logDetails = String.join("\n", logs);
            JOptionPane.showMessageDialog(null, "Logs for user " + userId + ":\n" + logDetails);
        }
    }

    public static class Account {
        private String accountId;
        private String holderName;
        private double balance;

        public Account(String accountId, String holderName, double balance) {
            this.accountId = accountId;
            this.holderName = holderName;
            this.balance = balance;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getHolderName() {
            return holderName;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            this.balance += amount;
        }

        public boolean withdraw(double amount) {
            if (this.balance >= amount) {
                this.balance -= amount;
                return true;
            }
            return false;
        }

        // Setter for holderName
        public void setHolderName(String holderName) {
            this.holderName = holderName;
        }

        @Override
        public String toString() {
            return "Account{accountId='" + accountId + "', holderName='" + holderName + "', balance=" + balance + "}"; 
        }
    }

    public static class User {
        private String username;
        private String fullName;

        public User(String username, String fullName) {
            this.username = username;
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }

        public String getRole() {
            if ("admin".equals(username)) return "admin";
            if ("staff".equals(username)) return "staff";
            return "unknown";
        }
    }
}
