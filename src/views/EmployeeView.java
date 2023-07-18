/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import app.App;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.event.*;
import model.*;
import utils.*;
import com.alexandriasoftware.swing.*;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author olivier
 */
public class EmployeeView extends javax.swing.JPanel {

    /**
     * We need this parent to show dialog box
     */
    JFrame parent;
    Connection connection;
    ArrayList<Employee> employees;

    boolean reseted = false, updateReseted = false;

    /**
     * Creates new form EmployeeView
     */
    public EmployeeView(Connection connection, JFrame parent) {
        this.connection = connection;
        this.parent = parent;
        initComponents();
        loadEmployee();
    }

    private void loadEmployee() {
        if (this.employees != null) {
            this.employees.clear();
        }

        this.employees = Employee.getAll(this.connection);

        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();

        // Reset the row table
        model.setRowCount(0);

        // Add the new rows
        for (Employee employee : employees) {

            Object[] row = new Object[7];
            row[0] = employee.getId();
            row[1] = employee.getLastName();
            row[2] = employee.getFirstName();
            row[3] = employee.getEmail();
            row[4] = employee.getCivility();
            row[5] = employee.getJob();
            row[6] = employee.getPlace().getFullName();

            model.addRow(row);
        }
    }

    private void addInitialValuesToUpdateForm() {
        if (employeeTable.getSelectedRows().length == 1) {
            int row = employeeTable.getSelectedRow();

            TableModel jtable = employeeTable.getModel();

            Integer id = Integer.parseInt(jtable.getValueAt(row, 0).toString());
            Employee employeeSelected = Employee.getOneById(connection, id);

            employeeLastNameTextFieldToUpdate.setText(employeeSelected.getLastName());
            employeeFirstNameTextFieldToUpdate.setText(employeeSelected.getFirstName());
            employeeEmailTextFieldToUpdate.setText(employeeSelected.getEmail());
            employeeCivilityComboBox1.setSelectedItem(employeeSelected.getCivility());
            employeeJobTextFieldToUpdate.setText(employeeSelected.getJob());
            employeePlaceComboBoxToUpdate.setSelectedItem(new ComboItem(
                    employeeSelected.getPlace().getFullName(),
                    employeeSelected.getPlace().getId().toString()));

        }
    }

    private void resetAddForm() {
        employeeLastNameTextField.setText("");
        employeeFirstNameTextField.setText("");
        employeeEmailTextField.setText("");
        employeeJobTextField.setText("");

        reseted = true;
    }

    private void resetUpdateForm() {
        employeeLastNameTextFieldToUpdate.setText("");
        employeeFirstNameTextFieldToUpdate.setText("");
        employeeEmailTextFieldToUpdate.setText("");
        employeeJobTextFieldToUpdate.setText("");

        updateReseted = true;
    }

    private void enableSubmit() {
        boolean isLastnameValid = employeeLastNameTextField.getText() != "" && employeeLastNameTextField.getText().length() > 2;
        boolean isFirstnameValid = employeeFirstNameTextField.getText() != "" && employeeFirstNameTextField.getText().length() > 2;
        boolean isPostValid = employeeJobTextField.getText() != "" && employeeJobTextField.getText().length() > 2;

        if (isLastnameValid && isFirstnameValid && isPostValid && isEmailValid(employeeEmailTextField)) {
            addEmployeeBtn.setEnabled(true);
        } else {
            addEmployeeBtn.setEnabled(false);
        }
    }

    private void enableUpdateSubmit() {
        boolean isLastnameValid = employeeLastNameTextFieldToUpdate.getText() != "" && employeeLastNameTextFieldToUpdate.getText().length() > 2;
        boolean isFirstnameValid = employeeFirstNameTextFieldToUpdate.getText() != "" && employeeFirstNameTextFieldToUpdate.getText().length() > 2;
        boolean isPostValid = employeeJobTextFieldToUpdate.getText() != "" && employeeJobTextFieldToUpdate.getText().length() > 2;

        if (isLastnameValid && isFirstnameValid && isPostValid && isEmailValid(employeeEmailTextFieldToUpdate)) {
            updateEmployeeBtn.setEnabled(true);
        } else {
            updateEmployeeBtn.setEnabled(false);
        }
    }

