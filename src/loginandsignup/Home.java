package loginandsignup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends javax.swing.JFrame {
    
    public Home() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        user = new javax.swing.JLabel();
        LogoutBtn = new javax.swing.JButton();
        NextBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("StudyMate Pro");
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 500));

        mainPanel.setBackground(new java.awt.Color(28, 28, 28));
        mainPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(45, 45, 45), 1, true));
        mainPanel.setLayout(null);

        // Welcome text
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); 
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Welcome Back");
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(jLabel1);
        jLabel1.setBounds(200, 100, 400, 60);

        // Username
        user.setFont(new java.awt.Font("Segoe UI Light", 0, 36));
        user.setForeground(new java.awt.Color(82, 97, 211));
        user.setText("John");
        user.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(user);
        user.setBounds(200, 170, 400, 60);

        // Logout button
        LogoutBtn.setBackground(new java.awt.Color(82, 97, 211));
        LogoutBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        LogoutBtn.setForeground(new java.awt.Color(255, 255, 255));
        LogoutBtn.setText("Sign out");
        LogoutBtn.setBorder(new com.formdev.flatlaf.ui.FlatRoundBorder());
        LogoutBtn.setFocusPainted(false);
        LogoutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogoutBtn.setPreferredSize(new java.awt.Dimension(200, 45));
        LogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBtnActionPerformed(evt);
            }
        });
        mainPanel.add(LogoutBtn);
        LogoutBtn.setBounds(300, 280, 200, 45);

        // Next button
        NextBtn.setBackground(new java.awt.Color(45, 45, 45));
        NextBtn.setFont(new java.awt.Font("Segoe UI", 1, 14));
        NextBtn.setForeground(new java.awt.Color(255, 255, 255));
        NextBtn.setText("Press This Shit!");
        NextBtn.setBorder(new com.formdev.flatlaf.ui.FlatRoundBorder());
        NextBtn.setFocusPainted(false);
        NextBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NextBtn.setPreferredSize(new java.awt.Dimension(200, 45));
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });
        mainPanel.add(NextBtn);
        NextBtn.setBounds(300, 340, 200, 45);

        // Close button
        JButton closeButton = new JButton("×");
        closeButton.setFont(new java.awt.Font("Segoe UI", 1, 18));
        closeButton.setForeground(new java.awt.Color(204, 204, 204));
        closeButton.setBorder(null);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });
        mainPanel.add(closeButton);
        closeButton.setBounds(760, 10, 30, 30);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
        dispose();
    }
    
    private void NextBtnActionPerformed(java.awt.event.ActionEvent evt) {
        AdvancedStudyAssistant assistant = new AdvancedStudyAssistant();
        assistant.setVisible(true);
        assistant.setLocationRelativeTo(null);
        dispose();
    }

    public void setUser(String name) {
        user.setText(name);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    private javax.swing.JButton LogoutBtn;
    private javax.swing.JButton NextBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel user;
    private javax.swing.JPanel mainPanel;
}
