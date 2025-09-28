package com.form;
import javax.swing.*;

public class Dashboard extends JFrame {
    public Dashboard(String role, int userId) {
        setTitle("Charity Management System - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lbl = new JLabel("Welcome, UserID: " + userId + " | Role: " + role);
        add(lbl);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

