package System;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class AdminDashboard {
    private BankingSystem bankingSystem;

    public AdminDashboard() {
        this.bankingSystem = BankingSystem.getInstance(); // Access the Singleton instance
    }

    // Method to display Admin Panel options
    public void showAdminFunctions() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);  // This will center the frame on the screen


        // Create buttons for each admin function
        JButton createAccountButton = new JButton("Create Account");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton searchAccountButton = new JButton("Search Account");
        JButton showAllAccountsButton = new JButton("Show All Accounts");
        JButton editAccountButton = new JButton("Edit Account");
        JButton showDeletedLogsButton = new JButton("Show Deleted Logs");
        JButton backToMainMenuButton = new JButton("Go Back to Main Menu");

        // Add buttons to the frame
        frame.add(createAccountButton);
        frame.add(deleteAccountButton);
        frame.add(searchAccountButton);
        frame.add(showAllAccountsButton);
        frame.add(editAccountButton);
        frame.add(showDeletedLogsButton);
        frame.add(backToMainMenuButton);

        // Add action listeners to buttons
        createAccountButton.addActionListener(e -> createAccount());
        deleteAccountButton.addActionListener(e -> deleteAccount());
        searchAccountButton.addActionListener(e -> searchAccount());
        showAllAccountsButton.addActionListener(e -> showAllAccounts());
        editAccountButton.addActionListener(e -> editAccount());
        showDeletedLogsButton.addActionListener(e -> showDeletedLogs());
        backToMainMenuButton.addActionListener(e -> goBackToMainMenu(frame));

        // Show the admin dashboard
        frame.setVisible(true);
    }

    // Methods to handle each functionality
    private void createAccount() {
        String accountId = JOptionPane.showInputDialog("Enter Account ID: ");
        String holderName = JOptionPane.showInputDialog("Enter Account Holder's Name: ");
        double balance = Double.parseDouble(JOptionPane.showInputDialog("Enter Initial Balance: "));
        bankingSystem.createAccount(accountId, holderName, balance);
        JOptionPane.showMessageDialog(null, "Account Created Successfully!");
    }

    private void deleteAccount() {
        String accountId = JOptionPane.showInputDialog("Enter Account ID to delete: ");
        bankingSystem.deleteAccount(accountId);
        JOptionPane.showMessageDialog(null, "Account Deleted Successfully!");
    }

    private void searchAccount() {
        String accountId = JOptionPane.showInputDialog("Enter Account ID to search: ");
        BankingSystem.Account account = bankingSystem.searchAccount(accountId);
        if (account != null) {
            JOptionPane.showMessageDialog(null, account.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }

    private void showAllAccounts() {
        Collection<BankingSystem.Account> accounts = bankingSystem.showAllAccounts();
        if (accounts != null && !accounts.isEmpty()) {
            StringBuilder allAccountsInfo = new StringBuilder("All Accounts:\n");
            for (BankingSystem.Account account : accounts) {
                allAccountsInfo.append(account.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(null, allAccountsInfo.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No accounts to display.");
        }
    }

    private void editAccount() {
        String accountId = JOptionPane.showInputDialog("Enter Account ID to edit: ");
        bankingSystem.editAccount(accountId);
    }

    private void showDeletedLogs() {
        String logs = String.join("\n", bankingSystem.showDeletedLogs());
        if (logs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No deleted logs available.");
        } else {
            JOptionPane.showMessageDialog(null, logs);
        }
    }

    private void goBackToMainMenu(JFrame frame) {
        frame.dispose(); // Close the current admin dashboard
        new BankingSystemGUI(); // Navigate back to the login screen
    }
}
