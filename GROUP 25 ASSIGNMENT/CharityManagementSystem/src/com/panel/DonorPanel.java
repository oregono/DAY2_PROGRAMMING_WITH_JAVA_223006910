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

    private JTextField donorIdField, nameField, emailField, phoneField, addressField;
    private JButton registerBtn, updateBtn, deleteBtn, refreshBtn, viewEventsBtn, viewDonationsBtn;

    public DonorPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Donor Registration / Self-Management"));

        formPanel.add(new JLabel("Donor ID:"));
        donorIdField = new JTextField();
        formPanel.add(donorIdField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("address:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        add(formPanel, BorderLayout.NORTH);

        String[] columns = {"Donor ID", "Name", "Email", "Phone","address"};
        tableModel = new DefaultTableModel(columns, 0);
        donorTable = new JTable(tableModel);
        scrollPane = new JScrollPane(donorTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        registerBtn = new JButton("Register");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        refreshBtn = new JButton("View Donors");
        viewEventsBtn = new JButton("View Events");
        viewDonationsBtn = new JButton("View Donations");

        btnPanel.add(registerBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(viewEventsBtn);
        btnPanel.add(viewDonationsBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadDonors();

        refreshBtn.addActionListener(e -> loadDonors());

        registerBtn.addActionListener(e -> registerDonor());
        updateBtn.addActionListener(e -> updateDonor());
        deleteBtn.addActionListener(e -> deleteDonor());
        viewEventsBtn.addActionListener(e -> viewEvents());
        viewDonationsBtn.addActionListener(e -> viewDonations());
    }

    private void loadDonors() {
        try (Connection con = DB.getConnection()) {
            String query = "SELECT donorid, name, email, phone, address FROM donor";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("donorid"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("phone"),
                        rs.getString("address")
                };
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading donors: " + ex.getMessage());
        }
    }

    private int getSelectedDonorId() {
        int selectedRow = donorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a donor first!");
            return -1;
        }
        return (int) tableModel.getValueAt(selectedRow, 0); 
    }

    private void registerDonor() {
        try (Connection con = DB.getConnection()) {
            String sql = "INSERT INTO donor (name, email, phone, address) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nameField.getText());
            ps.setString(2, emailField.getText());
            ps.setString(3, phoneField.getText());
            ps.setString(4, addressField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Donor registered successfully!");
            loadDonors();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateDonor() {
        int donorId = getSelectedDonorId();
        if (donorId == -1) return; 

        try (Connection con = DB.getConnection()) {
            String sql = "UPDATE donor SET name=?, email=?, phone=?,address=? WHERE donorid=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nameField.getText());
            ps.setString(2, emailField.getText());
            ps.setString(3, phoneField.getText());
            ps.setString(4, addressField.getText());
            ps.setInt(5, donorId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Donor updated successfully!");
                loadDonors(); 
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
   
    private void deleteDonor() {
        int donorId = getSelectedDonorId();
        if (donorId == -1) return;

        try (Connection con = DB.getConnection()) {
            con.setAutoCommit(false);

            PreparedStatement deleteDonations = con.prepareStatement("DELETE FROM donations WHERE donorid=?");
            deleteDonations.setInt(1, donorId);
            deleteDonations.executeUpdate();

            PreparedStatement deleteDonor = con.prepareStatement("DELETE FROM donor WHERE donorid=?");
            deleteDonor.setInt(1, donorId);
            deleteDonor.executeUpdate();

            con.commit();
            JOptionPane.showMessageDialog(this, "Donor and related donations deleted successfully!");
            loadDonors();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearFields() {
		// TODO Auto-generated method stub
		
	}

    private void viewEvents() {
        try (Connection con = DB.getConnection()) {
            String sql = "SELECT * FROM event";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            JTable table = new JTable();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Event ID", "name", "Date", "description"}, 0);
            table.setModel(model);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("eventid"),
                        rs.getString("name"),
                        rs.getDate("date"),
                        rs.getString("description")
                });
            }

            JFrame frame = new JFrame("Events");
            frame.add(new JScrollPane(table));
            frame.setSize(500, 300);
            frame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void viewDonations() {
        try (Connection con = DB.getConnection()) {
            String sql = "SELECT * FROM donations";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            JTable table = new JTable();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Donation ID", "Donor ID","types", "Amount", "createdAt"}, 0);
            table.setModel(model);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("donationid"),
                        rs.getInt("donorid"),
                        rs.getString("types"),
                        rs.getDouble("amount"),
                        rs.getDate("createdAt")
                });
            }

            JFrame frame = new JFrame("Donations");
            frame.add(new JScrollPane(table));
            frame.setSize(500, 300);
            frame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}