    boolean isEmailValid(JTextField emailInput) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailInput.getText());

        return matcher.matches();
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

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        searchBtn1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        noPostedEmployeeCheckBox = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        employeeFormContainer = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        addEmployeeTab = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        employeeLastNameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        employeeFirstNameTextField = new javax.swing.JTextField();
        employeeEmailTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        employeeCivilityComboBox = new javax.swing.JComboBox<>();
        addEmployeeBtn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        employeeJobTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        employeePlaceComboBox = new javax.swing.JComboBox<>();
        updateEmployeeTab = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        employeeCivilityComboBox1 = new javax.swing.JComboBox<>();
        employeeEmailTextFieldToUpdate = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        employeeFirstNameTextFieldToUpdate = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        employeeLastNameTextFieldToUpdate = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        updateEmployeeBtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        employeeJobTextFieldToUpdate = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        employeePlaceComboBoxToUpdate = new javax.swing.JComboBox<>();
        deleteEmployeeTab = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        deleteEmployeeBtn = new javax.swing.JButton();
        JScrollPane = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(240, 240, 240));
        setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(1288, 140));
        jPanel3.setBackground(new java.awt.Color(240, 240, 240));

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel1.setText("Liste des employés");

        searchTextField.setPreferredSize(new java.awt.Dimension(64, 35));

        searchBtn.setBackground(new java.awt.Color(0, 51, 255));
        searchBtn.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        searchBtn.setForeground(new java.awt.Color(255, 255, 255));
        searchBtn.setText("Rechercher");
        searchBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchBtn.setMargin(new java.awt.Insets(5, 14, 5, 14));
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        searchBtn1.setBackground(new java.awt.Color(102, 153, 255));
        searchBtn1.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        searchBtn1.setForeground(new java.awt.Color(255, 255, 255));
        searchBtn1.setText("Effacer");
        searchBtn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchBtn1.setMargin(new java.awt.Insets(4, 14, 4, 14));
        searchBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtn1ActionPerformed(evt);
            }
        });

        jLabel15.setText("Indiquer le nom à chercher");

        noPostedEmployeeCheckBox.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        noPostedEmployeeCheckBox.setText("Afficher les employées non afféctés");
        noPostedEmployeeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noPostedEmployeeCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel15)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(noPostedEmployeeCheckBox)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtn1)))
                .addContainerGap(609, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn)
                    .addComponent(searchBtn1))
                .addGap(18, 18, 18)
                .addComponent(noPostedEmployeeCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(240, 240, 240));
        jPanel4.setLayout(new java.awt.BorderLayout());

        employeeFormContainer.setBackground(new java.awt.Color(240, 240, 240));
        employeeFormContainer.setPreferredSize(new java.awt.Dimension(520, 477));
        employeeFormContainer.setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setBackground(new java.awt.Color(240, 240, 240));
        jTabbedPane1.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N

        addEmployeeTab.setBackground(new java.awt.Color(240, 240, 240));
        addEmployeeTab.setPreferredSize(new java.awt.Dimension(500, 600));

        jLabel2.setText("Nom");

        employeeLastNameTextField.setInputVerifier(new JInputValidator(employeeLastNameTextField, true,true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences){
                if(employeeLastNameTextField.getText().length() < 3 && !reseted){
                    return new Validation(Validation.Type.DANGER, "Too short", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
            }
        });
        employeeLastNameTextField.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
            public void removeUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
            public void insertUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
        });
        employeeLastNameTextField.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                employeeLastNameTextFieldInputMethodTextChanged(evt);
            }
        });
        employeeLastNameTextField.setPreferredSize(new java.awt.Dimension(64, 35));
        employeeLastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeLastNameTextFieldActionPerformed(evt);
            }
        });
        employeeLastNameTextField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                employeeLastNameTextFieldPropertyChange(evt);
            }
        });

        jLabel3.setText("Prénom(s)*");

        employeeFirstNameTextField.setInputVerifier(new JInputValidator(employeeFirstNameTextField, true,true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences){
                if(employeeFirstNameTextField.getText().length() < 3  && !reseted){
                    return new Validation(Validation.Type.DANGER, "Trop court", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
            }
        });
        employeeFirstNameTextField.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
            public void removeUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
            public void insertUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
        });

        employeeEmailTextField.setInputVerifier(new JInputValidator(employeeEmailTextField, true,true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences){
                if(!isEmailValid(employeeEmailTextField) && !reseted){
                    return new Validation(Validation.Type.DANGER, "Email invalide", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
            }
        });
        employeeEmailTextField.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
            public void removeUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
            public void insertUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
        });
        employeeFirstNameTextField.setPreferredSize(new java.awt.Dimension(64, 35));

        employeeEmailTextField.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel4.setText("Email*");

        jLabel5.setText("Civilité*");

        employeeCivilityComboBox.setBackground(new java.awt.Color(255, 255, 254));
        employeeCivilityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mr", "Mlle", "Mme" }));
        employeeCivilityComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeCivilityComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        employeeCivilityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeCivilityComboBoxActionPerformed(evt);
            }
        });

        addEmployeeBtn.setBackground(new java.awt.Color(0, 51, 255));
        addEmployeeBtn.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        addEmployeeBtn.setForeground(new java.awt.Color(255, 255, 255));
        addEmployeeBtn.setText("Ajouter");
        addEmployeeBtn.setEnabled(false);
        addEmployeeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addEmployeeBtn.setMargin(new java.awt.Insets(5, 14, 5, 14));
        addEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEmployeeBtnActionPerformed(evt);
            }
        });

        jLabel11.setText("Poste");

        employeeJobTextField.setInputVerifier(new JInputValidator(employeeJobTextField, true,true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences){
                if(employeeJobTextField.getText().length() < 3 && !reseted){
                    return new Validation(Validation.Type.DANGER, "Trop court", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
            }
        });
        employeeJobTextField.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
            public void removeUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
            public void insertUpdate(DocumentEvent e){
                reseted = false;
                enableSubmit();
            }
        });
        employeeJobTextField.setMinimumSize(new java.awt.Dimension(64, 35));
        employeeJobTextField.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel12.setText("Lieux");

        ArrayList<Place> places = Place.getAll(this.connection);

        for(Place place : places) {
            employeePlaceComboBox.addItem(
                new utils.ComboItem(place.getFullName(),place.getId().toString())
            );
        }
        employeePlaceComboBox.setBackground(new java.awt.Color(255, 255, 254));
        employeePlaceComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeePlaceComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        employeePlaceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeePlaceComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addEmployeeTabLayout = new javax.swing.GroupLayout(addEmployeeTab);
        addEmployeeTab.setLayout(addEmployeeTabLayout);
        addEmployeeTabLayout.setHorizontalGroup(
            addEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addEmployeeTabLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(addEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(employeePlaceComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addComponent(employeeJobTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(employeeLastNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(employeeFirstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(employeeEmailTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(employeeCivilityComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addEmployeeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        addEmployeeTabLayout.setVerticalGroup(
            addEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addEmployeeTabLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeEmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeCivilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeJobTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeePlaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(addEmployeeBtn)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ajouter un employé", addEmployeeTab);

        updateEmployeeTab.setBackground(new java.awt.Color(240, 240, 240));

        jLabel7.setText("Civilité*");

        employeeCivilityComboBox1.setBackground(new java.awt.Color(255, 255, 254));
        employeeCivilityComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mr", "Mlle", "Mme" }));
        employeeCivilityComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeCivilityComboBox1.setPreferredSize(new java.awt.Dimension(72, 35));
        employeeCivilityComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeCivilityComboBox1ActionPerformed(evt);
            }
        });

        employeeEmailTextFieldToUpdate.setInputVerifier(new JInputValidator(employeeEmailTextFieldToUpdate, true,true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences){
                if(!isEmailValid(employeeEmailTextFieldToUpdate) && !updateReseted){
                    return new Validation(Validation.Type.DANGER, "Email invalide", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
            }
        });
        employeeEmailTextFieldToUpdate.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
            public void removeUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
            public void insertUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
        });

        jLabel8.setText("Email*");

        employeeFirstNameTextFieldToUpdate.setInputVerifier(new JInputValidator(employeeFirstNameTextFieldToUpdate, true,true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences){
                if(employeeFirstNameTextFieldToUpdate.getText().length() < 3  && !updateReseted){
                    return new Validation(Validation.Type.DANGER, "Trop court", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
            }
        });
        employeeFirstNameTextFieldToUpdate.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
            public void removeUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
            public void insertUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
        });
        employeeEmailTextFieldToUpdate.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel8.setText("Email*");

        employeeFirstNameTextFieldToUpdate.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel9.setText("Prénom(s)*");

        employeeLastNameTextFieldToUpdate.setPreferredSize(new java.awt.Dimension(64, 35));
        employeeLastNameTextFieldToUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeLastNameTextFieldToUpdateActionPerformed(evt);
            }
        });
        employeeLastNameTextFieldToUpdate.setInputVerifier(new JInputValidator(employeeLastNameTextFieldToUpdate, true,true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences){
                if(employeeLastNameTextFieldToUpdate.getText().length() < 3 && !updateReseted){
                    return new Validation(Validation.Type.DANGER, "Too short", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
            }
        });
        employeeLastNameTextFieldToUpdate.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                updateReseted = false;
                enableSubmit();
            }
            public void removeUpdate(DocumentEvent e){
                updateReseted = false;
                enableSubmit();
            }
            public void insertUpdate(DocumentEvent e){
                updateReseted = false;
                enableSubmit();
            }
        });

        jLabel10.setText("Nom");

        updateEmployeeBtn.setBackground(new java.awt.Color(0, 51, 255));
        updateEmployeeBtn.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        updateEmployeeBtn.setForeground(new java.awt.Color(255, 255, 255));
        updateEmployeeBtn.setText("Sauvegarder les changements");
        updateEmployeeBtn.setEnabled(false);
        updateEmployeeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateEmployeeBtn.setMargin(new java.awt.Insets(5, 14, 5, 14));
        updateEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEmployeeBtnActionPerformed(evt);
            }
        });

        jLabel13.setText("Poste");

        employeeJobTextFieldToUpdate.setInputVerifier(new JInputValidator(employeeJobTextFieldToUpdate, true,true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences){
                if(employeeJobTextFieldToUpdate.getText().length() < 3 && !updateReseted){
                    return new Validation(Validation.Type.DANGER, "Trop court", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
            }
        });
        employeeJobTextFieldToUpdate.getDocument().addDocumentListener(new DocumentListener(){
            public void changedUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
            public void removeUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
            public void insertUpdate(DocumentEvent e){
                updateReseted = false;
                enableUpdateSubmit();
            }
        });
        employeeJobTextFieldToUpdate.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel14.setText("Lieux");

        ArrayList<Place> placeList = Place.getAll(this.connection);

        for(Place place : placeList) {
            employeePlaceComboBoxToUpdate.addItem(
                new utils.ComboItem(place.getFullName(),place.getId().toString())
            );
        }
        employeePlaceComboBoxToUpdate.setBackground(new java.awt.Color(255, 255, 254));
        employeePlaceComboBoxToUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeePlaceComboBoxToUpdate.setPreferredSize(new java.awt.Dimension(72, 35));
        employeePlaceComboBoxToUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeePlaceComboBoxToUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout updateEmployeeTabLayout = new javax.swing.GroupLayout(updateEmployeeTab);
        updateEmployeeTab.setLayout(updateEmployeeTabLayout);
        updateEmployeeTabLayout.setHorizontalGroup(
            updateEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateEmployeeTabLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(updateEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updateEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(employeePlaceComboBoxToUpdate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addComponent(employeeJobTextFieldToUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(updateEmployeeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(updateEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel10)
                        .addComponent(employeeLastNameTextFieldToUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addComponent(employeeFirstNameTextFieldToUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(employeeEmailTextFieldToUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(employeeCivilityComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        updateEmployeeTabLayout.setVerticalGroup(
            updateEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateEmployeeTabLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeLastNameTextFieldToUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeFirstNameTextFieldToUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeEmailTextFieldToUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeCivilityComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeJobTextFieldToUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeePlaceComboBoxToUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(updateEmployeeBtn)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mettre à jour un employé", updateEmployeeTab);

        deleteEmployeeTab.setBackground(new java.awt.Color(240, 240, 240));

        jLabel6.setText("Supprimer l'employé selectioné");

        deleteEmployeeBtn.setBackground(new java.awt.Color(255, 0, 51));
        deleteEmployeeBtn.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        deleteEmployeeBtn.setForeground(new java.awt.Color(255, 255, 255));
        deleteEmployeeBtn.setText("Supprimer");
        deleteEmployeeBtn.setEnabled(false);
        deleteEmployeeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteEmployeeBtn.setMargin(new java.awt.Insets(5, 14, 5, 14));
        deleteEmployeeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEmployeeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout deleteEmployeeTabLayout = new javax.swing.GroupLayout(deleteEmployeeTab);
        deleteEmployeeTab.setLayout(deleteEmployeeTabLayout);
        deleteEmployeeTabLayout.setHorizontalGroup(
            deleteEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteEmployeeTabLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(deleteEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(deleteEmployeeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(229, Short.MAX_VALUE))
        );
        deleteEmployeeTabLayout.setVerticalGroup(
            deleteEmployeeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteEmployeeTabLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteEmployeeBtn)
                .addContainerGap(519, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Supprimer un employé", deleteEmployeeTab);

        employeeFormContainer.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel4.add(employeeFormContainer, java.awt.BorderLayout.LINE_END);

        JScrollPane.setBackground(new java.awt.Color(240, 240, 240));
        JScrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        JScrollPane.setPreferredSize(new java.awt.Dimension(600, 406));

        employeeTable.setAutoCreateRowSorter(true);
        employeeTable.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Nom", "Prénoms", "Email", "Civilité", "Travail", "Lieu de travail"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        employeeTable.setGridColor(new java.awt.Color(255, 255, 255));
        employeeTable.setIntercellSpacing(new java.awt.Dimension(3, 0));
        employeeTable.setRowHeight(50);
        employeeTable.setSelectionBackground(new java.awt.Color(102, 102, 255));
        employeeTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        employeeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        employeeTable.setShowVerticalLines(true);
        employeeTable.getTableHeader().setReorderingAllowed(false);
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employeeTableMouseClicked(evt);
            }
        });
        JScrollPane.setViewportView(employeeTable);
        if (employeeTable.getColumnModel().getColumnCount() > 0) {
            employeeTable.getColumnModel().getColumn(0).setMaxWidth(40);
            employeeTable.getColumnModel().getColumn(4).setMaxWidth(60);
        }
        employeeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event){
                if(!event.getValueIsAdjusting() && employeeTable.getSelectedRow() != -1){
                    deleteEmployeeBtn.setEnabled(true);
                }else{
                    deleteEmployeeBtn.setEnabled(false);
                }
            }
        });

        jPanel4.add(JScrollPane, java.awt.BorderLayout.CENTER);

        add(jPanel4, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addEmployeeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEmployeeBtnActionPerformed
        String lastName = employeeLastNameTextField.getText();
        String firstName = employeeFirstNameTextField.getText();
        String email = employeeEmailTextField.getText();
        String civility = employeeCivilityComboBox.getSelectedItem().toString();
        String job = employeeJobTextField.getText();
        Integer placeId = Integer.parseInt(((ComboItem) (employeePlaceComboBox.getSelectedItem())).getValue());

        Employee.create(this.connection, lastName, firstName, civility, email, job, placeId);
        loadEmployee();
        resetAddForm();
    }//GEN-LAST:event_addEmployeeBtnActionPerformed

    private void employeeLastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_employeeLastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_employeeLastNameTextFieldActionPerformed

    private void employeeCivilityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_employeeCivilityComboBoxActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_employeeCivilityComboBoxActionPerformed

    private void deleteEmployeeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteEmployeeBtnActionPerformed
        if (employeeTable.getSelectedRows().length == 0) {
            JOptionPane.showMessageDialog(parent, "Vous devez selectionner au moins une ligne de la table",
                    "Aucune ligne selectionnée", JOptionPane.ERROR_MESSAGE);
        } else {
            int row = employeeTable.getSelectedRow();

            TableModel jtable = employeeTable.getModel();

            if (employeeTable.getSelectedRows().length == 1) {
                String id = jtable.getValueAt(row, 0).toString();

                Employee.destroy(this.connection, id);
                loadEmployee();
            }
        }
    }// GEN-LAST:event_deleteEmployeeBtnActionPerformed

    private void employeeCivilityComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_employeeCivilityComboBox1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_employeeCivilityComboBox1ActionPerformed

    private void employeeLastNameTextFieldToUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_employeeLastNameTextFieldToUpdateActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_employeeLastNameTextFieldToUpdateActionPerformed

    private void updateEmployeeBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updateEmployeeBtnActionPerformed
        if (employeeTable.getSelectedRows().length == 0) {
            JOptionPane.showMessageDialog(parent, "Vous devez selectionner au moins une ligne de la table",
                    "Aucune ligne selectionnée", JOptionPane.ERROR_MESSAGE);
        } else {
            int row = employeeTable.getSelectedRow();

            TableModel jtable = employeeTable.getModel();

            if (employeeTable.getSelectedRows().length == 1) {
                String id = jtable.getValueAt(row, 0).toString();

                String lastName = employeeLastNameTextFieldToUpdate.getText();
                String firstName = employeeFirstNameTextFieldToUpdate.getText();
                String email = employeeEmailTextFieldToUpdate.getText();
                String civility = employeeCivilityComboBox1.getSelectedItem().toString();
                String job = employeeJobTextFieldToUpdate.getText();
                Integer placeId = Integer
                        .parseInt(((ComboItem) (employeePlaceComboBoxToUpdate.getSelectedItem())).getValue());

                Employee.update(this.connection, id, lastName, firstName, civility, email, job, placeId);

                loadEmployee();
            }
        }
    }// GEN-LAST:event_updateEmployeeBtnActionPerformed

    private void employeeTableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_employeeTableMouseClicked
        addInitialValuesToUpdateForm();
    }// GEN-LAST:event_employeeTableMouseClicked

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchBtnActionPerformed
        ArrayList<Employee> employees = Employee.getByName(this.connection, searchTextField.getText());

        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();

        // Reset the row table
        model.setRowCount(0);

        // Add the new rows
        for (Employee employee : employees) {

            Object[] row = new Object[7];
            row[0] = employee.getId();
            row[1] = employee.getFirstName();
            row[2] = employee.getLastName();
            row[3] = employee.getEmail();
            row[4] = employee.getCivility();
            row[5] = employee.getJob();
            row[6] = employee.getPlace().getFullName();

            model.addRow(row);
        }
    }// GEN-LAST:event_searchBtnActionPerformed

    private void searchBtn1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchBtn1ActionPerformed
        searchTextField.setText("");
        loadEmployee();
    }// GEN-LAST:event_searchBtn1ActionPerformed

    private void employeePlaceComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_employeePlaceComboBoxActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_employeePlaceComboBoxActionPerformed

    private void employeePlaceComboBoxToUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_employeePlaceComboBoxToUpdateActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_employeePlaceComboBoxToUpdateActionPerformed

    private void noPostedEmployeeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_noPostedEmployeeCheckBoxActionPerformed
        if (noPostedEmployeeCheckBox.isSelected()) {
            if (this.employees != null) {
                this.employees.clear();
            }

            this.employees = Employee.getNoPostedEmployees(this.connection);

            DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();

            // Reset the row table
            model.setRowCount(0);

            // Add the new rows
            for (Employee employee : employees) {

                Object[] row = new Object[7];
                row[0] = employee.getId();
                row[1] = employee.getFirstName();
                row[2] = employee.getLastName();
                row[3] = employee.getEmail();
                row[4] = employee.getCivility();
                row[5] = employee.getJob();
                row[6] = employee.getPlace().getFullName();

                model.addRow(row);
            }
        } else {
            loadEmployee();
        }
    }// GEN-LAST:event_noPostedEmployeeCheckBoxActionPerformed

    private void employeeLastNameTextFieldPropertyChange(java.beans.PropertyChangeEvent evt) {// GEN-FIRST:event_employeeLastNameTextFieldPropertyChange

    }// GEN-LAST:event_employeeLastNameTextFieldPropertyChange

    private void employeeLastNameTextFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {// GEN-FIRST:event_employeeLastNameTextFieldInputMethodTextChanged
        System.out.println("ch");
    }// GEN-LAST:event_employeeLastNameTextFieldInputMethodTextChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JScrollPane;
    private javax.swing.JButton addEmployeeBtn;
    private javax.swing.JPanel addEmployeeTab;
    private javax.swing.JButton deleteEmployeeBtn;
    private javax.swing.JPanel deleteEmployeeTab;
    private javax.swing.JComboBox<String> employeeCivilityComboBox;
    private javax.swing.JComboBox<String> employeeCivilityComboBox1;
    private javax.swing.JTextField employeeEmailTextField;
    private javax.swing.JTextField employeeEmailTextFieldToUpdate;
    private javax.swing.JTextField employeeFirstNameTextField;
    private javax.swing.JTextField employeeFirstNameTextFieldToUpdate;
    private javax.swing.JPanel employeeFormContainer;
    private javax.swing.JTextField employeeJobTextField;
    private javax.swing.JTextField employeeJobTextFieldToUpdate;
    private javax.swing.JTextField employeeLastNameTextField;
    private javax.swing.JTextField employeeLastNameTextFieldToUpdate;
    private javax.swing.JComboBox<utils.ComboItem> employeePlaceComboBox;
    private javax.swing.JComboBox<utils.ComboItem> employeePlaceComboBoxToUpdate;
    private javax.swing.JTable employeeTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox noPostedEmployeeCheckBox;
    private javax.swing.JButton searchBtn;
    private javax.swing.JButton searchBtn1;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JButton updateEmployeeBtn;
    private javax.swing.JPanel updateEmployeeTab;
    // End of variables declaration//GEN-END:variables
}
