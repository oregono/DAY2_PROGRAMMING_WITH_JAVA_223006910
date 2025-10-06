package com.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.form.DB;

public class EventPanel extends JPanel {

    private JTable eventTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public EventPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"eventid", "name", "Date", "Description","total_collected" };
        tableModel = new DefaultTableModel(columns, 0);
        eventTable = new JTable(tableModel);
        scrollPane = new JScrollPane(eventTable);
        add(scrollPane, BorderLayout.CENTER);

        loadEvents();
    }

    private void loadEvents() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = stmt.executeQuery("SELECT eventid, name,date,description, total_collected FROM event");

            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("eventid"),
                        rs.getString("name"),
                        rs.getDate("date"),
                        rs.getString("description"),
                        rs.getDouble("total_collected")
                };
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}

