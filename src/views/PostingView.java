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

import model.Employee;
import model.Place;
import model.Posting;
import utils.DateFormatter;

/**
 *
 * @author olivier
 */
public class PostingView extends javax.swing.JPanel {

    private Connection connection;
    private JFrame parent;
    private ArrayList<Employee> employees;
    private ArrayList<Place> places;
    private ArrayList<Posting> postings;

    /**
     * Creates new form PostingView
     */
    public PostingView(Connection connection, JFrame parent) {
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
        DefaultComboBoxModel<Employee> comboBoxModel = (DefaultComboBoxModel<Employee>) postingEmployeeJComboBox.getModel();

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

    public void refillPostingEmployeeListJTable() {
        DefaultTableModel postingEmployeeModel = (DefaultTableModel) postingEmployeeListJTable.getModel();

        postingEmployeeModel.setRowCount(0);

        ArrayList<Posting> postings = Posting.getByEmployeeId(this.connection, this.getPostingEmployeeIdSelected());

        for (Posting posting : postings) {
            Object[] row = new Object[6];
            row[0] = posting.getId();
            row[1] = posting.getEmployee().getCivility() + " " + posting.getEmployee().getLastName() + " "
                    + posting.getEmployee().getFirstName();
            row[2] = posting.getOldPlace().getName() + ", " + posting.getOldPlace().getProvince();
            row[3] = posting.getPlace().getName() + ", " + posting.getPlace().getProvince();
            row[4] = DateFormatter.format(posting.getPostingDate());
            row[5] = DateFormatter.format(posting.getServiceDate());

            postingEmployeeModel.addRow(row);
        }
    }

    public void refillEditPostingEmployeeJComboBox() {
        DefaultComboBoxModel<Employee> comboBoxModel = (DefaultComboBoxModel<Employee>) editPostingEmployeeJComboBox.getModel();
    
        comboBoxModel.removeAllElements();
    
        for (Employee employee : this.employees) {
            comboBoxModel.addElement(employee);
    
            if (employee.getId() == this.getPostingSelected().getEmployee().getId()) {
                comboBoxModel.setSelectedItem(employee);
            }
        }
    }

    public void refillEditPostingPlaceJComboBox() {
        DefaultComboBoxModel<Place> comboBoxModel = (DefaultComboBoxModel<Place>) editPostingPlaceJComboBox.getModel();
    
        comboBoxModel.removeAllElements();
    
        for (Place place : this.places) {
            comboBoxModel.addElement(place);
    
            if (place.getId() == this.getPostingSelected().getPlace().getId()) {
                comboBoxModel.setSelectedItem(place);
            }
        }
    }

    public void refillEditPostingForm() {
        this.refillEditPostingEmployeeJComboBox();
        this.refillEditPostingPlaceJComboBox();
    }

    public void refillAddPostingEmployeeJComboBox() {
        DefaultComboBoxModel<Employee> comboBoxModel = (DefaultComboBoxModel<Employee>) addPostingEmployeeJComboBox.getModel();
    
        comboBoxModel.removeAllElements();
    
        for (Employee employee : this.employees) {
            comboBoxModel.addElement(employee);
        }
    }

    public void refillAddPostingPlaceJComboBox() {
        DefaultComboBoxModel<Place> comboBoxModel = (DefaultComboBoxModel<Place>) addPostingPlaceJComboBox.getModel();
    
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooserCellEditor1 = new com.toedter.calendar.JDateChooserCellEditor();
        postingJTabbedPane = new javax.swing.JTabbedPane();
        postingListJPanel = new javax.swing.JPanel();
        addPostingFormJPanel = new javax.swing.JPanel();
        searchPostingJLabel = new javax.swing.JLabel();
        startDateJLabel = new javax.swing.JLabel();
        startDateJDateChooser = new com.toedter.calendar.JDateChooser();
        endDateJLabel = new javax.swing.JLabel();
        endDateJDateChooser = new com.toedter.calendar.JDateChooser();
        searchPostingJButton = new javax.swing.JButton();
        clearSeachPostingJButton = new javax.swing.JButton();
        postingListJLabel = new javax.swing.JLabel();
        postingListJScrollPane = new javax.swing.JScrollPane();
        postingListJTable = new javax.swing.JTable();
        postingOptionsJPanel = new javax.swing.JPanel();
        postingOptionsJTabbedPane = new javax.swing.JTabbedPane();
        addPostingJPanel = new javax.swing.JPanel();
        addPostingFormJLabel = new javax.swing.JLabel();
        addPostingEmployeeJLabel = new javax.swing.JLabel();
        addPostingEmployeeJComboBox = new javax.swing.JComboBox<>();
        addPostingPlaceJLabel = new javax.swing.JLabel();
        addPostingPlaceJComboBox = new javax.swing.JComboBox<>();
        addPostingStartDateJLabel = new javax.swing.JLabel();
        addPostingStartDateJDateChooser = new com.toedter.calendar.JDateChooser();
        addPostingJButton = new javax.swing.JButton();
        editPostingJPanel = new javax.swing.JPanel();
        editPostingFormJLabel = new javax.swing.JLabel();
        editPostingEmployeeJLabel = new javax.swing.JLabel();
        editPostingEmployeeJComboBox = new javax.swing.JComboBox<>();
        editPostingPlaceJLabel = new javax.swing.JLabel();
        editPostingPlaceJComboBox = new javax.swing.JComboBox<>();
        editPostingStartDateJLabel = new javax.swing.JLabel();
        editPostingStartDateJDateChooser = new com.toedter.calendar.JDateChooser();
        editPostingJButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        deletePostingJLabel = new javax.swing.JLabel();
        deletePostingJButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        generatePDFJLabel = new javax.swing.JLabel();
        generatePDFJButton = new javax.swing.JButton();
        postingEmployeeListJPanel = new javax.swing.JPanel();
        postingEmployeeJLabel = new javax.swing.JLabel();
        postingEmployeeJComboBox = new javax.swing.JComboBox<>();
        postingEmpoyeeListJLabel = new javax.swing.JLabel();
        postingEmployeeScrollPane = new java.awt.ScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        postingEmployeeListJTable = new javax.swing.JTable();
        postingEmployeeOptionsJPanel = new javax.swing.JPanel();
        postingEmployeeGeneratePDFJLabel = new javax.swing.JLabel();
        postingEmployeeGeneratePDFJButton = new javax.swing.JButton();
        unpostedListPanel = new javax.swing.JPanel();
        unpostedListJScrollPane = new javax.swing.JScrollPane();
        unpostedListJTable = new javax.swing.JTable();
        unpostedListJLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        setMinimumSize(new java.awt.Dimension(199, 71));
        setPreferredSize(new java.awt.Dimension(952, 611));

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

        postingListJLabel.setText("Liste des afffectatios :");

        javax.swing.GroupLayout addPostingFormJPanelLayout = new javax.swing.GroupLayout(addPostingFormJPanel);
        addPostingFormJPanel.setLayout(addPostingFormJPanelLayout);
        addPostingFormJPanelLayout.setHorizontalGroup(
            addPostingFormJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPostingFormJPanelLayout.createSequentialGroup()
                .addGroup(addPostingFormJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPostingFormJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchPostingJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(addPostingFormJPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(addPostingFormJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(startDateJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endDateJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(addPostingFormJPanelLayout.createSequentialGroup()
                                .addComponent(searchPostingJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearSeachPostingJButton))
                            .addComponent(endDateJDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startDateJDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(addPostingFormJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(postingListJLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addPostingFormJPanelLayout.setVerticalGroup(
            addPostingFormJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPostingFormJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchPostingJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startDateJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(endDateJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(addPostingFormJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchPostingJButton)
                    .addComponent(clearSeachPostingJButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(postingListJLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        postingListJScrollPane.setPreferredSize(new java.awt.Dimension(1000, 1000));

        postingListJTable.setAutoCreateRowSorter(true);
        postingListJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Employé", "De", "Affecté à", "Afffecté le", "Prise de Service le"
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
        postingListJScrollPane.setViewportView(postingListJTable);
        if (postingListJTable.getColumnModel().getColumnCount() > 0) {
            postingListJTable.getColumnModel().getColumn(0).setMaxWidth(50);
            postingListJTable.getColumnModel().getColumn(4).setPreferredWidth(150);
            postingListJTable.getColumnModel().getColumn(4).setMaxWidth(150);
            postingListJTable.getColumnModel().getColumn(5).setPreferredWidth(150);
            postingListJTable.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        addPostingFormJLabel.setText("Veuiller remplir le formulaire :");

        addPostingEmployeeJLabel.setText("Employé :");

        addPostingEmployeeJComboBox.setModel(new DefaultComboBoxModel<Employee>());
        addPostingEmployeeJComboBox.setRenderer(new DefaultListCellRenderer() {
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

        addPostingPlaceJLabel.setText("Lieu d'afféctation :");

        addPostingPlaceJComboBox.setModel(new DefaultComboBoxModel<Place>());
        addPostingPlaceJComboBox.setRenderer(new DefaultListCellRenderer() {
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

        addPostingStartDateJLabel.setText("Date de prise de service :");

        addPostingJButton.setText("Ajouter");
        addPostingJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPostingJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addPostingJPanelLayout = new javax.swing.GroupLayout(addPostingJPanel);
        addPostingJPanel.setLayout(addPostingJPanelLayout);
        addPostingJPanelLayout.setHorizontalGroup(
            addPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPostingJPanelLayout.createSequentialGroup()
                .addGroup(addPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPostingJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addPostingFormJLabel))
                    .addGroup(addPostingJPanelLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(addPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(addPostingPlaceJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addPostingEmployeeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(addPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(addPostingEmployeeJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(addPostingPlaceJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(addPostingStartDateJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(addPostingJPanelLayout.createSequentialGroup()
                                    .addGap(38, 38, 38)
                                    .addComponent(addPostingStartDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(addPostingJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        addPostingJPanelLayout.setVerticalGroup(
            addPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addPostingJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addPostingFormJLabel)
                .addGap(18, 18, 18)
                .addComponent(addPostingEmployeeJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addPostingEmployeeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addPostingPlaceJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addPostingPlaceJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addPostingStartDateJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addPostingStartDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addPostingJButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        postingOptionsJTabbedPane.addTab("Ajouter une afféctation", addPostingJPanel);

        editPostingFormJLabel.setText("Veuiller remplir le formulaire :");

        editPostingEmployeeJLabel.setText("Employé :");

        editPostingEmployeeJComboBox.setModel(new DefaultComboBoxModel<Employee>());
        editPostingEmployeeJComboBox.setRenderer(new DefaultListCellRenderer() {
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
        editPostingEmployeeJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPostingEmployeeJComboBoxActionPerformed(evt);
            }
        });

        editPostingPlaceJLabel.setText("Lieu d'affectation :");

        editPostingPlaceJComboBox.setModel(new DefaultComboBoxModel<Place>());
        editPostingPlaceJComboBox.setRenderer(new DefaultListCellRenderer() {
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

        editPostingStartDateJLabel.setText("Date de prise de service :");

        editPostingJButton.setText("Enregistrer");

        javax.swing.GroupLayout editPostingJPanelLayout = new javax.swing.GroupLayout(editPostingJPanel);
        editPostingJPanel.setLayout(editPostingJPanelLayout);
        editPostingJPanelLayout.setHorizontalGroup(
            editPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPostingJPanelLayout.createSequentialGroup()
                .addGroup(editPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editPostingJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editPostingStartDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editPostingPlaceJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(editPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(editPostingJPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(editPostingFormJLabel))
                        .addGroup(editPostingJPanelLayout.createSequentialGroup()
                            .addGap(80, 80, 80)
                            .addComponent(editPostingEmployeeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(editPostingJPanelLayout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addGroup(editPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(editPostingPlaceJLabel)
                                .addComponent(editPostingEmployeeJLabel)
                                .addComponent(editPostingStartDateJLabel)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editPostingJPanelLayout.setVerticalGroup(
            editPostingJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPostingJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editPostingFormJLabel)
                .addGap(18, 18, 18)
                .addComponent(editPostingEmployeeJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editPostingEmployeeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editPostingPlaceJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editPostingPlaceJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editPostingStartDateJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editPostingStartDateJDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editPostingJButton)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        postingOptionsJTabbedPane.addTab("Mettre à jour l'affectation", editPostingJPanel);

        deletePostingJLabel.setText("Supprimer l'affectation seletionnée :");

        deletePostingJButton.setText("Supprimer");

        generatePDFJLabel.setText("Génerer l'arrêté d'affectation :");

        generatePDFJButton.setText("Génerer en PDF");

        javax.swing.GroupLayout postingOptionsJPanelLayout = new javax.swing.GroupLayout(postingOptionsJPanel);
        postingOptionsJPanel.setLayout(postingOptionsJPanelLayout);
        postingOptionsJPanelLayout.setHorizontalGroup(
            postingOptionsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(postingOptionsJTabbedPane)
            .addComponent(jSeparator2)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(postingOptionsJPanelLayout.createSequentialGroup()
                .addGroup(postingOptionsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(postingOptionsJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(deletePostingJLabel))
                    .addGroup(postingOptionsJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(generatePDFJLabel))
                    .addGroup(postingOptionsJPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(deletePostingJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(postingOptionsJPanelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(generatePDFJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        postingOptionsJPanelLayout.setVerticalGroup(
            postingOptionsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postingOptionsJPanelLayout.createSequentialGroup()
                .addComponent(postingOptionsJTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deletePostingJLabel)
                .addGap(18, 18, 18)
                .addComponent(deletePostingJButton)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(generatePDFJLabel)
                .addGap(18, 18, 18)
                .addComponent(generatePDFJButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout postingListJPanelLayout = new javax.swing.GroupLayout(postingListJPanel);
        postingListJPanel.setLayout(postingListJPanelLayout);
        postingListJPanelLayout.setHorizontalGroup(
            postingListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addPostingFormJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(postingListJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postingListJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1152, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postingOptionsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        postingListJPanelLayout.setVerticalGroup(
            postingListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postingListJPanelLayout.createSequentialGroup()
                .addComponent(addPostingFormJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(postingListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(postingOptionsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(postingListJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        postingJTabbedPane.addTab("Liste des afféctations", postingListJPanel);

        postingEmployeeJLabel.setText("Selectionner l'employé :");

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

        postingEmpoyeeListJLabel.setText("Historique des affectations :");

        postingEmployeeListJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Employé", "De", "Affecté à", "Affecté le", "Prise de service le"
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
        jScrollPane2.setViewportView(postingEmployeeListJTable);
        if (postingEmployeeListJTable.getColumnModel().getColumnCount() > 0) {
            postingEmployeeListJTable.getColumnModel().getColumn(0).setMaxWidth(50);
            postingEmployeeListJTable.getColumnModel().getColumn(4).setPreferredWidth(150);
            postingEmployeeListJTable.getColumnModel().getColumn(4).setMaxWidth(150);
            postingEmployeeListJTable.getColumnModel().getColumn(5).setPreferredWidth(150);
            postingEmployeeListJTable.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        postingEmployeeScrollPane.add(jScrollPane2);

        postingEmployeeOptionsJPanel.setMaximumSize(new java.awt.Dimension(500, 32767));

        postingEmployeeGeneratePDFJLabel.setText("Génerer l'arrêté de l'affectation :");

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
                        .addGap(33, 33, 33)
                        .addComponent(postingEmployeeGeneratePDFJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        postingEmployeeOptionsJPanelLayout.setVerticalGroup(
            postingEmployeeOptionsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postingEmployeeOptionsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postingEmployeeGeneratePDFJLabel)
                .addGap(18, 18, 18)
                .addComponent(postingEmployeeGeneratePDFJButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout postingEmployeeListJPanelLayout = new javax.swing.GroupLayout(postingEmployeeListJPanel);
        postingEmployeeListJPanel.setLayout(postingEmployeeListJPanelLayout);
        postingEmployeeListJPanelLayout.setHorizontalGroup(
            postingEmployeeListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postingEmployeeListJPanelLayout.createSequentialGroup()
                .addGroup(postingEmployeeListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(postingEmployeeListJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(postingEmployeeJLabel))
                    .addGroup(postingEmployeeListJPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(postingEmployeeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(postingEmployeeListJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(postingEmployeeListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(postingEmpoyeeListJLabel)
                            .addGroup(postingEmployeeListJPanelLayout.createSequentialGroup()
                                .addComponent(postingEmployeeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(postingEmployeeOptionsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        postingEmployeeListJPanelLayout.setVerticalGroup(
            postingEmployeeListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(postingEmployeeListJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postingEmployeeJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postingEmployeeJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(postingEmpoyeeListJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(postingEmployeeListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(postingEmployeeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                    .addComponent(postingEmployeeOptionsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        postingJTabbedPane.addTab("Historique d'un employé", postingEmployeeListJPanel);

        unpostedListJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        unpostedListJScrollPane.setViewportView(unpostedListJTable);

        unpostedListJLabel.setText("Liste des employés sans affectations :");

        javax.swing.GroupLayout unpostedListPanelLayout = new javax.swing.GroupLayout(unpostedListPanel);
        unpostedListPanel.setLayout(unpostedListPanelLayout);
        unpostedListPanelLayout.setHorizontalGroup(
            unpostedListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(unpostedListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(unpostedListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(unpostedListJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1553, Short.MAX_VALUE)
                    .addGroup(unpostedListPanelLayout.createSequentialGroup()
                        .addComponent(unpostedListJLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        unpostedListPanelLayout.setVerticalGroup(
            unpostedListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, unpostedListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(unpostedListJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(unpostedListJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                .addContainerGap())
        );

        postingJTabbedPane.addTab("Employés non affectés", unpostedListPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(postingJTabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(postingJTabbedPane)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void postingEmployeeJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postingEmployeeJComboBoxActionPerformed
        // TODO add your handling code here:
        JComboBox<Employee> source = (JComboBox<Employee>) evt.getSource();
        Employee selectedEmployee = (Employee) source.getSelectedItem();
        int selectedEmployeeId = selectedEmployee.getId();
        this.setPostingEmployeeIdSelected(selectedEmployeeId);
        this.refillPostingEmployeeListJTable();
    }//GEN-LAST:event_postingEmployeeJComboBoxActionPerformed

    private void postingEmployeeGeneratePDFJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postingEmployeeGeneratePDFJButtonActionPerformed
        // TODO add your handling code here:
        // TODO generate PDF
        System.out.println("TODO create PDF for posting where employee_id = : " + this.getPostingEmployeeIdSelected() + " but we don't know which one.");
    }//GEN-LAST:event_postingEmployeeGeneratePDFJButtonActionPerformed

    private void postingListJTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_postingListJTableMouseClicked
        int selectedRow = postingListJTable.getSelectedRow();
        if (selectedRow != -1) {
            Integer selectedId = (Integer) postingListJTable.getValueAt(selectedRow, 0);
            this.setPostingSelected(Posting.getById(this.connection, selectedId));
            this.refillEditPostingForm();
        }
    }//GEN-LAST:event_postingListJTableMouseClicked

    private void editPostingEmployeeJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPostingEmployeeJComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editPostingEmployeeJComboBoxActionPerformed

    private void addPostingJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addPostingJButtonActionPerformed
        Posting.create(this.connection, 1, 1, new Date());
    }// GEN-LAST:event_addPostingJButtonActionPerformed

    private void searchPostingJButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchPostingJButtonActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_searchPostingJButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Employee> addPostingEmployeeJComboBox;
    private javax.swing.JLabel addPostingEmployeeJLabel;
    private javax.swing.JLabel addPostingFormJLabel;
    private javax.swing.JPanel addPostingFormJPanel;
    private javax.swing.JButton addPostingJButton;
    private javax.swing.JPanel addPostingJPanel;
    private javax.swing.JComboBox<Place> addPostingPlaceJComboBox;
    private javax.swing.JLabel addPostingPlaceJLabel;
    private com.toedter.calendar.JDateChooser addPostingStartDateJDateChooser;
    private javax.swing.JLabel addPostingStartDateJLabel;
    private javax.swing.JButton clearSeachPostingJButton;
    private javax.swing.JButton deletePostingJButton;
    private javax.swing.JLabel deletePostingJLabel;
    private javax.swing.JComboBox<Employee> editPostingEmployeeJComboBox;
    private javax.swing.JLabel editPostingEmployeeJLabel;
    private javax.swing.JLabel editPostingFormJLabel;
    private javax.swing.JButton editPostingJButton;
    private javax.swing.JPanel editPostingJPanel;
    private javax.swing.JComboBox<Place> editPostingPlaceJComboBox;
    private javax.swing.JLabel editPostingPlaceJLabel;
    private com.toedter.calendar.JDateChooser editPostingStartDateJDateChooser;
    private javax.swing.JLabel editPostingStartDateJLabel;
    private com.toedter.calendar.JDateChooser endDateJDateChooser;
    private javax.swing.JLabel endDateJLabel;
    private javax.swing.JButton generatePDFJButton;
    private javax.swing.JLabel generatePDFJLabel;
    private com.toedter.calendar.JDateChooserCellEditor jDateChooserCellEditor1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton postingEmployeeGeneratePDFJButton;
    private javax.swing.JLabel postingEmployeeGeneratePDFJLabel;
    private javax.swing.JComboBox<Employee> postingEmployeeJComboBox;
    private javax.swing.JLabel postingEmployeeJLabel;
    private javax.swing.JPanel postingEmployeeListJPanel;
    private javax.swing.JTable postingEmployeeListJTable;
    private javax.swing.JPanel postingEmployeeOptionsJPanel;
    private java.awt.ScrollPane postingEmployeeScrollPane;
    private javax.swing.JLabel postingEmpoyeeListJLabel;
    private javax.swing.JTabbedPane postingJTabbedPane;
    private javax.swing.JLabel postingListJLabel;
    private javax.swing.JPanel postingListJPanel;
    private javax.swing.JScrollPane postingListJScrollPane;
    private javax.swing.JTable postingListJTable;
    private javax.swing.JPanel postingOptionsJPanel;
    private javax.swing.JTabbedPane postingOptionsJTabbedPane;
    private javax.swing.JButton searchPostingJButton;
    private javax.swing.JLabel searchPostingJLabel;
    private com.toedter.calendar.JDateChooser startDateJDateChooser;
    private javax.swing.JLabel startDateJLabel;
    private javax.swing.JLabel unpostedListJLabel;
    private javax.swing.JScrollPane unpostedListJScrollPane;
    private javax.swing.JTable unpostedListJTable;
    private javax.swing.JPanel unpostedListPanel;
    // End of variables declaration//GEN-END:variables
}
