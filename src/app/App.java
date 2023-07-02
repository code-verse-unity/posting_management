/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package app;

import javax.swing.JFrame;

import io.github.cdimascio.dotenv.Dotenv;
import views.EmployeeView;
import views.PlaceView;
import views.PostingView;
import java.sql.Connection;
import utils.Database;
import utils.EmailSender;
import views.PostingView2;

/**
 *
 * @author olivier
 */
public class App extends javax.swing.JFrame {
    Connection connection;
    EmailSender emailSender;
    Dotenv dotenv;

    /**
     * Creates new form NewJFrame
     */
    public App() {
        initComponents();

        dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Database database = new Database(url, user, password);
        connection = database.connect();

        this.emailSender = new EmailSender(
                dotenv.get("MAIL_HOST"),
                Integer.parseInt(dotenv.get("MAIL_PORT")),
                dotenv.get("MAIL_USERNAME"),
                dotenv.get("MAIL_PASSWORD"));

        EmployeeView home = new EmployeeView(connection, this);
        mainContent.add(home);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidebar = new javax.swing.JPanel();
        employeeBtn = new javax.swing.JLabel();
        placeBtn = new javax.swing.JLabel();
        postingBtn1 = new javax.swing.JLabel();
        mainContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestion d'afféctation");
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new java.awt.BorderLayout(8, 0));

        sidebar.setBackground(new java.awt.Color(153, 153, 153));
        sidebar.setPreferredSize(new java.awt.Dimension(150, 639));

        employeeBtn.setText("Employées");
        employeeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeBtnMouseClicked(evt);
            }
        });

        placeBtn.setText("Lieux");
        placeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                placeBtnMouseClicked(evt);
            }
        });

        postingBtn1.setText("Afféctations");
        postingBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                postingBtn1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(employeeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(placeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
            .addComponent(postingBtn1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(employeeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postingBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(266, Short.MAX_VALUE))
        );

        getContentPane().add(sidebar, java.awt.BorderLayout.LINE_START);

        mainContent.setBackground(new java.awt.Color(255, 255, 255));
        mainContent.setLayout(new java.awt.BorderLayout());
        getContentPane().add(mainContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void employeeBtnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_employeeBtnMouseClicked
        // Remove existing component to render a new one
        mainContent.removeAll();

        System.out.println("hey");
        // Add the employeeView panel to the mainContent panel
        EmployeeView employeeView = new EmployeeView(connection, this);
        mainContent.add(employeeView);
        mainContent.updateUI();
    }// GEN-LAST:event_employeeBtnMouseClicked

    private void placeBtnMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_placeBtnMouseClicked
        mainContent.removeAll();

        PlaceView placeView = new PlaceView(connection, this);
        mainContent.add(placeView);
        mainContent.updateUI();
    }// GEN-LAST:event_placeBtnMouseClicked

    private void postingBtn1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_postingBtn1MouseClicked
        mainContent.removeAll();

        PostingView2 postingView = new PostingView2(connection, this.emailSender, this.dotenv, this);
        mainContent.add(postingView);
        mainContent.updateUI();
    }// GEN-LAST:event_postingBtn1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                App app = new App();

                app.setVisible(true);
                app.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel employeeBtn;
    private javax.swing.JPanel mainContent;
    private javax.swing.JLabel placeBtn;
    private javax.swing.JLabel postingBtn1;
    private javax.swing.JPanel sidebar;
    // End of variables declaration//GEN-END:variables
}
