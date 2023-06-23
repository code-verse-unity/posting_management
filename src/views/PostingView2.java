/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Employee;
import model.Place;
import model.Posting;
import utils.DateFormatter;

/**
 *
 * @author olivier
 */
public class PostingView2 extends javax.swing.JPanel {

    private Connection connection;
    private JFrame parent;
    private ArrayList<Employee> employees;
    private ArrayList<Place> places;
    private ArrayList<Posting> postings;

    /**
     * Creates new form PostingView2
     */
    public PostingView2(Connection connection, JFrame parent) {
        initComponents();
        this.connection = connection;
        this.parent = parent;

        this.employees = Employee.getAll(connection);
        this.places = Place.getAll(connection);

        this.refillPostingListJTable();
        this.refillPostingEmployeeJComboBox();
        this.refillAddPostingForm();
    }

    public void reloadPostings() {
        this.postings = Posting.getAll(connection);
    }

    public void refillPostingListJTable() {
        reloadPostings();

        DefaultTableModel postingModel = (DefaultTableModel) postingListTable.getModel();

        postingModel.setRowCount(0);

        for (Posting posting : this.postings) {
            Object[] row = new Object[6];
            row[0] = posting.getId();
            row[1] = posting.getEmployee().getCivility() + " " + posting.getEmployee().getFullName();
            row[2] = posting.getOldPlace().getName() + ", " + posting.getOldPlace().getProvince();
            row[3] = posting.getPlace().getName() + ", " + posting.getPlace().getProvince();
            row[4] = DateFormatter.format(posting.getPostingDate());
            row[5] = DateFormatter.format(posting.getServiceDate());

            postingModel.addRow(row);
        }
    }

    private Integer postingEmployeeIdSelected = 0;

    public void setPostingEmployeeIdSelected(Integer postingEmployeeIdSelected) {
        this.postingEmployeeIdSelected = postingEmployeeIdSelected;
    }

    public Integer getPostingEmployeeIdSelected() {
        return this.postingEmployeeIdSelected;
    }

    public void refillPostingEmployeeJComboBox() {
//        DefaultComboBoxModel<Employee> comboBoxModel = (DefaultComboBoxModel<Employee>) postingEmployeeJComboBox.getModel();
//
//        for (Employee employee : this.employees) {
//            comboBoxModel.addElement(employee);
//        }
    }

    private Posting postingSelected = null;

    public void setPostingSelected(Posting postingSelected) {
        this.postingSelected = postingSelected;
    }

    public Posting getPostingSelected() {
        return this.postingSelected;
    }

    public void refillPostingEmployeeListJTable() {
//        DefaultTableModel postingEmployeeModel = (DefaultTableModel) postingListJTable.getModel();
//
//        postingEmployeeModel.setRowCount(0);
//
//        ArrayList<Posting> postings = Posting.getByEmployeeId(this.connection, this.getPostingEmployeeIdSelected());
//
//        for (Posting posting : postings) {
//            Object[] row = new Object[6];
//            row[0] = posting.getId();
//            row[1] = posting.getEmployee().getCivility() + " " + posting.getEmployee().getLastName() + " "
//                    + posting.getEmployee().getFirstName();
//            row[2] = posting.getOldPlace().getName() + ", " + posting.getOldPlace().getProvince();
//            row[3] = posting.getPlace().getName() + ", " + posting.getPlace().getProvince();
//            row[4] = DateFormatter.format(posting.getPostingDate());
//            row[5] = DateFormatter.format(posting.getServiceDate());
//
//            postingEmployeeModel.addRow(row);
//        }
    }

    public void refillEditPostingEmployeeJComboBox() {
//        DefaultComboBoxModel<Employee> comboBoxModel = (DefaultComboBoxModel<Employee>) postingEmployeeJComboBox.getModel();
//
//        comboBoxModel.removeAllElements();
//
//        for (Employee employee : this.employees) {
//            comboBoxModel.addElement(employee);
//
//            if (employee.getId() == this.getPostingSelected().getEmployee().getId()) {
//                comboBoxModel.setSelectedItem(employee);
//            }
//        }
    }

    public void refillEditPostingPlaceJComboBox() {
//        DefaultComboBoxModel<Place> comboBoxModel = (DefaultComboBoxModel<Place>) editPostingPlaceJComboBox.getModel();
//
//        comboBoxModel.removeAllElements();
//
//        for (Place place : this.places) {
//            comboBoxModel.addElement(place);
//
//            if (place.getId() == this.getPostingSelected().getPlace().getId()) {
//                comboBoxModel.setSelectedItem(place);
//            }
//        }
    }

