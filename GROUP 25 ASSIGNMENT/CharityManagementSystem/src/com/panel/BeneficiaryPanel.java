package com.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import com.form.DB;

public class BeneficiaryPanel extends JPanel {

    private JTable beneficiaryTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public BeneficiaryPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"BeneficiaryID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        beneficiaryTable = new JTable(tableModel);
        scrollPane = new JScrollPane(beneficiaryTable);
        add(scrollPane, BorderLayout.CENTER);

        loadBeneficiaries();
    }

    private void loadBeneficiaries() {
        try (Connection con = DB.getConnection()) {
            Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet rs = stmt.executeQuery("SELECT benefiaryid, name, category, contactinfo, allocated_support FROM beneficiary");

            tableModel.setRowCount(0);
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("benefiaryid"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("contactinfo"),
                        rs.getString("allocated_support"),
                };
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}

