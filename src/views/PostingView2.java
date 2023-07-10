/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package views;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.event.*;

import io.github.cdimascio.dotenv.Dotenv;
import model.Employee;
import model.Place;
import model.Posting;
import utils.DateFormatter;
import utils.EmailSender;
import utils.GeneratePDF;
import utils.OpenFolder;

import com.alexandriasoftware.swing.*;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import java.beans.*;

/**
 *
 * @author olivier
 */
public class PostingView2 extends javax.swing.JPanel {

    private Connection connection;
    private JFrame parent;
    private EmailSender emailSender;
    private ArrayList<Employee> employees;
    private ArrayList<Place> places;
    private ArrayList<Posting> postings;
    private Dotenv dotenv;
    
    boolean reseted = false;

    /**
     * Creates new form PostingView2
     */
    public PostingView2(Connection connection, EmailSender emailSender, Dotenv dotenv, JFrame parent) {
        initComponents();
        this.connection = connection;
        this.parent = parent;
        this.emailSender = emailSender;
        this.dotenv = dotenv;

        this.employees = Employee.getAll(connection);
        this.places = Place.getAll(connection);

        this.refillPostingListJTable();
        this.refillPostingEmployeeJComboBox();
        this.refillAddPostingForm();
    }

    public void reloadPostings() {
        Date startDate = startDateJDateChooser.getDate();
        Date endDate = endDateJDateChooser.getDate();
        if (startDate != null && endDate != null) {
            this.postings = Posting.getBetweenDates(connection, startDate, endDate);
        } else {
            this.postings = Posting.getAll(connection);
        }
    }

    public void refillPostingListJTable() {
        reloadPostings();

        DefaultTableModel postingModel = (DefaultTableModel) postingListJTable.getModel();

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
        DefaultComboBoxModel<Employee> comboBoxModel = (DefaultComboBoxModel<Employee>) postingEmployeeJComboBox
                .getModel();

        for (Employee employee : this.employees) {
            comboBoxModel.addElement(employee);
        }
    }

    private Posting postingSelected = null;

    public void setPostingSelected(Posting postingSelected) {
        this.postingSelected = postingSelected;
    }

    public Posting getPostingSelected() {
        return this.postingSelected;
    }

    private Posting postingEmployeeSelected = null;

    public void setPostingEmployeeSelected(Posting posting) {
        this.postingEmployeeSelected = posting;
    }

    public Posting getPostingEmployeeSelected() {
        return this.postingEmployeeSelected;
    }

    public void refillPostingEmployeeListJTable() {
        DefaultTableModel postingEmployeeModel = (DefaultTableModel) postingEmployeeListJTable.getModel();
        postingEmployeeModel.setRowCount(0);

        ArrayList<Posting> postings = Posting.getByEmployeeId(this.connection,
                this.getPostingEmployeeIdSelected());

        for (Posting posting : postings) {
            Object[] row = new Object[6];
            row[0] = posting.getId();
            row[1] = posting.getEmployee().getCivility() + " " +
                    posting.getEmployee().getLastName() + " "
                    + posting.getEmployee().getFirstName();
            row[2] = posting.getOldPlace().getName() + ", " +
                    posting.getOldPlace().getProvince();
            row[3] = posting.getPlace().getName() + ", " +
                    posting.getPlace().getProvince();
            row[4] = DateFormatter.format(posting.getPostingDate());
            row[5] = DateFormatter.format(posting.getServiceDate());

            postingEmployeeModel.addRow(row);
        }
    }

    public void refillEditPostingEmployeeJComboBox() {
        DefaultComboBoxModel<Employee> comboBoxModel = (DefaultComboBoxModel<Employee>) employeeToUpdateComboBox
                .getModel();

        comboBoxModel.removeAllElements();

        for (Employee employee : this.employees) {
            comboBoxModel.addElement(employee);

            if (employee.getId() == this.getPostingSelected().getEmployee().getId()) {
                comboBoxModel.setSelectedItem(employee);
            }
        }
    }