    public void refillEditPostingForm() {
        this.refillEditPostingEmployeeJComboBox();
        this.refillEditPostingPlaceJComboBox();
    }

    public void refillAddPostingEmployeeJComboBox() {
        DefaultComboBoxModel<Employee> comboBoxModel = (DefaultComboBoxModel<Employee>) employeeComboBox.getModel();

        comboBoxModel.removeAllElements();

        for (Employee employee : this.employees) {
            comboBoxModel.addElement(employee);
        }
    }

    public void refillAddPostingPlaceJComboBox() {
        DefaultComboBoxModel<Place> comboBoxModel = (DefaultComboBoxModel<Place>) placeComboBox.getModel();

        comboBoxModel.removeAllElements();

        for (Place place : this.places) {
            comboBoxModel.addElement(place);
        }
    }

    public void refillAddPostingForm() {
        this.refillAddPostingEmployeeJComboBox();
        this.refillAddPostingPlaceJComboBox();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        postingTab = new javax.swing.JPanel();
        searchContainer = new javax.swing.JPanel();
        searchPostingJLabel = new javax.swing.JLabel();
        startDateJLabel = new javax.swing.JLabel();
        startDateJDateChooser = new com.toedter.calendar.JDateChooser();
        endDateJLabel = new javax.swing.JLabel();
        endDateJDateChooser = new com.toedter.calendar.JDateChooser();
        searchPostingJButton = new javax.swing.JButton();
        clearSeachPostingJButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        addPostingFormJLabel1 = new javax.swing.JLabel();
        addPostingEmployeeJLabel1 = new javax.swing.JLabel();
        employeeComboBox = new javax.swing.JComboBox<>();
        addPostingPlaceJLabel1 = new javax.swing.JLabel();
        placeComboBox = new javax.swing.JComboBox<>();
        addPostingStartDateJLabel1 = new javax.swing.JLabel();
        addPostingStartDateJDateChooser = new com.toedter.calendar.JDateChooser();
        addPostingBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        deletePostingJLabel = new javax.swing.JLabel();
        deletePostingBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        generatePDFJLabel = new javax.swing.JLabel();
        generatePDFJButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        postingListTable = new javax.swing.JTable();
        historicTab = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(1, 0));

        postingTab.setLayout(new java.awt.BorderLayout());

        searchContainer.setPreferredSize(new java.awt.Dimension(1586, 120));

        searchPostingJLabel.setText("Options de recheche :");

        startDateJLabel.setText("Date de début :");

        endDateJLabel.setText("Date de fin :");

