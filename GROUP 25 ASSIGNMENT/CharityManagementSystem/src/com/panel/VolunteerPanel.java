package com.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.form.DB;

public class VolunteerPanel extends JPanel {

    private JTable volunteerTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public VolunteerPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Email", "Phone", "Task"};
        tableModel = new DefaultTableModel(columns, 0);
        volunteerTable = new JTable(tableModel);
        scrollPane = new JScrollPane(volunteerTable);
        add(scrollPane, BorderLayout.CENTER);

        loadVolunteers();
    }

    private void loadVolunteers() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = stmt.executeQuery("SELECT volunteerid, name, email, phone, availability FROM volunteers");

            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("volunteerid"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("availability")
                };
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}