    public void refillEditPostingPlaceJComboBox() {
        DefaultComboBoxModel<Place> comboBoxModel = (DefaultComboBoxModel<Place>) placeToUpdateComboBox.getModel();

        comboBoxModel.removeAllElements();

        for (Place place : this.places) {
            comboBoxModel.addElement(place);

            if (place.getId() == this.getPostingSelected().getPlace().getId()) {
                comboBoxModel.setSelectedItem(place);
            }
        }
    }

    public void refillStartDateToUpdateJDateChooser() {
        startDateToUpdateJDateChooser.setDate(this.getPostingSelected().getServiceDate());
    }
// tets
    public void refillEditPostingForm() {
        this.refillEditPostingEmployeeJComboBox();
        this.refillEditPostingPlaceJComboBox();
        this.refillStartDateToUpdateJDateChooser();
    }

    // some change
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
    
    private void enableSearch() {
        boolean startDateValid = startDateJDateChooser.getDate() != null && endDateJDateChooser.getDate() != null && startDateJDateChooser.getDate().before(endDateJDateChooser.getDate()) ;
        
        if(startDateValid){
            searchPostingJButton.setEnabled(true);
        }else{
            searchPostingJButton.setEnabled(false);
        }
    }
    
    private void enableAddPosting(){
        boolean validDate = addPostingStartDateJDateChooser.getDate() != null && addPostingStartDateJDateChooser.getDate().before(new Date());
        
        if(validDate){
            addPostingBtn.setEnabled(true);
        }else{
            addPostingBtn.setEnabled(false);
        }
    }
    
    private void enableUpdatePosting(){
        boolean validDate = startDateToUpdateJDateChooser.getDate() != null;
        
        if(validDate){
            updatePostingBtn.setEnabled(true);
        }else{
            updatePostingBtn.setEnabled(false);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        postingJTabbedPanel = new javax.swing.JTabbedPane();
        postingTabJPanel = new javax.swing.JPanel();
        searchPostingJPanel = new javax.swing.JPanel();
        searchPostingJLabel = new javax.swing.JLabel();
        startDateJLabel = new javax.swing.JLabel();
        startDateJDateChooser = new com.toedter.calendar.JDateChooser();
        endDateJLabel = new javax.swing.JLabel();
        endDateJDateChooser = new com.toedter.calendar.JDateChooser();
        searchPostingJButton = new javax.swing.JButton();
        clearSeachPostingJButton = new javax.swing.JButton();
        startDateError = new javax.swing.JLabel();
        endDateError = new javax.swing.JLabel();
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
        addPostingDateError = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        addPostingFormJLabel2 = new javax.swing.JLabel();
        addPostingEmployeeJLabel2 = new javax.swing.JLabel();
        employeeToUpdateComboBox = new javax.swing.JComboBox<>();
        addPostingPlaceJLabel2 = new javax.swing.JLabel();
        placeToUpdateComboBox = new javax.swing.JComboBox<>();
        addPostingStartDateJLabel2 = new javax.swing.JLabel();
        startDateToUpdateJDateChooser = new com.toedter.calendar.JDateChooser();
        updatePostingBtn = new javax.swing.JButton();
        updatePostingDateError = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        deletePostingJLabel = new javax.swing.JLabel();
        deletePostingJButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        generatePDFJLabel = new javax.swing.JLabel();
        generatePDFJButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        postingListJTable = new javax.swing.JTable();
        historicTabjPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        postingEmployeeJLabel = new javax.swing.JLabel();
        postingEmployeeJComboBox = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        postingEmployeeListJTable = new javax.swing.JTable();
        postingEmployeeOptionsJPanel = new javax.swing.JPanel();
        postingEmployeeGeneratePDFJLabel = new javax.swing.JLabel();
        postingEmployeeGeneratePDFJButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));
        setLayout(new java.awt.GridLayout(1, 0));

        postingJTabbedPanel.setBackground(new java.awt.Color(240, 240, 240));
        postingJTabbedPanel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N

        postingTabJPanel.setBackground(new java.awt.Color(240, 240, 240));
        postingTabJPanel.setLayout(new java.awt.BorderLayout());

