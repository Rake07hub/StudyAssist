package loginandsignup;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

public class Login extends javax.swing.JFrame {

  
    public Login() {
        initComponents();
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Right = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        LoginBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("StudyMate Pro");
        setUndecorated(true); // Remove window decorations for modern look

        jPanel1.setBackground(new java.awt.Color(28, 28, 28));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(45, 45, 45), 1, true));
        jPanel1.setLayout(null);

        Right.setBackground(new java.awt.Color(28, 28, 28));
        Right.setPreferredSize(new java.awt.Dimension(400, 500));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/logo.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("StudyMate Pro");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Your one stop for organizing tasks and more...");

        javax.swing.GroupLayout RightLayout = new javax.swing.GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGroup(RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel5))
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightLayout.createSequentialGroup()
                .addGap(0, 74, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(60, 60, 60))
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel5)
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(82, 82, 82))
        );

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);

        Left.setBackground(new java.awt.Color(28, 28, 28));
        Left.setMinimumSize(new java.awt.Dimension(400, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Welcome Back");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Email address");

        email.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        email.setForeground(new java.awt.Color(255, 255, 255));
        email.setBackground(new java.awt.Color(45, 45, 45));
        email.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new com.formdev.flatlaf.ui.FlatRoundBorder(),
            javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        email.setCaretColor(new java.awt.Color(255, 255, 255));
        email.setMargin(new java.awt.Insets(2, 10, 2, 10));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Password");

        password.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        password.setForeground(new java.awt.Color(255, 255, 255));
        password.setBackground(new java.awt.Color(45, 45, 45));
        password.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new com.formdev.flatlaf.ui.FlatRoundBorder(),
            javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        password.setCaretColor(new java.awt.Color(255, 255, 255));
        password.setMargin(new java.awt.Insets(2, 10, 2, 10));

        LoginBtn.setBackground(new java.awt.Color(82, 97, 211));
        LoginBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LoginBtn.setForeground(new java.awt.Color(255, 255, 255));
        LoginBtn.setText("Sign in");
        LoginBtn.setBorder(new com.formdev.flatlaf.ui.FlatRoundBorder());
        LoginBtn.setFocusPainted(false);
        LoginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LoginBtn.setPreferredSize(new java.awt.Dimension(100, 40));
        LoginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Don't have an account?");

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(82, 97, 211));
        jButton2.setText("Create account");
        jButton2.setContentAreaFilled(false);
        jButton2.setBorder(null);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignUp SignUpFrame = new SignUp();
                SignUpFrame.setVisible(true);
                SignUpFrame.pack();
                SignUpFrame.setLocationRelativeTo(null); 
                dispose();
            }
        });

        // Add a close button
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
        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LoginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(10, 10, 10)
                        .addComponent(jButton2)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(LoginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton2))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jPanel1.add(Left);
        Left.setBounds(400, 0, 400, 500);

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        SignUp SignUpFrame = new SignUp();
        SignUpFrame.setVisible(true);
        SignUpFrame.pack();
        SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void LoginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String Email, Password, query, fname = null, passDb = null;
        String SUrl, SUser, SPass;
        SUrl = "jdbc:MySQL://localhost:3306/java_user_database";
        SUser = "root";
        SPass = "";
        int notFound = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            if("".equals(email.getText())){
                JOptionPane.showMessageDialog(new JFrame(), "Email Address is required", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else if("".equals(password.getText())){
                JOptionPane.showMessageDialog(new JFrame(), "Password is required", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }else {
                Email = email.getText();
                Password = password.getText();
                
                query = "SELECT * FROM user WHERE email= '"+Email+"'";
           
                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                    passDb = rs.getString("password");
                    fname = rs.getString("full_name");
                    notFound = 1;
                }
                if(notFound == 1 && Password.equals(passDb)){
                    Home HomeFrame = new Home();
                    HomeFrame.setUser(fname);
                    HomeFrame.setVisible(true);
                    HomeFrame.pack();
                    HomeFrame.setLocationRelativeTo(null); 
                    this.dispose();
                }else{
                   JOptionPane.showMessageDialog(new JFrame(), "Incorrect email or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                password.setText("");
            }
        }catch(Exception e){
           System.out.println("Error!" + e.getMessage()); 
        }
    }

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Left;
    private javax.swing.JButton LoginBtn;
    private javax.swing.JPanel Right;
    private javax.swing.JTextField email;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField password;
    // End of variables declaration//GEN-END:variables
}
