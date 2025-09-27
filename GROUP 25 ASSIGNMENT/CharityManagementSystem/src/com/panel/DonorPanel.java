package com.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.form.DB;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * DonorPanel.java
 * Handles donor management: add, view, delete donors.
 */
public class DonorPanel extends JPanel {
    private JTextField txtName, txtEmail, txtPhone, txtAddress;
    private JButton btnAdd, btnDelete, btnRefresh;
    private JTable donorTable;
    private DefaultTableModel tableModel;

    public DonorPanel() {
        setLayout(new BorderLayout());

        // Top form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        formPanel.add(txtAddress);

        btnAdd = new JButton("Add Donor");
        btnDelete = new JButton("Delete Donor");
        formPanel.add(btnAdd);
        formPanel.add(btnDelete);

        add(formPanel, BorderLayout.NORTH);

        // Table to display donors
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Address"}, 0);
        donorTable = new JTable(tableModel);
        add(new JScrollPane(donorTable), BorderLayout.CENTER);

        // Refresh button
        btnRefresh = new JButton("Refresh");
        add(btnRefresh, BorderLayout.SOUTH);

        // Load donors initially
        loadDonors();

        // Button actions
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDonor();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteDonor();
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDonors();
            }
        });
    }

    /**
     * Add donor to database
     */
    private void addDonor() {
        try (Connection conn =DB.getConnection()) {
            String sql = "INSERT INTO donor(name,email,phone,address) VALUES(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, txtName.getText());
            stmt.setString(2, txtEmail.getText());
            stmt.setString(3, txtPhone.getText());
            stmt.setString(4, txtAddress.getText());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Donor added successfully!");
            loadDonors(); // refresh
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Delete selected donor
     */
    private void deleteDonor() {
        int row = donorTable.getSelectedRow();
        if (row >= 0) {
            int donorId = (int) tableModel.getValueAt(row, 0);

            try (Connection conn = DB.getConnection()) {
                String sql = "DELETE FROM donor WHERE donor_id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, donorId);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Donor deleted successfully!");
                loadDonors(); // refresh
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a donor to delete!");
        }
    }

   
    private void loadDonors() {
        tableModel.setRowCount(0); // clear table
        try (Connection conn =DB.getConnection()) {
            String sql = "SELECT * FROM donor";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("donor_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
