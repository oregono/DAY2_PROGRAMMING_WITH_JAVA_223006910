package com.form;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import com.panel.UserPanel; // for later use or dashboard

public class LoginForm extends JFrame implements ActionListener {

    private JTextField userTxt;
    private JPasswordField passTxt;
    private JButton loginbtn, cancelbtn;

    // constructor
    public LoginForm() {
        setTitle("Charity Management System - Login");
        setBounds(100, 100, 400, 250);
        setLayout(null);

        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");

        lblUser.setBounds(50, 30, 100, 25);
        userTxt = new JTextField();
        userTxt.setBounds(150, 30, 180, 25);

        lblPass.setBounds(50, 70, 100, 25);
        passTxt = new JPasswordField();
        passTxt.setBounds(150, 70, 180, 25);

        loginbtn = new JButton("Login");
        loginbtn.setBounds(70, 130, 100, 30);
        cancelbtn = new JButton("Cancel");
        cancelbtn.setBounds(200, 130, 100, 30);

        add(lblUser);
        add(userTxt);
        add(lblPass);
        add(passTxt);
        add(loginbtn);
        add(cancelbtn);

        loginbtn.addActionListener(this);
        cancelbtn.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginbtn) {
            String username = userTxt.getText();
            String password = new String(passTxt.getPassword());

            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            try (Connection con = DB.getConnection()) {
                String sql = "SELECT * FROM user WHERE username=? AND password=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String role = rs.getString("role");
                    int userId = rs.getInt("userid");

                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    dispose();
                    // Open the dashboard or CMS
                    new CMS(role); 
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database connection error: " + ex.getMessage());
            }

        } else if (e.getSource() == cancelbtn) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
