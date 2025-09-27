package com.form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.panel.UserPanel;
import com.panel.DonorPanel;      // We'll create this similar to UserPanel
import com.panel.BeneficiaryPanel; 
import com.panel.VolunteerPanel; 
import com.panel.DonationsPanel;


public class CMS extends JFrame {

    private String userRole;
    private JPanel contentPanel;

    public CMS(String role) {
        this.userRole = role;

        // Frame settings
        setTitle("Charity Management System - Dashboard");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Content panel to swap views
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // Menus
        JMenu donorMenu = new JMenu("Donors");
        JMenuItem manageDonors = new JMenuItem("Manage Donors");
        donorMenu.add(manageDonors);

        JMenu donationMenu = new JMenu("Donations");
        JMenuItem manageDonations = new JMenuItem("Manage Donations");
        donationMenu.add(manageDonations);

        JMenu beneficiaryMenu = new JMenu("Beneficiaries");
        JMenuItem manageBeneficiaries = new JMenuItem("Manage Beneficiaries");
        beneficiaryMenu.add(manageBeneficiaries);

        JMenu volunteerMenu = new JMenu("Volunteers");
        JMenuItem manageVolunteers = new JMenuItem("Manage Volunteers");
        volunteerMenu.add(manageVolunteers);

        JMenu eventMenu = new JMenu("Events");
        JMenuItem manageEvents = new JMenuItem("Manage Events");
        eventMenu.add(manageEvents);

        // Role-based menu display
        if (role.equalsIgnoreCase("Admin")) {
            menuBar.add(donorMenu);
            menuBar.add(donationMenu);
            menuBar.add(beneficiaryMenu);
            menuBar.add(volunteerMenu);
            menuBar.add(eventMenu);
        } else if (role.equalsIgnoreCase("Donor")) {
            menuBar.add(donationMenu);
            menuBar.add(eventMenu);
        } else if (role.equalsIgnoreCase("Volunteer")) {
            menuBar.add(eventMenu);
            menuBar.add(beneficiaryMenu);
        }

        setJMenuBar(menuBar);

        // Welcome label
        JLabel lbl = new JLabel("Welcome to Charity Management System (" + role + ")", JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(lbl, BorderLayout.CENTER);

        // Menu actions
        manageDonors.addActionListener(e -> switchPanel(new DonorPanel()));
       // manageDonations.addActionListener(e -> switchPanel(new DonationsPanel()));
       // manageBeneficiaries.addActionListener(e -> switchPanel(new BeneficiaryPanel()));
       // manageVolunteers.addActionListener(e -> switchPanel(new VolunteerPanel()));

        setVisible(true);
    }

    private void switchPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        new CMS("Admin");
    }
}
