
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DoganCan
 */
public class Directory extends javax.swing.JFrame {

    /**
     * Creates new form Directory
     */    
       
    String [] theWholeMessage;
    
    DataOutputStream pw;    

    
    public Directory(String [] dl , DataOutputStream printwriter) {
        initComponents();
        pw = printwriter;
        theWholeMessage = dl;
        
        DefaultListModel  dirList = new DefaultListModel();
        
        directoryList.setModel(dirList);
        
        
        for(String token : dl) {
            
            dirList.addElement(token);
            
        }
        
    }

    private Directory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        directoryList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        directoryList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                directoryListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(directoryList);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void directoryListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_directoryListMouseClicked
        
                if (evt.getClickCount() == 2){ 

                    int index = directoryList.locationToIndex(evt.getPoint());

                    System.out.println(index + "th message is requested");
                    
                    String message = theWholeMessage[index];
                    
                    Query query = new Query();
                    query.newQuery("QUERY", "FileRequest");
                    query.newQuery("MESSAGE", message);
                    String content = query.toString() + "\n\n";
                    String header = "HTTP/1.0 200 OK\n" + "Allow: GET\n" +
                    "MIME-Version: 1.0\n" + "Server: HMJ Basic HTTP Server\n" +
                    "Content-Type: text/plain\n" + "Content-length: " +
                    content.length() + "\n\n";
                    
                    try {
                        pw.writeBytes(header);
                        pw.writeBytes(content);
                        pw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                }
        
        
    }//GEN-LAST:event_directoryListMouseClicked

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
            java.util.logging.Logger.getLogger(Directory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Directory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Directory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Directory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Directory().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList directoryList;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
