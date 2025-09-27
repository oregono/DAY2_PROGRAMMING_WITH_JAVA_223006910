package com.panel;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EventPanel extends JFrame {
    private JTextField nameField, dateField, locationField;
    private JButton addBtn, viewBtn;
    private JTextArea displayArea;

    public EventPanel() {
        setTitle("Event Management");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Event Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Event Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Location:"));
        locationField = new JTextField();
        add(locationField);

        addBtn = new JButton("Add Event");
        add(addBtn);

        viewBtn = new JButton("View Events");
        add(viewBtn);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        addBtn.addActionListener(e -> addEvent());
        viewBtn.addActionListener(e -> viewEvents());
    }

    private void addEvent() {
        String name = nameField.getText();
        String date = dateField.getText();
        String location = locationField.getText();

        String url = "jdbc:mysql://localhost:3306/cms_db";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "INSERT INTO events (event_name, event_date, location) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setDate(2, java.sql.Date.valueOf(date));
            pst.setString(3, location);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event added successfully!");
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private void viewEvents() {
        String url = "jdbc:mysql://localhost:3306/cms_db";
        String dbUser = "root";
        String dbPass = "";

        try {
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
            String query = "SELECT * FROM events";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            displayArea.setText("");
            while (rs.next()) {
                displayArea.append(
                        "ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("event_name") +
                        ", Date: " + rs.getDate("event_date") +
                        ", Location: " + rs.getString("location") + "\n");
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}
