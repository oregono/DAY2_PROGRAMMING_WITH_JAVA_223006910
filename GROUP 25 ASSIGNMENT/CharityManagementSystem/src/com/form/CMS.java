package com.form;

import com.panel.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CMS extends JFrame {
    private String userRole;
    private int userId;

    public CMS(String role, int userId) {
        this.userRole = role;
        this.userId = userId;

        setTitle("Charity Management System - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,15, 10));
        JLabel lbl = new JLabel("Welcome to Charity Management System (" + role + ")", JLabel.LEFT);
        lbl.setFont(new Font("Arial", Font.BOLD, 15));

        
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.PLAIN, 13));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(220, 53, 69)); // Soft red
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setContentAreaFilled(false);
        btnLogout.setOpaque(true);
        
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(200, 35, 51)); // Darker red on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(220, 53, 69)); // Original red
            }
        });
        
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    CMS.this,
                    "Are you sure you want to log out?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginForm().setVisible(true);
                }
            }
        });

      
        headerPanel.add(lbl, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();

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

       
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);

        setVisible(true);
    }
}

