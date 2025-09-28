package com.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.form.DB;

public class UserPanel extends JPanel {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public UserPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"UserID", "Username", "Role"};
        tableModel = new DefaultTableModel(columns, 0); 
        userTable = new JTable(tableModel);
        scrollPane = new JScrollPane(userTable);

        add(scrollPane, BorderLayout.CENTER);

        loadUsers(); 
    }

    private void loadUsers() {
        try (Connection con = DB.getConnection()) {
            String query = "SELECT userid, username, role FROM user";
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = stmt.executeQuery(query);

            tableModel.setRowCount(0); 

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("userid"),
                        rs.getString("username"),
                        rs.getString("role")
                };
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}

