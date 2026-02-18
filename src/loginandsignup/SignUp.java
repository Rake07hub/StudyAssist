package loginandsignup;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;



public class SignUp extends javax.swing.JFrame {

 
    public SignUp() {
        initComponents();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        emailAddress = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pass = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        SignUpBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("StudyMate Pro");
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(28, 28, 28));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(45, 45, 45), 1, true));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(28, 28, 28));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/logo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("StudyMate Pro");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Your one stop for organizing tasks and more...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel2)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(64, 64, 64))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 400, 500);

        jPanel3.setBackground(new java.awt.Color(28, 28, 28));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Create Account");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Full name");

        fname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        fname.setForeground(new java.awt.Color(255, 255, 255));
        fname.setBackground(new java.awt.Color(45, 45, 45));
        fname.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new com.formdev.flatlaf.ui.FlatRoundBorder(),
            javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        fname.setCaretColor(new java.awt.Color(255, 255, 255));
        fname.setMargin(new java.awt.Insets(2, 10, 2, 10));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Email address");

        emailAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailAddress.setForeground(new java.awt.Color(255, 255, 255));
        emailAddress.setBackground(new java.awt.Color(45, 45, 45));
        emailAddress.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new com.formdev.flatlaf.ui.FlatRoundBorder(),
            javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        emailAddress.setCaretColor(new java.awt.Color(255, 255, 255));
        emailAddress.setMargin(new java.awt.Insets(2, 10, 2, 10));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Password");

        pass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pass.setForeground(new java.awt.Color(255, 255, 255));
        pass.setBackground(new java.awt.Color(45, 45, 45));
        pass.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new com.formdev.flatlaf.ui.FlatRoundBorder(),
            javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        pass.setCaretColor(new java.awt.Color(255, 255, 255));
        pass.setMargin(new java.awt.Insets(2, 10, 2, 10));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Already have an account?");

        SignUpBtn.setBackground(new java.awt.Color(82, 97, 211));
        SignUpBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SignUpBtn.setForeground(new java.awt.Color(255, 255, 255));
        SignUpBtn.setText("Create account");
        SignUpBtn.setBorder(new com.formdev.flatlaf.ui.FlatRoundBorder());
        SignUpBtn.setFocusPainted(false);
        SignUpBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SignUpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignUpBtnActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(82, 97, 211));
        jButton2.setText("Sign in");
        jButton2.setContentAreaFilled(false);
        jButton2.setBorder(null);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login LoginFrame = new Login();
                LoginFrame.setVisible(true);
                LoginFrame.pack();
                LoginFrame.setLocationRelativeTo(null); 
                dispose();
            }
        });

        // Add close button
        javax.swing.JButton closeButton = new javax.swing.JButton("×");
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
        jPanel1.add(closeButton);
        closeButton.setBounds(760, 10, 30, 30);

        // Update the layout
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(emailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SignUpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(10, 10, 10)
                        .addComponent(jButton2)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel4)
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addGap(8, 8, 8)
                .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(8, 8, 8)
                .addComponent(emailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(8, 8, 8)
                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(SignUpBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jButton2))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(400, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null); 
        this.dispose();
    }

    private void SignUpBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String fullName, email, Password, query;
        String SUrl, SUser, SPass;
        SUrl = "jdbc:MySQL://localhost:3306/java_user_database";
        SUser = "root";
        SPass = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            if("".equals(fname.getText())){
                JOptionPane.showMessageDialog(new JFrame(), "Full Name is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else if("".equals(emailAddress.getText())){
                JOptionPane.showMessageDialog(new JFrame(), "Email Address is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else if("".equals(pass.getText())){
                JOptionPane.showMessageDialog(new JFrame(), "Password is require", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else {
            fullName = fname.getText(); 
            email    = emailAddress.getText();
            Password = pass.getText();
            System.out.println(Password);
            
            query = "INSERT INTO user(full_name, email, password)"+
                    "VALUES('"+fullName+"', '"+email+"' , '"+Password+"')";
            
            st.execute(query);
            fname.setText("");
            emailAddress.setText("");
            pass.setText("");
            showMessageDialog(null, "New account has been created successfully!");
            }
        }catch(Exception e){
           System.out.println("Error!" + e.getMessage()); 
        }
        
    }

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SignUpBtn;
    private javax.swing.JTextField emailAddress;
    private javax.swing.JTextField fname;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField pass;
    // End of variables declaration//GEN-END:variables
}
