package com.panel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VolunteerPanel extends JFrame {
    private JTextField nameField, emailField, phoneField, taskField;
    private JButton addBtn, viewBtn;
    private JTextArea displayArea;

    public VolunteerPanel() {
        setTitle("Volunteer Management");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Assigned Task:"));
        taskField = new JTextField();
        add(taskField);

        addBtn = new JButton("Add Volunteer");
        add(addBtn);

        viewBtn = new JButton("View Volunteers");
        add(viewBtn);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        addBtn.addActionListener(e -> addVolunteer());
        viewBtn.addActionListener(e -> viewVolunteers());
    }

    private void addVolunteer() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String task = taskField.getText();

        String url = "jdbc:mysql://localhost:3306/donation";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "INSERT INTO volunteers (name, email, phone, assigned_task) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, task);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Volunteer added successfully!");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void viewVolunteers() {
        String url = "jdbc:mysql://localhost:3306/donation";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "SELECT * FROM volunteers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            displayArea.setText("");
            while (rs.next()) {
                displayArea.append(
                        "ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") +
                        ", Phone: " + rs.getString("phone") +
                        ", Task: " + rs.getString("assigned_task") + "\n");
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		return;
	}
}


