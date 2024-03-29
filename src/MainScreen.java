
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DoganCan
 */
public class MainScreen extends javax.swing.JFrame {

    /**
     * Creates new form MainScreen
     */
    
     LinkedList<String> user_name;
    
     LinkedList<String> ip_addresses;
     
     //private String clientRecords =  "/Users/DoganCan/Desktop/clientRecords";
    
     private void getUsername() {

    
         try {
             FileReader fileReader;
             fileReader = new FileReader("clientRecords.txt");
             
             BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            // Initialize linked list arrays
            user_name = new LinkedList<String>();
            
            ip_addresses = new LinkedList<String>();
  
            String text_line = "";
            
            while((text_line = bufferedReader.readLine()) != null) {
          
                if(text_line != "\n"){
                
                    System.out.println(text_line);
          
                    String [] split = text_line.split("/");
          
                    user_name.add(split[0]);
                    ip_addresses.add(split[1]);
      }
            }
             
         } catch (FileNotFoundException ex) {
             Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         for (int j = 0; j < user_name.size(); j++) {
            
            usersList.addItem(user_name.get(j));
            
        }
         
         
    }
     
     
    public MainScreen() {
        initComponents();
        
        this.getUsername();
        
    }

   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ipAddress = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        usersList = new javax.swing.JComboBox();
        connect = new javax.swing.JButton();
        userName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ipAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipAddressActionPerformed(evt);
            }
        });

        jLabel1.setText("IP:");

        usersList.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Users" }));
        usersList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersListActionPerformed(evt);
            }
        });

        connect.setText("Connect");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });

        jLabel2.setText("Name");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(30, 30, 30)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(usersList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 211, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ipAddress)
                    .add(connect)
                    .add(userName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(64, 64, 64)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ipAddress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(9, 9, 9)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(userName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .add(37, 37, 37)
                .add(connect)
                .add(18, 18, 18)
                .add(usersList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(255, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ipAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipAddressActionPerformed

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
        try {
            
            if(userName.getText().isEmpty() || ipAddress.getText().isEmpty()){
                System.out.println("Username or ip address is not given");
            
            }    
            else{
                client newClient = new client("DoganCan", ipAddress.getText());

                boolean contains = false;

                for (int i = 0; i < user_name.size(); i++) {

                    if(user_name.get(i).equals(userName.getText())){
                        contains = true;
                        break;
                    }
                }

                if(contains == false){

                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("clientRecords.txt", true)));
                out.println(userName.getText() + "/"+ ipAddress.getText());
                out.close();
                }

            }
            
        } catch (IOException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }//GEN-LAST:event_connectActionPerformed

    private void usersListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersListActionPerformed

        JComboBox cb = (JComboBox)evt.getSource();
        String uname = (String)cb.getSelectedItem();
        
        int index = (int)cb.getSelectedIndex();
        --index;
        userName.setText(user_name.get(index));
        ipAddress.setText(ip_addresses.get(index));


    }//GEN-LAST:event_usersListActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
                                          
                
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connect;
    private javax.swing.JTextField ipAddress;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField userName;
    private javax.swing.JComboBox usersList;
    // End of variables declaration//GEN-END:variables

    
}