        searchPostingJPanel.setBackground(new java.awt.Color(240, 240, 240));
        searchPostingJPanel.setPreferredSize(new java.awt.Dimension(1586, 120));

        searchPostingJLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        searchPostingJLabel.setText("Options de recheche :");

        startDateJLabel.setText("Date de début :");

        startDateJDateChooser.addPropertyChangeListener("date", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent event){

                if(startDateJDateChooser.getDate() == null && reseted){
                    startDateError.setText("Date de début requis");
                }else if(endDateJDateChooser.getDate() != null && startDateJDateChooser.getDate() != null && startDateJDateChooser.getDate().after(endDateJDateChooser.getDate()) && reseted){
                    startDateError.setText("Date de début ne peut pas être après la date de fin de recherche");
                }else{
                    startDateError.setText("");
                }

                reseted = false;
                enableSearch();
            }

        });

        endDateJLabel.setText("Date de fin :");

        endDateJDateChooser.addPropertyChangeListener("date", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent event){

                if(endDateJDateChooser.getDate() == null && reseted){
                    endDateError.setText("Date de fin requis");
                }else if(startDateJDateChooser.getDate() != null && endDateJDateChooser.getDate() != null && startDateJDateChooser.getDate().after(endDateJDateChooser.getDate()) && reseted){
                    startDateError.setText("Date de début ne peut pas être après la date de fin de recherche");
                }else{
                    endDateError.setText("");
                    startDateError.setText("");
                }

                reseted = false;
                enableSearch();
            }

        });

        searchPostingJButton.setEnabled(false);

        endDateJLabel.setText("Date de fin :");

        endDateJDateChooser.setBackground(new java.awt.Color(255, 255, 254));
        endDateJDateChooser.setPreferredSize(new java.awt.Dimension(94, 35));

        searchPostingJButton.setBackground(new java.awt.Color(0, 51, 255));
        searchPostingJButton.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        searchPostingJButton.setForeground(new java.awt.Color(255, 255, 255));
        searchPostingJButton.setText("Rechercher");
        searchPostingJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchPostingJButton.setMargin(new java.awt.Insets(5, 14, 5, 14));
        searchPostingJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPostingJButtonActionPerformed(evt);
            }
        });

        clearSeachPostingJButton.setBackground(new java.awt.Color(102, 153, 255));
        clearSeachPostingJButton.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        clearSeachPostingJButton.setForeground(new java.awt.Color(255, 255, 255));
        clearSeachPostingJButton.setText("Effacer");
        clearSeachPostingJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearSeachPostingJButton.setMargin(new java.awt.Insets(5, 14, 5, 14));
        clearSeachPostingJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSeachPostingJButtonActionPerformed(evt);
            }
        });

        startDateError.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        startDateError.setForeground(new java.awt.Color(153, 0, 0));

        javax.swing.GroupLayout searchPostingJPanelLayout = new javax.swing.GroupLayout(searchPostingJPanel);
        searchPostingJPanel.setLayout(searchPostingJPanelLayout);
        searchPostingJPanelLayout.setHorizontalGroup(
            searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchPostingJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                        .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(startDateError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startDateJLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startDateJDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endDateJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                                .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(endDateError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(endDateJDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(searchPostingJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearSeachPostingJButton)))))
                .addContainerGap(819, Short.MAX_VALUE))
        );
        searchPostingJPanelLayout.setVerticalGroup(
            searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(searchPostingJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                        .addComponent(startDateJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                        .addComponent(endDateJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(searchPostingJButton)
                                .addComponent(clearSeachPostingJButton)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startDateError)
                    .addComponent(endDateError))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        postingTabJPanel.add(searchPostingJPanel, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(240, 240, 240));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 623));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jTabbedPane3.setBackground(new java.awt.Color(240, 240, 240));
        jTabbedPane3.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(240, 240, 240));

        addPostingFormJLabel1.setText("Veuiller remplir le formulaire :");

        addPostingEmployeeJLabel1.setText("Employé :");

        employeeComboBox.setBackground(new java.awt.Color(255, 255, 254));
        employeeComboBox.setModel(new DefaultComboBoxModel<Employee>());
        employeeComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
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

        placeComboBox.setBackground(new java.awt.Color(255, 255, 254));
        placeComboBox.setModel(new DefaultComboBoxModel<Place>());
        placeComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        placeComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
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

        addPostingStartDateJDateChooser.addPropertyChangeListener("date", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent event){

                if(addPostingStartDateJDateChooser.getDate() == null){
                    addPostingDateError.setText("Date de prise de service requis");
                }else if(addPostingStartDateJDateChooser.getDate() != null && addPostingStartDateJDateChooser.getDate().before(new Date()) ){
                    addPostingDateError.setText("Doit être postérieure à la date d'aujourd'hui");
                }else{
                    addPostingDateError.setText("");
                }

                enableAddPosting();
            }

        });

        addPostingStartDateJDateChooser.setBackground(new java.awt.Color(255, 255, 254));
        addPostingStartDateJDateChooser.setPreferredSize(new java.awt.Dimension(94, 35));

        addPostingBtn.setBackground(new java.awt.Color(0, 51, 255));
        addPostingBtn.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        addPostingBtn.setForeground(new java.awt.Color(255, 255, 255));
        addPostingBtn.setText("Ajouter");
        addPostingBtn.setEnabled(false);
        addPostingBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addPostingBtn.setMargin(new java.awt.Insets(5, 14, 5, 14));
        addPostingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPostingBtnActionPerformed(evt);
            }
        });

        addPostingDateError.setForeground(new java.awt.Color(255, 0, 51));
        addPostingDateError.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addPostingDateError.setMaximumSize(new java.awt.Dimension(80, 100));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addPostingEmployeeJLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPostingPlaceJLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(placeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPostingStartDateJLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(addPostingBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addPostingStartDateJDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                    .addComponent(addPostingFormJLabel1)
                    .addComponent(addPostingDateError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
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
                .addGap(2, 2, 2)
                .addComponent(addPostingDateError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(addPostingBtn)
                .addContainerGap(741, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Ajouter une afféctation", jPanel6);

        jPanel7.setBackground(new java.awt.Color(240, 240, 240));

        addPostingFormJLabel2.setText("Veuiller remplir le formulaire :");

        addPostingEmployeeJLabel2.setText("Employé :");

        employeeToUpdateComboBox.setBackground(new java.awt.Color(255, 255, 254));
        employeeToUpdateComboBox.setModel(new DefaultComboBoxModel<Employee>());
        employeeToUpdateComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employeeToUpdateComboBox.setOpaque(true);
        employeeToUpdateComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        employeeToUpdateComboBox.setRenderer(new DefaultListCellRenderer() {
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

        addPostingPlaceJLabel2.setText("Lieu d'afféctation :");

        placeToUpdateComboBox.setBackground(new java.awt.Color(255, 255, 254));
        placeToUpdateComboBox.setModel(new DefaultComboBoxModel<Place>());
        placeToUpdateComboBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        placeToUpdateComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        placeToUpdateComboBox.setRenderer(new DefaultListCellRenderer() {
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

        addPostingStartDateJLabel2.setText("Date de prise de service :");

        startDateToUpdateJDateChooser.addPropertyChangeListener("date", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent event){

                if(addPostingStartDateJDateChooser.getDate() == null){
                    updatePostingDateError.setText("Date de prise de service requis");
                }else{
                    updatePostingDateError.setText("");
                }

                enableUpdatePosting();
            }

        });

        startDateToUpdateJDateChooser.setBackground(new java.awt.Color(255, 255, 254));
        startDateToUpdateJDateChooser.setPreferredSize(new java.awt.Dimension(94, 35));

        updatePostingBtn.setBackground(new java.awt.Color(0, 51, 255));
        updatePostingBtn.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        updatePostingBtn.setForeground(new java.awt.Color(255, 255, 255));
        updatePostingBtn.setText("Enregistrer les modifications");
        updatePostingBtn.setEnabled(false);
        updatePostingBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updatePostingBtn.setMargin(new java.awt.Insets(5, 14, 5, 14));
        updatePostingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePostingBtnActionPerformed(evt);
            }
        });

        updatePostingDateError.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addPostingFormJLabel2)
                    .addComponent(addPostingEmployeeJLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeToUpdateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPostingPlaceJLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(placeToUpdateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPostingStartDateJLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(updatePostingBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(startDateToUpdateJDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(updatePostingDateError))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPostingFormJLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addPostingEmployeeJLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeToUpdateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addPostingPlaceJLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeToUpdateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(addPostingStartDateJLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startDateToUpdateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(updatePostingDateError)
                .addGap(18, 18, 18)
                .addComponent(updatePostingBtn)
                .addContainerGap(750, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Mettre à jour l'affectation", jPanel7);

        jPanel2.add(jTabbedPane3);

        postingTabJPanel.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));
        jPanel1.setPreferredSize(new java.awt.Dimension(1586, 80));

        deletePostingJLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        deletePostingJLabel.setText("Supprimer l'affectation seletionnée :");

        deletePostingJButton.setBackground(new java.awt.Color(255, 0, 51));
        deletePostingJButton.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        deletePostingJButton.setForeground(new java.awt.Color(255, 255, 255));
        deletePostingJButton.setText("Supprimer");
        deletePostingJButton.setEnabled(false);
        deletePostingJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deletePostingJButton.setMargin(new java.awt.Insets(5, 14, 5, 14));
        deletePostingJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePostingJButtonActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        generatePDFJLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        generatePDFJLabel.setText("Génerer l'arrêté d'affectation :");

        generatePDFJButton.setEnabled(false);
        generatePDFJButton.setBackground(new java.awt.Color(0, 51, 255));
        generatePDFJButton.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        generatePDFJButton.setForeground(new java.awt.Color(255, 255, 255));
        generatePDFJButton.setText("Génerer en PDF");
        generatePDFJButton.setBorderPainted(false);
        generatePDFJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generatePDFJButton.setMargin(new java.awt.Insets(5, 14, 5, 14));
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
                    .addComponent(deletePostingJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generatePDFJLabel)
                    .addComponent(generatePDFJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(deletePostingJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deletePostingJButton))
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

        postingTabJPanel.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBackground(new java.awt.Color(240, 240, 240));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        postingListJTable.setAutoCreateRowSorter(true);
        postingListJTable.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        postingListJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Employée", "Lieu actuel", "Lieu d'affectation", "Date d'affection", "Date de prise de service"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        postingListJTable.setGridColor(new java.awt.Color(255, 255, 255));
        postingListJTable.setIntercellSpacing(new java.awt.Dimension(3, 0));
        postingListJTable.setRowHeight(50);
        postingListJTable.setSelectionBackground(new java.awt.Color(102, 102, 255));
        postingListJTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        postingListJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        postingListJTable.setShowVerticalLines(true);
        postingListJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                postingListJTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(postingListJTable);
        if (postingListJTable.getColumnModel().getColumnCount() > 0) {
            postingListJTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }
        postingListJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event){
                if(!event.getValueIsAdjusting() && postingListJTable.getSelectedRow() != -1){
                    deletePostingJButton.setEnabled(true);
                    generatePDFJButton.setEnabled(true);
                }else{
                    deletePostingJButton.setEnabled(false);
                    generatePDFJButton.setEnabled(false);
                }
            }
        });

        jPanel5.add(jScrollPane1);

        postingTabJPanel.add(jPanel5, java.awt.BorderLayout.CENTER);

        postingJTabbedPanel.addTab("Afféctations", postingTabJPanel);

        historicTabjPanel.setBackground(new java.awt.Color(240, 240, 240));
        historicTabjPanel.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(240, 240, 240));

        postingEmployeeJLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        postingEmployeeJLabel.setText("Sélectionnner l'employé :");

        postingEmployeeJComboBox.setBackground(new java.awt.Color(255, 255, 254));
        postingEmployeeJComboBox.setModel(new DefaultComboBoxModel<Employee>());
        postingEmployeeJComboBox.setPreferredSize(new java.awt.Dimension(72, 35));
        postingEmployeeJComboBox.setRenderer(new DefaultListCellRenderer() {
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
        postingEmployeeJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postingEmployeeJComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(postingEmployeeJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(postingEmployeeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1247, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(postingEmployeeJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(postingEmployeeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        historicTabjPanel.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(240, 240, 240));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBackground(new java.awt.Color(240, 240, 240));

        postingEmployeeListJTable.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        postingEmployeeListJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Employé", "Lieu actuel", "Lieu d'affectation", "Date d'affectation", "Date de prise de service"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        postingEmployeeListJTable.setGridColor(new java.awt.Color(255, 255, 255));
        postingEmployeeListJTable.setIntercellSpacing(new java.awt.Dimension(3, 0));
        postingEmployeeListJTable.setRowHeight(50);
        postingEmployeeListJTable.setSelectionBackground(new java.awt.Color(102, 102, 255));
        postingEmployeeListJTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        postingEmployeeListJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        postingEmployeeListJTable.setShowVerticalLines(true);
        postingEmployeeListJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                postingEmployeeListJTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(postingEmployeeListJTable);
        if (postingEmployeeListJTable.getColumnModel().getColumnCount() > 0) {
            postingEmployeeListJTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        postingEmployeeOptionsJPanel.setBackground(new java.awt.Color(240, 240, 240));
        postingEmployeeOptionsJPanel.setPreferredSize(new java.awt.Dimension(417, 714));

        postingEmployeeGeneratePDFJLabel.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        postingEmployeeGeneratePDFJLabel.setText("Génerer l'arrêté de l'affectation selectionnée :");

        postingEmployeeGeneratePDFJButton.setBackground(new java.awt.Color(0, 51, 255));
        postingEmployeeGeneratePDFJButton.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        postingEmployeeGeneratePDFJButton.setForeground(new java.awt.Color(255, 255, 255));
        postingEmployeeGeneratePDFJButton.setText("Génerer en PDF");
        postingEmployeeGeneratePDFJButton.setMargin(new java.awt.Insets(5, 14, 5, 14));
        postingEmployeeGeneratePDFJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postingEmployeeGeneratePDFJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout postingEmployeeOptionsJPanelLayout = new javax.swing.GroupLayout(postingEmployeeOptionsJPanel);
        postingEmployeeOptionsJPanel.setLayout(postingEmployeeOptionsJPanelLayout);
        postingEmployeeOptionsJPanelLayout.setHorizontalGroup(
            postingEmployeeOptionsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postingEmployeeOptionsJPanelLayout.createSequentialGroup()
                .addGroup(postingEmployeeOptionsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(postingEmployeeOptionsJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(postingEmployeeGeneratePDFJLabel))
                    .addGroup(postingEmployeeOptionsJPanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(postingEmployeeGeneratePDFJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        postingEmployeeOptionsJPanelLayout.setVerticalGroup(
            postingEmployeeOptionsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postingEmployeeOptionsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postingEmployeeGeneratePDFJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(postingEmployeeGeneratePDFJButton)
                .addContainerGap(648, Short.MAX_VALUE))
        );

        jPanel4.add(postingEmployeeOptionsJPanel, java.awt.BorderLayout.LINE_END);

        historicTabjPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

        postingJTabbedPanel.addTab("Historique d'un employé", historicTabjPanel);

        add(postingJTabbedPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void clearSeachPostingJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearSeachPostingJButtonActionPerformed
        startDateJDateChooser.setDate(null);
        endDateJDateChooser.setDate(null);
        reseted = true;
        this.refillPostingListJTable();
    }// GEN-LAST:event_clearSeachPostingJButtonActionPerformed

    private void postingEmployeeGeneratePDFJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_postingEmployeeGeneratePDFJButtonActionPerformed
        Posting posting = this.getPostingEmployeeSelected();

        if (posting != null) {
            String path = GeneratePDF.generate(posting);
            OpenFolder.open(path);
        }
    }// GEN-LAST:event_postingEmployeeGeneratePDFJButtonActionPerformed

    private void postingEmployeeJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_postingEmployeeJComboBoxActionPerformed
        Employee employee = (Employee) postingEmployeeJComboBox.getSelectedItem();
        if (employee != null) {
            this.setPostingEmployeeIdSelected(employee.getId());
            this.refillPostingEmployeeListJTable();
        }
    }// GEN-LAST:event_postingEmployeeJComboBoxActionPerformed

    private void postingEmployeeListJTableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_postingEmployeeListJTableMouseClicked
        if (postingEmployeeListJTable.getSelectedRows().length == 1) {
            int row = postingEmployeeListJTable.getSelectedRow();
            TableModel jtable = postingEmployeeListJTable.getModel();
            Integer id = Integer.parseInt(jtable.getValueAt(row, 0).toString());
            this.setPostingEmployeeSelected(Posting.getById(connection, id));
        }
    }// GEN-LAST:event_postingEmployeeListJTableMouseClicked

    private void addPostingBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addPostingBtnActionPerformed
        Employee employee = (Employee) employeeComboBox.getSelectedItem();
        Integer employeeId = employee.getId();
        Integer oldPlaceId = employee.getPlace().getId();
        Integer newPlaceId = ((Place) placeComboBox.getSelectedItem()).getId();
        Date startDate = addPostingStartDateJDateChooser.getDate();

        Posting posting = Posting.create(connection, employeeId, oldPlaceId, newPlaceId, startDate);
        refillPostingListJTable();

        String subject = "Affectation";
        String contentType = "text/html; charset=utf-8";
        String body = "<!DOCTYPE html>" +
                "<html lang=\"fr\">" +
                "  <head>" +
                "    <meta charset=\"UTF-8\" />" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                "    <title>Affectation</title>" +
                "  </head>" +
                "  <body style=\"width: 100%; margin: 0; min-height: 100vh; display: flex; align-items: center; justify-content: center;\">"
                +
                "<div style=\"width: 87%;\">" +
                "    <h1 style=\"text-align: center;\">Arrêté N° " + posting.getId() + " du "
                + DateFormatter.format(posting.getPostingDate(), "dd/MM/yyyy") + "</h1>" +
                "" +
                "    <p style=\"font-size: large;\">" +
                posting.getEmployee().getCivility() + " <b>" +
                posting.getEmployee().getFullName() + "</b>, qui occupe le poste de " +
                posting.getEmployee().getJob() + " à " +
                posting.getOldPlace().getFullName() + ", est affecté à <b>" +
                posting.getPlace().getFullName() + "</b> pour compter de prise de service le <b>" +
                DateFormatter.format(posting.getPostingDate(), "dd/MM/yyyy") + "</b>." +
                "    </p>" +
                "" +
                "    <p style=\"font-size: large;\">" +
                "      Le présent communiqué sera enregistré et communiqué partout ou besoin" +
                "      sera." +
                "    </p>" +
                "</div>" +
                "  </body>" +
                "</html>";

        this.emailSender.sendEmail(
                this.dotenv.get("MAIL_USERNAME_SENDER"),
                employee.getEmail(),
                subject,
                body,
                contentType);
    }// GEN-LAST:event_addPostingBtnActionPerformed

    private void searchPostingJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchPostingJButtonActionPerformed
        this.refillPostingListJTable();
    }// GEN-LAST:event_searchPostingJButtonActionPerformed

    private void generatePDFJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_generatePDFJButtonActionPerformed
        Posting posting = this.getPostingSelected();

        if (posting != null) {
            String path = GeneratePDF.generate(posting);
            OpenFolder.open(path);
        }
    }// GEN-LAST:event_generatePDFJButtonActionPerformed

    private void deletePostingJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deletePostingJButtonActionPerformed
        if (this.postingSelected != null) {
            Posting.destroy(connection, this.postingSelected.getId());
            refillPostingListJTable();
        }

    }// GEN-LAST:event_deletePostingJButtonActionPerformed

    private void postingListJTableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_postingListJTableMouseClicked
        if (postingListJTable.getSelectedRows().length == 1) {
            int row = postingListJTable.getSelectedRow();
            TableModel jtable = postingListJTable.getModel();
            Integer id = Integer.parseInt(jtable.getValueAt(row, 0).toString());
            this.setPostingSelected(Posting.getById(connection, id));
            this.refillEditPostingForm();
        }
    }// GEN-LAST:event_postingListJTableMouseClicked

    private void updatePostingBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updatePostingBtnActionPerformed
        Employee selectedEmployee = (Employee) employeeToUpdateComboBox.getSelectedItem();
        Place selectedPlace = (Place) placeToUpdateComboBox.getSelectedItem();
        Date selectedDate = (Date) startDateToUpdateJDateChooser.getDate();

        if (selectedEmployee != null && selectedPlace != null && selectedDate != null) {
            Integer employeeId = selectedEmployee.getId();
            Integer placeId = selectedPlace.getId();

            Posting.update(this.connection, this.getPostingSelected().getId(), employeeId, placeId, selectedDate);
            this.refillPostingListJTable();
        }
    }// GEN-LAST:event_updatePostingBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPostingBtn;
    private javax.swing.JLabel addPostingDateError;
    private javax.swing.JLabel addPostingEmployeeJLabel1;
    private javax.swing.JLabel addPostingEmployeeJLabel2;
    private javax.swing.JLabel addPostingFormJLabel1;
    private javax.swing.JLabel addPostingFormJLabel2;
    private javax.swing.JLabel addPostingPlaceJLabel1;
    private javax.swing.JLabel addPostingPlaceJLabel2;
    private com.toedter.calendar.JDateChooser addPostingStartDateJDateChooser;
    private javax.swing.JLabel addPostingStartDateJLabel1;
    private javax.swing.JLabel addPostingStartDateJLabel2;
    private javax.swing.JButton clearSeachPostingJButton;
    private javax.swing.JButton deletePostingJButton;
    private javax.swing.JLabel deletePostingJLabel;
    private javax.swing.JComboBox<Employee> employeeComboBox;
    private javax.swing.JComboBox<Employee> employeeToUpdateComboBox;
    private javax.swing.JLabel endDateError;
    private com.toedter.calendar.JDateChooser endDateJDateChooser;
    private javax.swing.JLabel endDateJLabel;
    private javax.swing.JButton generatePDFJButton;
    private javax.swing.JLabel generatePDFJLabel;
    private javax.swing.JPanel historicTabjPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JComboBox<Place> placeComboBox;
    private javax.swing.JComboBox<Place> placeToUpdateComboBox;
    private javax.swing.JButton postingEmployeeGeneratePDFJButton;
    private javax.swing.JLabel postingEmployeeGeneratePDFJLabel;
    private javax.swing.JComboBox<Employee> postingEmployeeJComboBox;
    private javax.swing.JLabel postingEmployeeJLabel;
    private javax.swing.JTable postingEmployeeListJTable;
    private javax.swing.JPanel postingEmployeeOptionsJPanel;
    private javax.swing.JTabbedPane postingJTabbedPanel;
    private javax.swing.JTable postingListJTable;
    private javax.swing.JPanel postingTabJPanel;
    private javax.swing.JButton searchPostingJButton;
    private javax.swing.JLabel searchPostingJLabel;
    private javax.swing.JPanel searchPostingJPanel;
    private javax.swing.JLabel startDateError;
    private com.toedter.calendar.JDateChooser startDateJDateChooser;
    private javax.swing.JLabel startDateJLabel;
    private com.toedter.calendar.JDateChooser startDateToUpdateJDateChooser;
    private javax.swing.JButton updatePostingBtn;
    private javax.swing.JLabel updatePostingDateError;
    // End of variables declaration//GEN-END:variables
}
