package com.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.form.DB;

public class DonorPanel extends JPanel {

    private JTable donorTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public DonorPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"DonorID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        donorTable = new JTable(tableModel);
        scrollPane = new JScrollPane(donorTable);
        add(scrollPane, BorderLayout.CENTER);

        loadDonors();
    }

    private void loadDonors() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = stmt.executeQuery("SELECT donorid, name, email, phone FROM donor");

            tableModel.setRowCount(0); 
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("donorid"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                };
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}

