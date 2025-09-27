package com.panel;

import javax.swing.*;

import com.form.DB;

import java.awt.*;
import java.sql.*;

public class UserPanel extends JPanel {

    private JTable userTable;
    private JScrollPane scrollPane;

    public UserPanel() {
        setLayout(new BorderLayout());

        // Table columns
        String[] columns = {"UserID", "Username", "Role"};

        // Table model initialized empty
        Object[][] data = {};
        userTable = new JTable(data, columns);
        scrollPane = new JScrollPane(userTable);

        add(scrollPane, BorderLayout.CENTER);

        loadUsers(); // Load data from database
    }

    private void loadUsers() {
        try (Connection con = DB.getConnection()) {
            String query = "SELECT userid, username, role FROM user";
            ResultSet rs = con.createStatement().executeQuery(query);

            // Count rows
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            Object[][] data = new Object[rowCount][3];
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getInt("userid");
                data[i][1] = rs.getString("username");
                data[i][2] = rs.getString("role");
                i++;
            }

            String[] columns = {"UserID", "Username", "Role"};
            userTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}
