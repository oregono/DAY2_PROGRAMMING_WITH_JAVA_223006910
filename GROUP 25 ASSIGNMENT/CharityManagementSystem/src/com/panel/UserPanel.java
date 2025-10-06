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

    private JTextField usernameField, roleField;
    private JPasswordField passwordField;
    private JButton createBtn, updateBtn, deleteBtn;

    public UserPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

     
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleField = new JTextField();

       
        createBtn = new JButton("Create");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");

        
        String[] columns = {"UserID", "Username", "Password", "Role"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setRowHeight(30);
        scrollPane = new JScrollPane(userTable);

        // === Add Components ===
        add(usernameLabel); add(usernameField);
        add(passwordLabel); add(passwordField);
        add(roleLabel); add(roleField);
        add(createBtn); add(updateBtn); add(deleteBtn);
        add(scrollPane);

        int x = 20, y = 20, w = 100, h = 30;
        usernameLabel.setBounds(x, y, w, h);
        usernameField.setBounds(x + 110, y, 150, h);

        passwordLabel.setBounds(x, y + 40, w, h);
        passwordField.setBounds(x + 110, y + 40, 150, h);

        roleLabel.setBounds(x, y +80, w, h);
        roleField.setBounds(x + 110, y + 80, 150, h);

        createBtn.setBounds(x + 540, y, 100, h);
        updateBtn.setBounds(x + 540, y + 50, 100, h);
        deleteBtn.setBounds(x + 540, y + 100, 100, h);

        scrollPane.setBounds(20, 150, 650, 300);

       
        loadUsers();

    
        createBtn.addActionListener(_ -> addUser());
        deleteBtn.addActionListener(_ -> deleteUser());
        updateBtn.addActionListener(_ -> updateUserById());
    }

    private void loadUsers() {
        try (Connection con = DB.getConnection()) {
            String query = "SELECT userid, username, password, role FROM user";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("userid"),
                    rs.getString("username"),
                    rs.getInt("password"),
                    rs.getString("role")
                };
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error Loading Users: " + ex.getMessage());
        }
    }

    private void addUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = roleField.getText();

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO user (username, password, role) VALUES (?, ?, ?)"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "User created successfully!");
            clearFields();
            loadUsers();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error Adding User: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete!");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE userid = ?");
            ps.setInt(1, userId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "User deleted successfully!");
            loadUsers();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error Deleting User: " + ex.getMessage());
        }
    }
    private int getSelectedUserId() throws Exception {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user first!");
            return 0;
        }

        String selectedId = tableModel.getValueAt(selectedRow, 0).toString();
        int userId = Integer.parseInt(selectedId);

        try (Connection con = DB.getConnection()) {
            PreparedStatement check = con.prepareStatement("SELECT * FROM user WHERE userid = ?");
            check.setInt(1, userId);
            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "User does not exist anymore!");
                return 0;
            }
        }
        return userId;
    }


    private void updateUserById() {
        try (Connection con = DB.getConnection()) {
            int userId = getSelectedUserId();
            if (userId == 0) return;

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText().trim();

            PreparedStatement fetchUser = con.prepareStatement("SELECT * FROM user WHERE userid = ?");
            fetchUser.setInt(1, userId);
            ResultSet rs = fetchUser.executeQuery();

            if (rs.next()) {
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE user SET username = ?, password = ?, role = ? WHERE userid = ?"
                );
                ps.setString(1, username.isEmpty() ? rs.getString("username") : username);
                ps.setString(2, password.isEmpty() ? rs.getString("password") : password);
                ps.setString(3, role.isEmpty() ? rs.getString("role") : role);
                ps.setInt(4, userId);

                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "User updated successfully!");
                    loadUsers();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed!");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating user: " + ex.getMessage());
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        roleField.setText("");
    }
}



