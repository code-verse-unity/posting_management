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
import javax.swing.table.TableModel;

import io.github.cdimascio.dotenv.Dotenv;
import model.Employee;
import model.Place;
import model.Posting;
import utils.DateFormatter;
import utils.EmailSender;
import utils.GeneratePDF;
import utils.OpenFolder;

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

    public void refillEditPostingForm() {
        this.refillEditPostingEmployeeJComboBox();
        this.refillEditPostingPlaceJComboBox();
        this.refillStartDateToUpdateJDateChooser();
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
        addPostingFormJLabel2 = new javax.swing.JLabel();
        addPostingEmployeeJLabel2 = new javax.swing.JLabel();
        employeeToUpdateComboBox = new javax.swing.JComboBox<>();
        addPostingPlaceJLabel2 = new javax.swing.JLabel();
        placeToUpdateComboBox = new javax.swing.JComboBox<>();
        addPostingStartDateJLabel2 = new javax.swing.JLabel();
        startDateToUpdateJDateChooser = new com.toedter.calendar.JDateChooser();
        updatePostingBtn = new javax.swing.JButton();
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

        setLayout(new java.awt.GridLayout(1, 0));

        postingTabJPanel.setLayout(new java.awt.BorderLayout());

        searchPostingJPanel.setPreferredSize(new java.awt.Dimension(1586, 120));

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
        clearSeachPostingJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSeachPostingJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchPostingJPanelLayout = new javax.swing.GroupLayout(searchPostingJPanel);
        searchPostingJPanel.setLayout(searchPostingJPanelLayout);
        searchPostingJPanelLayout.setHorizontalGroup(
            searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                        .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startDateJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(searchPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(endDateJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(searchPostingJPanelLayout.createSequentialGroup()
                                .addComponent(endDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchPostingJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearSeachPostingJButton))))
                    .addComponent(searchPostingJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(130, Short.MAX_VALUE))
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
                .addContainerGap(18, Short.MAX_VALUE))
        );

        postingTabJPanel.add(searchPostingJPanel, java.awt.BorderLayout.PAGE_START);

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
                        .addContainerGap(84, Short.MAX_VALUE))))
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
                .addContainerGap(765, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Ajouter une afféctation", jPanel6);

        addPostingFormJLabel2.setText("Veuiller remplir le formulaire :");

        addPostingEmployeeJLabel2.setText("Employé :");

        employeeToUpdateComboBox.setModel(new DefaultComboBoxModel<Employee>());
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

        placeToUpdateComboBox.setModel(new DefaultComboBoxModel<Place>());
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

        updatePostingBtn.setText("Enregistrer les modifications");
        updatePostingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePostingBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addPostingFormJLabel2)
                    .addComponent(addPostingEmployeeJLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeToUpdateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPostingPlaceJLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(placeToUpdateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPostingStartDateJLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(updatePostingBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(startDateToUpdateJDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(18, 18, 18)
                .addComponent(updatePostingBtn)
                .addContainerGap(765, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Mettre à jour l'affectation", jPanel7);

        jPanel2.add(jTabbedPane3);

        postingTabJPanel.add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel1.setPreferredSize(new java.awt.Dimension(1586, 70));

        deletePostingJLabel.setText("Supprimer l'affectation seletionnée :");

        deletePostingJButton.setText("Supprimer");
        deletePostingJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePostingJButtonActionPerformed(evt);
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
                    .addComponent(deletePostingJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generatePDFJLabel)
                    .addComponent(generatePDFJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(365, Short.MAX_VALUE))
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

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        postingListJTable.setAutoCreateRowSorter(true);
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
        postingListJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                postingListJTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(postingListJTable);
        if (postingListJTable.getColumnModel().getColumnCount() > 0) {
            postingListJTable.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jPanel5.add(jScrollPane1);

        postingTabJPanel.add(jPanel5, java.awt.BorderLayout.CENTER);

        postingJTabbedPanel.addTab("Afféctations", postingTabJPanel);

        historicTabjPanel.setLayout(new java.awt.BorderLayout());

        postingEmployeeJLabel.setText("Sélectionnner l'employé :");

        postingEmployeeJComboBox.setModel(new DefaultComboBoxModel<Employee>());
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
                .addContainerGap(558, Short.MAX_VALUE))
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

        jPanel4.setLayout(new java.awt.BorderLayout());

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

        postingEmployeeGeneratePDFJLabel.setText("Génerer l'arrêté de l'affectation selectionnée :");

        postingEmployeeGeneratePDFJButton.setText("Génerer en PDF");
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
                        .addGap(36, 36, 36)
                        .addComponent(postingEmployeeGeneratePDFJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        postingEmployeeOptionsJPanelLayout.setVerticalGroup(
            postingEmployeeOptionsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postingEmployeeOptionsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postingEmployeeGeneratePDFJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(postingEmployeeGeneratePDFJButton)
                .addContainerGap(1078, Short.MAX_VALUE))
        );

        jPanel4.add(postingEmployeeOptionsJPanel, java.awt.BorderLayout.LINE_END);

        historicTabjPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

        postingJTabbedPanel.addTab("Historique d'un employé", historicTabjPanel);

        add(postingJTabbedPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void clearSeachPostingJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearSeachPostingJButtonActionPerformed
        startDateJDateChooser.setDate(null);
        endDateJDateChooser.setDate(null);
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
    private com.toedter.calendar.JDateChooser startDateJDateChooser;
    private javax.swing.JLabel startDateJLabel;
    private com.toedter.calendar.JDateChooser startDateToUpdateJDateChooser;
    private javax.swing.JButton updatePostingBtn;
    // End of variables declaration//GEN-END:variables
}
