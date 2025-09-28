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

        String[] columns = {"DonationID", "Type", "Amount", "CreatedAt"};
        tableModel = new DefaultTableModel(columns, 0);
        donationsTable = new JTable(tableModel);
        scrollPane = new JScrollPane(donationsTable);
        add(scrollPane, BorderLayout.CENTER);

        loadDonations();
    }

    private void loadDonations() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            String query = "SELECT donationid, types, amount, createdAt FROM donation";
            ResultSet rs = stmt.executeQuery(query);

            tableModel.setRowCount(0); 
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("donationid"),
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

