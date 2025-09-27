package com.panel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BeneficiaryPanel extends JFrame {
    private JTextField nameField, emailField, phoneField, aidField;
    private JButton addBtn, viewBtn;
    private JTextArea displayArea;

    public BeneficiaryPanel() {
        setTitle("Beneficiaries Management");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Input fields
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Aid Received:"));
        aidField = new JTextField();
        add(aidField);

        addBtn = new JButton("Add Beneficiary");
        add(addBtn);

        viewBtn = new JButton("View Beneficiaries");
        add(viewBtn);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        // Button actions
        addBtn.addActionListener(e -> addBeneficiary());
        viewBtn.addActionListener(e -> viewBeneficiaries());
    }

    private void addBeneficiary() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String aid = aidField.getText();

        String url = "jdbc:mysql://localhost:3306/donation";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "INSERT INTO beneficiary (name, email, phone, aid_received) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, aid);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Beneficiary added successfully!");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void viewBeneficiaries() {
        String url = "jdbc:mysql://localhost:3306/donation";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "SELECT * FROM beneficiary";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            displayArea.setText("");
            while (rs.next()) {
                displayArea.append(
                        "ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") +
                        ", Phone: " + rs.getString("phone") +
                        ", Aid: " + rs.getString("aid_received") + "\n");
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}

