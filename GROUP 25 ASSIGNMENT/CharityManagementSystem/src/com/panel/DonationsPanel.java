package com.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.form.DB;

public class DonationsPanel extends JPanel {

    private JTable donationsTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public DonationsPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"DonationID", "donorid", "Type", "Amount", "CreatedAt"};
        tableModel = new DefaultTableModel(columns, 0);
        donationsTable = new JTable(tableModel);
        scrollPane = new JScrollPane(donationsTable);
        add(scrollPane, BorderLayout.CENTER);

        loadDonations();
    }

    public void loadDonations() {
        try (Connection con = DB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT donationid, donorid, types, amount, createdAt FROM donations")) {

            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("donationid"),
                        rs.getInt("donorid"),
                        rs.getString("types"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("createdAt")
                };
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}




