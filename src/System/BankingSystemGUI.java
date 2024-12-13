package System;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BankingSystemGUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private BankingSystem bankingSystem;

    public BankingSystemGUI() {
        // Use the Singleton instance of BankingSystem
        this.bankingSystem = BankingSystem.getInstance();

        frame = new JFrame("Banking Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Set layout to GridBagLayout for centering components
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add space between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ensure components stretch horizontally

        // Create heading label
        JLabel headingLabel = new JLabel("BANKING MANAGEMENT SYSTEM", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));  // Make the heading bold and larger
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Make heading span across both columns
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(headingLabel, gbc);

        // Create components for username and password fields
        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField(20);  // No placeholder text, just an empty field
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Set the preferred size for the login button to make it smaller
        loginButton.setPreferredSize(new Dimension(100, 30)); // Width 100, Height 30

        // Set GridBagConstraints for username label and field (center)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;  // Reset grid width to 1 for subsequent components
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(usernameField, gbc);

        // Set GridBagConstraints for password label and field (center)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(passwordField, gbc);

        // Set GridBagConstraints for login button (center)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Make login button span across both columns
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(loginButton, gbc);

        // Add event listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Username or Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Authenticate the user
                BankingSystem.User user = bankingSystem.authenticateUser(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(frame, "Login Successful! Welcome " + user.getFullName());
                    openDashboard(user); // Dynamically open the dashboard based on role
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Set frame visibility
        frame.setVisible(true);
    }

    // Method to dynamically open the dashboard based on the user's role
    private void openDashboard(BankingSystem.User user) {
        frame.dispose(); // Close the login frame

        String role = user.getRole();
        switch (role.toLowerCase()) {
            case "admin":
                new AdminDashboard().showAdminFunctions(); // Pass BankingSystem instance via Singleton
                break;
            case "staff":
                new StaffDashboard(bankingSystem).showStaffFunctions(); // Pass BankingSystem instance via Singleton
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unknown role: " + role, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new BankingSystemGUI();
    }
}
