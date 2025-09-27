package com.panel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DonationsPanel extends JFrame {
    private JTextField donorIdField, amountField;
    private JButton addBtn, viewBtn;
    private JTextArea displayArea;

    public DonationsPanel() {
        setTitle("Donations Management");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Donor ID:"));
        donorIdField = new JTextField();
        add(donorIdField);

        add(new JLabel("Donation Amount:"));
        amountField = new JTextField();
        add(amountField);

        addBtn = new JButton("Add Donation");
        add(addBtn);

        viewBtn = new JButton("View Donations");
        add(viewBtn);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        addBtn.addActionListener(e -> addDonation());
        viewBtn.addActionListener(e -> viewDonations());
    }

    private void addDonation() {
        int donorId;
        double amount;
        try {
            donorId = Integer.parseInt(donorIdField.getText());
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers for Donor ID and Amount.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/donation";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "INSERT INTO donations (donor_id, amount, donation_date) VALUES (?, ?, CURDATE())";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, donorId);
            pst.setDouble(2, amount);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Donation added successfully!");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void viewDonations() {
        String url = "jdbc:mysql://localhost:3306/donations";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "SELECT d.id, d.donor_id, u.name AS donor_name, d.amount, d.donation_date " +
                           "FROM donations d JOIN donors u ON d.donor_id = u.id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            displayArea.setText("");
            while (rs.next()) {
                displayArea.append(
                        "Donation ID: " + rs.getInt("id") +
                        ", Donor ID: " + rs.getInt("donor_id") +
                        ", Name: " + rs.getString("donor_name") +
                        ", Amount: " + rs.getDouble("amount") +
                        ", Date: " + rs.getDate("donation_date") + "\n");
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}