        searchPostingJButton.setText("Rechercher");
        searchPostingJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPostingJButtonActionPerformed(evt);
            }
        });

        clearSeachPostingJButton.setText("Effacer");

        javax.swing.GroupLayout searchContainerLayout = new javax.swing.GroupLayout(searchContainer);
        searchContainer.setLayout(searchContainerLayout);
        searchContainerLayout.setHorizontalGroup(
            searchContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchContainerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(searchContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchContainerLayout.createSequentialGroup()
                        .addGroup(searchContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDateJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(searchContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endDateJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(searchContainerLayout.createSequentialGroup()
                                .addComponent(endDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchPostingJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearSeachPostingJButton))))
                    .addComponent(searchPostingJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        searchContainerLayout.setVerticalGroup(
            searchContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchContainerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(searchPostingJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(searchContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchContainerLayout.createSequentialGroup()
                        .addComponent(startDateJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(searchContainerLayout.createSequentialGroup()
                        .addComponent(endDateJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(searchContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(searchContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(searchPostingJButton)
                                .addComponent(clearSeachPostingJButton)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        postingTab.add(searchContainer, java.awt.BorderLayout.PAGE_START);

        jPanel2.setPreferredSize(new java.awt.Dimension(400, 623));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        addPostingFormJLabel1.setText("Veuiller remplir le formulaire :");

        addPostingEmployeeJLabel1.setText("Employé :");

        employeeComboBox.setModel(new DefaultComboBoxModel<Employee>());
        employeeComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Employee) {
                    Employee employee = (Employee) value;
                    setText(employee.getId() + ". " + employee.getFullName());
                }
                return this;
            }
        });

        addPostingPlaceJLabel1.setText("Lieu d'afféctation :");

        placeComboBox.setModel(new DefaultComboBoxModel<Place>());
        placeComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Place) {
                    Place place = (Place) value;
                    setText(place.getFullName());
                }
                return this;
            }
        });

        addPostingStartDateJLabel1.setText("Date de prise de service :");

        addPostingBtn.setText("Ajouter");
        addPostingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPostingBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(addPostingFormJLabel1)
                        .addGap(0, 206, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addPostingEmployeeJLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(employeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addPostingPlaceJLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(placeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addPostingStartDateJLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(addPostingBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addPostingStartDateJDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPostingFormJLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addPostingEmployeeJLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addPostingPlaceJLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(addPostingStartDateJLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addPostingStartDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addPostingBtn)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Ajouter une afféctation", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Mettre à jour l'affectation", jPanel7);

        jPanel2.add(jTabbedPane3);

        postingTab.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel1.setPreferredSize(new java.awt.Dimension(1586, 70));

        deletePostingJLabel.setText("Supprimer l'affectation seletionnée :");

        deletePostingBtn.setText("Supprimer");
        deletePostingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePostingBtnActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        generatePDFJLabel.setText("Génerer l'arrêté d'affectation :");

        generatePDFJButton.setText("Génerer en PDF");
        generatePDFJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePDFJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(deletePostingJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deletePostingBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generatePDFJLabel)
                    .addComponent(generatePDFJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(482, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(deletePostingJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deletePostingBtn))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSeparator1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(generatePDFJLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(generatePDFJButton)
                            .addGap(12, 12, 12))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        postingTab.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        postingListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Employée", "Lieu actuel", "Lieu d'affectation", "Date d'affection", "Date de prise de service"
            }
        ));
        postingListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                postingListTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(postingListTable);

        jPanel5.add(jScrollPane1);

        postingTab.add(jPanel5, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Afféctations", postingTab);

        javax.swing.GroupLayout historicTabLayout = new javax.swing.GroupLayout(historicTab);
        historicTab.setLayout(historicTabLayout);
        historicTabLayout.setHorizontalGroup(
            historicTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1014, Short.MAX_VALUE)
        );
        historicTabLayout.setVerticalGroup(
            historicTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Historique d'un employé", historicTab);

        add(jTabbedPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void addPostingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPostingBtnActionPerformed
        Employee employee = (Employee) employeeComboBox.getSelectedItem();
        Integer employeeId = employee.getId();
        Integer oldPlaceId = employee.getPlace().getId();
        Integer newPlaceId = ((Place) placeComboBox.getSelectedItem()).getId();
        Date startDate = addPostingStartDateJDateChooser.getDate();

        Posting.create(connection, employeeId, oldPlaceId, newPlaceId, startDate);
        refillPostingListJTable();
    }//GEN-LAST:event_addPostingBtnActionPerformed

    private void searchPostingJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPostingJButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchPostingJButtonActionPerformed

    private void generatePDFJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatePDFJButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_generatePDFJButtonActionPerformed

    private void deletePostingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePostingBtnActionPerformed
        if (this.postingSelected != null) {
            Posting.destroy(connection, this.postingSelected.getId());
            refillPostingListJTable();
        }
       
    }//GEN-LAST:event_deletePostingBtnActionPerformed

    private void postingListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_postingListTableMouseClicked
        if (postingListTable.getSelectedRows().length == 1) {
            int row = postingListTable.getSelectedRow();
            TableModel jtable = postingListTable.getModel();
            Integer id = Integer.parseInt(jtable.getValueAt(row, 0).toString());
            this.postingSelected = Posting.getById(connection, id);
        }
    }//GEN-LAST:event_postingListTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPostingBtn;
    private javax.swing.JLabel addPostingEmployeeJLabel1;
    private javax.swing.JLabel addPostingFormJLabel1;
    private javax.swing.JLabel addPostingPlaceJLabel1;
    private com.toedter.calendar.JDateChooser addPostingStartDateJDateChooser;
    private javax.swing.JLabel addPostingStartDateJLabel1;
    private javax.swing.JButton clearSeachPostingJButton;
    private javax.swing.JButton deletePostingBtn;
    private javax.swing.JLabel deletePostingJLabel;
    private javax.swing.JComboBox<Employee> employeeComboBox;
    private com.toedter.calendar.JDateChooser endDateJDateChooser;
    private javax.swing.JLabel endDateJLabel;
    private javax.swing.JButton generatePDFJButton;
    private javax.swing.JLabel generatePDFJLabel;
    private javax.swing.JPanel historicTab;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JComboBox<Place> placeComboBox;
    private javax.swing.JTable postingListTable;
    private javax.swing.JPanel postingTab;
    private javax.swing.JPanel searchContainer;
    private javax.swing.JButton searchPostingJButton;
    private javax.swing.JLabel searchPostingJLabel;
    private com.toedter.calendar.JDateChooser startDateJDateChooser;
    private javax.swing.JLabel startDateJLabel;
    // End of variables declaration//GEN-END:variables
}
