package System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class StaffDashboard {
    private BankingSystem bankingSystem;

    public StaffDashboard(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    public void showStaffFunctions() {
        JFrame frame = new JFrame("Staff Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);  // This will center the frame on the screen


        // Buttons for staff functionalities
        JButton checkAccountInfoButton = new JButton("Check Account Info");
        JButton depositCashButton = new JButton("Deposit Cash");
        JButton withdrawCashButton = new JButton("Withdraw Cash");
        JButton checkLogsButton = new JButton("Check Logs of User");
        JButton viewDeletedAccountsButton = new JButton("View Deleted Accounts Logs");
        JButton listAllAccountsButton = new JButton("List All Accounts");
        JButton backToMainMenuButton = new JButton("Go Back to Main Menu");

        // Add buttons to the frame
        frame.add(checkAccountInfoButton);
        frame.add(depositCashButton);
        frame.add(withdrawCashButton);
        frame.add(checkLogsButton);
        frame.add(viewDeletedAccountsButton);
        frame.add(listAllAccountsButton);
        frame.add(backToMainMenuButton);

        // Action for Check Account Info button
        checkAccountInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountId = JOptionPane.showInputDialog("Enter Account ID:");
                BankingSystem.Account account = bankingSystem.searchAccount(accountId);
                if (account != null) {
                    JOptionPane.showMessageDialog(frame, account.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Account not found.");
                }
            }
        });

        // Action for Deposit Cash button
        depositCashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountId = JOptionPane.showInputDialog("Enter Account ID:");
                double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Deposit Amount:"));
                String userId = "staff"; // Use the logged-in user ID
                bankingSystem.depositAmount(accountId, amount, userId);
                JOptionPane.showMessageDialog(frame, "Amount deposited successfully!");
            }
        });

        // Action for Withdraw Cash button
        withdrawCashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountId = JOptionPane.showInputDialog("Enter Account ID:");
                double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Withdrawal Amount:"));
                String userId = "staff"; // Use the logged-in user ID
                bankingSystem.withdrawAmount(accountId, amount, userId);
                JOptionPane.showMessageDialog(frame, "Amount withdrawn successfully!");
            }
        });

        // Action for Check Logs of User button
        checkLogsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = JOptionPane.showInputDialog("Enter User ID:");
                List<String> logs = bankingSystem.getUserLogs(userId);  // Fetch logs for the user

                if (logs.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No logs found for the user.");
                } else {
                    String logDetails = String.join("\n", logs);  // Join the logs into a string to display
                    JOptionPane.showMessageDialog(frame, logDetails);
                }
            }
        });

        // Action for View Deleted Accounts Logs button
        viewDeletedAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String logs = String.join("\n", bankingSystem.showDeletedLogs());
                if (logs.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No deleted accounts logs available.");
                } else {
                    JOptionPane.showMessageDialog(frame, logs);
                }
            }
        });

        // Action for List All Accounts button
        listAllAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder allAccounts = new StringBuilder();
                for (BankingSystem.Account account : bankingSystem.showAllAccounts()) {
                    allAccounts.append(account.toString()).append("\n");
                }
                if (allAccounts.length() == 0) {
                    JOptionPane.showMessageDialog(frame, "No accounts available.");
                } else {
                    JOptionPane.showMessageDialog(frame, allAccounts.toString());
                }
            }
        });

        // Action for Go Back to Main Menu button
        backToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new BankingSystemGUI(); // Navigate back to the login screen
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
