package Course.Management.System;

import Helpers.DBUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Student implements CreateTable, ClearField {
    private JButton cancelButton;
    JPanel mainPanel;
    private JPanel idPanel;
    private JTextField idField;
    private JPanel modPanel;
    private JRadioButton mod1;
    private JRadioButton mod2;
    private JPanel surNamePanel;
    private JTextField surNameField;
    private JPanel buttonPanel;
    private JButton doneButton;
    private JButton registerButton1;
    private JPanel Header;
    private JTable showTable;
    private JTextField nameField;
    private JPanel levelField;
    private JPanel JTable;
    private JTextField emailField;
    private JComboBox<String> courseBox;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private JRadioButton otherButton;
    private JComboBox levelBox;
    private JPanel genderPanel;
    private JTable compTable;
    private JTextField addressField;
    private JPanel optPanel;
    private JPanel compPanel;
    private JComboBox<String> optSem1Combo;
    private JComboBox<String> optSem2Combo;
    private JPanel selectPanel;

    private String gender = " ";
    private String courses = " ";
    private String levels = " ";
    public String optMod1;
    public String optMod2;
    private String comp = "Compulsory";
    private String opt = "Optional";
    private String sem1 = "Semester 1";
    private String sem2 = "Semester 2";
    private String marks = "";
    private String result = " ";
    private String modId1;
    private String modId2;
    private String modName1;
    private String modName2;
    private String insId1;
    private String insId2;
    public String ins1;
    public String ins2;
    private String module1;
    public String modN1;
    public String modN2;
    private String module2;
    ArrayList<String> modid = new ArrayList<>();
    ArrayList<String> modName = new ArrayList<>();
    ArrayList<String> intid = new ArrayList<>();
    private String level;
    ///////////////////////////////////////////////////////////////////////////////////Database Connectors///////////////////////////////////////////////////////////////////////////
    private Connection con;
    private PreparedStatement insert;
    ///////////////////////////////////////////////////////////////////////////////////Adding Student Data///////////////////////////////////////////////////////////////////////////
    private void addData(String id, String FirstName, String LastName, String gender, String Address, String email){
        try {
            con = DBUtils.getDbConnection();
            insert = con.prepareStatement("insert into student(ID,FirstName,LastName,Gender,Address,Email,Course,level)values(?,?,?,?,?,?,?,?);");
            insert.setString(1, id);
            insert.setString(2, FirstName);
            insert.setString(3, LastName);
            insert.setString(4, gender);
            insert.setString(5, Address);
            insert.setString(6, email);
            String course = Objects.requireNonNull(courseBox.getSelectedItem()).toString();
            insert.setString(7, course);
            level = Objects.requireNonNull(levelBox.getSelectedItem()).toString();
            insert.setString(8, level);
            insert.executeUpdate();
            JOptionPane.showMessageDialog(null, "Added");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Course Administrator has not assigned any courses");
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////Extracting data from Database///////////////////////////////////////////////////////////////////////////
    public ArrayList<extracter> extractData() {
        ArrayList<extracter> extractsData = new ArrayList<>();
        try {
            con = DBUtils.getDbConnection();
            Statement st = con.createStatement();
            ResultSet ex = st.executeQuery("SELECT * From student");
            extracter extracts;
            while (ex.next()) {
                extracts = new extracter(ex.getString("ID"), ex.getString("FirstName"), ex.getString("LastName"), ex.getString("Gender"), ex.getString("Address"), ex.getString("email"), ex.getString("course"), ex.getString("level"));
                extractsData.add(extracts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extractsData;
    }
    //////////////////////////////////////////////////////////////////////////////////Using getters to show on table///////////////////////////////////////////////////////////////////////////
    public void showStData() {
        ArrayList<extracter> list = extractData();
        DefaultTableModel model = (DefaultTableModel) showTable.getModel();
        Object[] row = new Object[8];
        for (Course.Management.System.extracter extracter : list) {
            row[0] = extracter.getId();
            row[1] = extracter.getFN();
            row[2] = extracter.getLN();
            row[3] = extracter.getGen();
            row[4] = extracter.getAddress();
            row[5] = extracter.getEmail();
            row[6] = extracter.getCourse();
            row[7] = extracter.getlevel();
            model.addRow(row);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////Displaying modules/////////////////////////////////////////////////////////////////////////////
    public void showModData() throws SQLException {
        con = DBUtils.getDbConnection();
        String sql = "Select * from modules where Course_id='" + courses + "'AND level='" + levels + "'AND State='" + comp + "'";
        insert = con.prepareStatement(sql);
        ResultSet rs = insert.executeQuery(sql);

        if (rs != null) {
            while (rs.next()) {
                modid.add(rs.getString("Mod_id"));
                modName.add(rs.getString("Name"));
                intid.add(rs.getString("Ins_id"));
                String modId = rs.getString("Mod_id");
                String modName = rs.getString("Name");
                String semester = rs.getString("Semester");
                String instructor = rs.getString("Ins_id");
                String[] tbData = {modId, modName, semester, instructor};
                DefaultTableModel t1 = (DefaultTableModel) compTable.getModel();
                t1.addRow(tbData);
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////Adding data to std_mod DataBase///////////////////////////////////////////////////////////////////////////
    private void addStdMod() throws SQLException {
        con = DBUtils.getDbConnection();
        insert = con.prepareStatement("insert into std_mod(Student_id, Student_Name, Mod_id, Mod_name, Ins_id, Marks, Result)values(?,?,?,?,?,?,?)");
        for (int i = 0; i < modid.size(); i++) {
            insert.setString(1, idField.getText());
            insert.setString(2, nameField.getText() + " " + surNameField.getText());
            insert.setString(3, modid.get(i));
            insert.setString(4, modName.get(i));
            insert.setString(5, intid.get(i));
            insert.setString(6, marks);
            insert.setString(7, result);
            insert.executeUpdate();
        }
    }
    //////////////////////////////////////////////////////////////////////////////////Clearing Text Fields///////////////////////////////////////////////////////////////////////////
    public void clearField() {
        idField.setText("");
        nameField.setText("");
        surNameField.setText("");
        emailField.setText("");
        addressField.setText("");
    }
    //////////////////////////////////////////////////////////////////////////////////Empty text field Warning///////////////////////////////////////////////////////////////////////////
    private boolean emptyWarning() {
        boolean result = true;
        if (idField.getText().length() <= 0 || nameField.getText().length() <= 0 || surNameField.getText().length() <= 0 || addressField.getText().length() <= 0) {
            JOptionPane.showMessageDialog(null, "Please Fill up the form");
            result = false;
        }
        return result;
    }
    ///////////////////////////////////////////////////////////////////////////////////Creating Table///////////////////////////////////////////////////////////////////////////
    @Override
    public void createTable() {
        showTable.setModel(new DefaultTableModel(
                null, new String[]{"Id", "FirstName", "LastName", "gender", "Address", "email", "course", "level"}
        ));
        compTable.setModel(new DefaultTableModel(
                null, new String[]{"Module ID", "Module Name", "Semester", "Instructor_id"}
        ));
    }
    ///////////////////////////////////////////////////////////////////////////////////Adding course Id in comboBox///////////////////////////////////////////////////////////////////////////
    private void forCombobox() throws SQLException {
        con = DBUtils.getDbConnection();
        Statement st = con.createStatement();
        ResultSet ex = st.executeQuery("SELECT Course_id From courses");
        while (ex.next()){
            String couId = ex.getString("Course_id");
            courseBox.addItem(couId);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////Adding optional Subjects///////////////////////////////////////////////////////////////////////////
    private void optLev6Sem1() throws SQLException {
        con = DBUtils.getDbConnection();
        String sql = "Select * from modules where Course_id='" + courses + "'AND level='" + levels + "'AND Semester='" + sem1 + "'AND State='" + opt + "'";
        insert = con.prepareStatement(sql);
        ResultSet rs = insert.executeQuery(sql);

        if (rs != null) {
            while (rs.next()) {
                String modName = rs.getString("Mod_id") + " " + rs.getString("Name");
                optSem1Combo.addItem(modName);
                modId1 = rs.getString("Mod_id");
                modName1 = rs.getString("Name");
                insId1 = rs.getString("Ins_id");
            }
        }
    }

    private void optLev6Sem2() throws SQLException {
        con = DBUtils.getDbConnection();
        String sql = "Select * from modules where Course_id='" + courses + "'AND level='" + levels + "'AND Semester='" + sem2 + "'AND State='" + opt + "'";
        insert = con.prepareStatement(sql);
        ResultSet rs = insert.executeQuery(sql);

        if (rs != null) {
            while (rs.next()) {
                String modName = rs.getString("Mod_id") + " " + rs.getString("Name");
                optSem2Combo.addItem(modName);
                modId2 = rs.getString("Mod_id");
                modName2 = rs.getString("Name");
                insId2 = rs.getString("Ins_id");
            }
        }
    }
    public Student() throws SQLException {
        createTable();
        showStData();
        extractData();
        forCombobox();
        compPanel.setVisible(false);
        optPanel.setVisible(false);
///////////////////////////////////////////////////////////////////////////////////Action Listeners////////////////////////////////////////////////////////////////////////////
        cancelButton.addActionListener(actionEvent -> System.exit(0));
        registerButton1.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) showTable.getModel();
            t1.setRowCount(0);
            module2 = modId2;
            modN1 = modName1;
            modN2 = modName2;
            ins2 = insId2;
            modid.add(module2);
            intid.add(insId2);
            modName.add(modName1);
            module1 = modId1;
            ins1 = insId1;
            modid.add(module1);
            intid.add(insId1);
            modName.add(modName2);
            if (emptyWarning()) {
                try {
                    if(!levels.equals("Select a Level")){
                        addData(idField.getText(), nameField.getText(), surNameField.getText(), gender, addressField.getText(), emailField.getText());
                        addStdMod();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Select a level");
                    }

                } catch (SQLException e) {
                   JOptionPane.showMessageDialog(null, "Please select a module");
                }
            }
            showStData();
            clearField();
        });
        maleButton.addActionListener(actionEvent -> {
            if (maleButton.isSelected()) {
                femaleButton.setSelected(false);
                otherButton.setSelected(false);
                gender = "Male";
            }
        });
        femaleButton.addActionListener(actionEvent -> {
            if (femaleButton.isSelected()) {
                maleButton.setSelected(false);
                otherButton.setSelected(false);
                gender = "Female";
            }
        });
        otherButton.addActionListener(actionEvent -> {
            if (otherButton.isSelected()) {
                femaleButton.setSelected(false);
                maleButton.setSelected(false);
                gender = "Other";
            }
        });
        courseBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                optSem1Combo.removeAllItems();
                optSem2Combo.removeAllItems();
                optSem1Combo.addItem("Select a module");
                optSem2Combo.addItem("Select a module");
                modid.clear();
                modName.clear();
                intid.clear();
                compPanel.setVisible(true);
                DefaultTableModel t1 = (DefaultTableModel) compTable.getModel();
                t1.setRowCount(0);
                this.courses = Objects.requireNonNull(courseBox.getSelectedItem()).toString();
                try {
                    showModData();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        levelBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                compPanel.setVisible(true);
                optSem1Combo.removeAllItems();
                optSem2Combo.removeAllItems();
                optSem1Combo.addItem("Select a module");
                optSem2Combo.addItem("Select a module");
                modid.clear();
                modName.clear();
                intid.clear();
                module1 = null;
                modN1= null;
                ins1 = null;
                modName2 = null;
                modN2 = null;
                ins2 = null;
                DefaultTableModel t1 = (DefaultTableModel) compTable.getModel();
                t1.setRowCount(0);
                this.levels = Objects.requireNonNull(levelBox.getSelectedItem()).toString();
                try {
                    showModData();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if (levels.equals("6")) {
                    optPanel.setVisible(true);
                    try {
                        optLev6Sem1();
                        optLev6Sem2();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    optPanel.setVisible(false);
                }
            }
        });
        optSem1Combo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.optMod1 = Objects.requireNonNull(optSem1Combo.getSelectedItem()).toString();
            }
        });
        optSem2Combo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.optMod2 = Objects.requireNonNull(optSem2Combo.getSelectedItem()).toString();
            }
        });
        doneButton.addActionListener(actionEvent -> System.exit(0));
    }
}


