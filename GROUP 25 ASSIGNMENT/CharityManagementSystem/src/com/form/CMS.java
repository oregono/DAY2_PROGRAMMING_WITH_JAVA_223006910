package com.form;

import com.panel.*; // Import your panels (DonorPanel, UserPanel, etc.)
import javax.swing.*;
import java.awt.*;

public class CMS extends JFrame {
    private String userRole;
    private int userId;

    public CMS(String role, int userId) {
        this.userRole = role;
        this.userId = userId;

        // Frame settings
        setTitle("Charity Management System - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Welcome label
        JLabel lbl = new JLabel("Welcome to Charity Management System (" + role + ")", JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));

        // Tabbed Pane
        JTabbedPane tabs = new JTabbedPane();

        // Role-based access control
        if (role.equalsIgnoreCase("Admin")) {
            tabs.add("Users", new UserPanel());
            tabs.add("Donors", new DonorPanel());
            tabs.add("Beneficiaries", new BeneficiaryPanel());
            tabs.add("Volunteers", new VolunteerPanel());
            tabs.add("Events", new EventPanel());
            tabs.add("Donations", new DonationsPanel());
        } else if (role.equalsIgnoreCase("Donor")) {
            tabs.add("Donations", new DonationsPanel());
            tabs.add("Events", new EventPanel());
        } else if (role.equalsIgnoreCase("Volunteer")) {
            tabs.add("Beneficiaries", new BeneficiaryPanel());
            tabs.add("Events", new EventPanel());
        }

        // Layout
        setLayout(new BorderLayout());
        add(lbl, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);

        setVisible(true);
    }
}

