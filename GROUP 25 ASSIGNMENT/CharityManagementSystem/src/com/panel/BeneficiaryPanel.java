package com.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.form.DB;

public class BeneficiaryPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton registerBtn, viewEventsBtn;

    public BeneficiaryPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Beneficiary ID", "Category", "Contactinfo","allocated_support"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        registerBtn = new JButton("Register Self");
        viewEventsBtn = new JButton("View Events");
        bottomPanel.add(registerBtn);
        bottomPanel.add(viewEventsBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        loadBeneficiaries();

        registerBtn.addActionListener(e -> registerBeneficiary());
        viewEventsBtn.addActionListener(e -> viewEvents());
    }

    private void loadBeneficiaries() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM beneficiary");
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("benefiaryid"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("contactinfo"),
                        rs.getString("allocated_support")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void registerBeneficiary() {
        String name = JOptionPane.showInputDialog(this, "Enter Name:");
        String category = JOptionPane.showInputDialog(this, "Enter category:");
        String contactinfo = JOptionPane.showInputDialog(this, "Enter contactinfo:");
        String allocated_support = JOptionPane.showInputDialog(this, "Enter allocated_support:");

        try (Connection con = DB.getConnection()) {
            String sql = "INSERT INTO beneficiary (name, category, contactinfo, allocated_support) VALUES (?, ?, ?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setString(3, contactinfo);
            ps.setString(4,allocated_support);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registered successfully!");
            loadBeneficiaries();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void viewEvents() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM event");

            JTable eventTable = new JTable();
            DefaultTableModel eventModel = new DefaultTableModel(new String[]{"Event ID", "name", "Date", "Description","Total_Collected"}, 0);
            eventTable.setModel(eventModel);
            while (rs.next()) {
                eventModel.addRow(new Object[]{
                        rs.getInt("eventid"),
                        rs.getString("name"),
                        rs.getDate("date"),
                        rs.getString("description"),
                        rs.getInt("Total_collected")
                });
            }

            JFrame frame = new JFrame("Events");
            frame.add(new JScrollPane(eventTable));
            frame.setSize(500, 300);
            frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}



