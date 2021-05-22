package Course.Management.System;
import Helpers.DBUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class couseForm implements CreateTable, ClearField {
    private JPanel couMainPannel;
    private JPanel addPanel;
    private JPanel insPanel;
    private JTextField courseIdField;
    private JTextField modIdField;
    private JButton addCourseButton;
    private JRadioButton sem1Radio;
    private JRadioButton sem2Radio;
    private JButton addInsButton;
    JPanel mainPanelCourse;
    private JTextField insIdField;
    private JTextField insNameField;
    private JTextField insLNameField;
    private JTextField courseNameField;
    private JTextField modNameField;
    private JComboBox levBox;
    private JButton modButton;
    private JCheckBox activeCheckBox;
    private JCheckBox canceledCheckBox;
    public JComboBox<String> courseBox;
    private JComboBox<String> insBox;
    private JTable modTable;
    private JPanel modulePanel;
    private JButton ModDelete;
    private JTable couTable;
    private JButton couDelete;
    private JButton insDelete;
    private JTable insTable;
    private JButton couEdit;
    private JButton insEdit;
    private JButton modEdit;
    private JCheckBox optBox;
    private JCheckBox compBox;
    public JTextField searchField;
    private JButton generateButton;
    private JPanel report;
    private JPanel generatePanel;
    private JPanel modPanel;
    private JButton addModButton;
    private JComboBox courseCombo;

    private String stat = " ";
    private String levels = " ";
    private String sem = " ";
    private String opt = " ";
    private String opts;
    private String stats;
    private String sems;
    private String StdId;
    private String sql;
    private int j;

    ///////////////////////////////////////////////////////////////////////////////////Database Connectors///////////////////////////////////////////////////////////////////////////
    private Connection con;
    private PreparedStatement insert;

    /////////////////////////////////////////////////////////////////////////////////////////ADD DATA//////////////////////////////////////////////////////////////////////////////////
    private void addCourseData(String id, String Name, String stat) {
        try {
            con = DBUtils.getDbConnection();
            insert = con.prepareStatement("insert into courses(Course_id, Name, Status)values(?,?,?)");
            insert.setString(1, id);
            insert.setString(2, Name);
            insert.setString(3, stat);
            insert.executeUpdate();
            JOptionPane.showMessageDialog(null,"Course Added");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Same ID detected or to edit, press edit Button");
        }
    }

    private void addModuleData(String id, String Name, String sem, String opt) {
        try {
            con = DBUtils.getDbConnection();
            insert = con.prepareStatement("insert into modules(Mod_id, Name, Course_id, Level, Semester, Ins_id, State)values(?,?,?,?,?,?,?)");
            insert.setString(1, id);
            insert.setString(2, Name);
            String course = Objects.requireNonNull(courseBox.getSelectedItem()).toString();
            insert.setString(3, course);
            String level = Objects.requireNonNull(levBox.getSelectedItem()).toString();
            insert.setString(4, level);
            insert.setString(5, sem);
            JOptionPane.showMessageDialog(null,"Module Added");
            try{
                String instructor = Objects.requireNonNull(insBox.getSelectedItem()).toString();
                insert.setString(6, instructor);
            }
            catch (java.lang.NullPointerException a){
                JOptionPane.showMessageDialog(null, "Instructor is empty");
            }
            insert.setString(7, opt);
            try{
                insert.executeUpdate();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Duplicate Id detected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addInsData(String id, String Name) {
        try {
            con = DBUtils.getDbConnection();
            insert = con.prepareStatement("insert into instructor(Ins_id, FullName)values(?,?)");
            insert.setString(1, id);
            insert.setString(2, Name);
            insert.executeUpdate();
            JOptionPane.showMessageDialog(null,"Instructor Added");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Same ID detected or to edit, press edit Button");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////MOUSE CLICK//////////////////////////////////////////////////////////////////////////////////////
    private void couMouse() {
        j = couTable.getSelectedRow();
        TableModel cou = couTable.getModel();
        courseIdField.setText(cou.getValueAt(j, 0).toString());
        courseNameField.setText(cou.getValueAt(j, 1).toString());
        String stat = cou.getValueAt(j, 2).toString();
        switch (stat) {
            case "Active":
                activeCheckBox.setSelected(true);
                canceledCheckBox.setSelected(false);
                stats = "Active";
                break;
            case "Canceled":
                canceledCheckBox.setSelected(true);
                activeCheckBox.setSelected(false);
                stats = "Canceled";
                break;
        }
    }

    private void insMouse() {
        j = insTable.getSelectedRow();
        TableModel ins = insTable.getModel();
        insIdField.setText(ins.getValueAt(j, 0).toString());
        insNameField.setText(ins.getValueAt(j, 1).toString());
    }

    private void modMouse() {
        j = modTable.getSelectedRow();
        TableModel mod = modTable.getModel();
        modIdField.setText(mod.getValueAt(j, 0).toString());
        modNameField.setText(mod.getValueAt(j, 1).toString());
        courseBox.setSelectedItem(mod.getValueAt(j, 2));
        levBox.setSelectedItem(mod.getValueAt(j, 3));
        String sem = mod.getValueAt(j, 4).toString();
        if (sem.equals("Semester 1")) {
            sem1Radio.setSelected(true);
            sem2Radio.setSelected(false);
            sems = "Semester 1";
        } else {
            sem2Radio.setSelected(true);
            sem1Radio.setSelected(false);
            sems = "Semester 2";
        }
        insBox.setSelectedItem(mod.getValueAt(j, 5));
        String opt = mod.getValueAt(j, 6).toString();
        if (opt.equals("Optional")) {
            optBox.setSelected(true);
            compBox.setSelected(false);
            opts = "Optional";
        } else {
            compBox.setSelected(true);
            optBox.setSelected(false);
            opts = "Compulsory";
        }
    }
/////////////////////////////////////////////////////////////////////////////////////Clearing Fields//////////////////////////////////////////////////////////////////////////////////
    public void clearField() {
        courseIdField.setText("");
        courseNameField.setText("");
        activeCheckBox.setSelected(true);
        canceledCheckBox.setSelected(false);
        insIdField.setText("");
        insNameField.setText("");
        modIdField.setText("");
        modNameField.setText("");
        optBox.setSelected(false);
        sem1Radio.setSelected(false);
        sem2Radio.setSelected(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////EMPTY WARNINGS/////////////////////////////////////////////////////////////////////////////////
    private boolean emptyCouWarning() {
        boolean result = true;
        if (courseIdField.getText().length() <= 0 || courseNameField.getText().length() <= 0) {
            JOptionPane.showMessageDialog(null, "Please Fill up the form");
            result = false;
        }
        return result;
    }

    private boolean emptyInsWarning() {
        boolean result = true;
        if (insIdField.getText().length() <= 0 || insNameField.getText().length() <= 0) {
            JOptionPane.showMessageDialog(null, "Please Fill up the form");
            result = false;
        }
        return result;
    }

    private boolean emptyModWarning() {
        boolean result = true;
        if ((modIdField.getText().length() <= 0 || modNameField.getText().length() <= 0) || (!compBox.isSelected() && !optBox.isSelected()) || (!sem1Radio.isSelected() && !sem2Radio.isSelected())) {
            JOptionPane.showMessageDialog(null, "Please Fill up the form");
            result = false;
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////////////////FOR COMBO///////////////////////////////////////////////////////////////////////////////////////
    private void forCourseCombobox() throws SQLException {
        con = DBUtils.getDbConnection();
        Statement st = con.createStatement();
        ResultSet ex = st.executeQuery("SELECT Course_id From courses");
        while (ex.next()) {
            String couId = ex.getString("Course_id");
            courseBox.addItem(couId);
        }
    }

    private void forInsCombobox() throws SQLException {
        con = DBUtils.getDbConnection();
        Statement st = con.createStatement();
        ResultSet ex = st.executeQuery("SELECT Ins_id From instructor");
        while (ex.next()) {
            String insId = ex.getString("Ins_id");
            insBox.addItem(insId);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////CREATE TABLE////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void createTable() {
        modTable.setModel(new DefaultTableModel(
                null, new String[]{"Module ID", "Module Name", "Course ID", "Level", "Semester", "Instructor ID", "State"}
        ));
        couTable.setModel(new DefaultTableModel(
                null, new String[]{"Course ID", "Name", "Status"}
        ));
        insTable.setModel(new DefaultTableModel(
                null, new String[]{"Instructor ID", "Full name"}
        ));
    }

    ////////////////////////////////////////////////////////////////////////////////EXTRACT DATA///////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<extract> extractData() {
        ArrayList<extract> extractsData = new ArrayList<>();
        try {
            con = DBUtils.getDbConnection();
            Statement st = con.createStatement();
            ResultSet x = st.executeQuery("SELECT * From modules");
            extract extract;
            while (x.next()) {
                extract = new extract(x.getString("Mod_id"), x.getString("Name"), x.getString("Course_id"), x.getString("Level"), x.getString("Semester"), x.getString("Ins_id"), x.getString("State"));
                extractsData.add(extract);
            }
            x = st.executeQuery("SELECT * From courses");
            while (x.next()) {
                extract = new extract(x.getString("Course_id"), x.getString("Name"), x.getString("Status"));
                extractsData.add(extract);
            }
            x = st.executeQuery("SELECT * From instructor");
            while (x.next()) {
                extract = new extract(x.getString("Ins_id"), x.getString("FullName"));
                extractsData.add(extract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extractsData;
    }

    ////////////////////////////////////////////////////////////////////////////////EDIT DATA////////////////////////////////////////////////////////////////////////////////////////////

    private void editCourseData() throws SQLException {
        con = DBUtils.getDbConnection();
        insert = con.prepareStatement("Update courses set Name='" + courseNameField.getText() + "',Status='" + stats + "' where Course_id='" + courseIdField.getText() + "' ");
        insert.executeUpdate();

        JOptionPane.showMessageDialog(null, "Edited");
    }

    private void editInsData() throws SQLException {
        con = DBUtils.getDbConnection();
        insert = con.prepareStatement("Update instructor set FullName='" + insNameField.getText() + "' where Ins_id='" + insIdField.getText() + "' ");
        insert.executeUpdate();

        JOptionPane.showMessageDialog(null, "Edited");
    }

    private void editModData() throws SQLException {
        con = DBUtils.getDbConnection();
        insert = con.prepareStatement("Update modules set Name='" + modNameField.getText() + "',level='" + levBox.getSelectedItem() + "',Semester='" + sems + "',Ins_id='" + insBox.getSelectedItem() + "',State='" + opts + "' where Mod_id='" + modIdField.getText() + "' ");
        insert.executeUpdate();

        JOptionPane.showMessageDialog(null, "Edited");
    }

    /////////////////////////////////////////////////////////////////////////////DELETE DATA///////////////////////////////////////////////////////////////////////////////////////////

    private void delCouData() throws SQLException {
        int ask = JOptionPane.showConfirmDialog(null, "Modules and student data associated with this Course will also be deleted", "Delete", JOptionPane.YES_NO_OPTION);
        if (ask == 0) {
            con = DBUtils.getDbConnection();
            insert = con.prepareStatement("DELETE from courses where Course_id='" + courseIdField.getText() + "' ");
            insert.executeUpdate();
        }
    }

    private void delInsData() throws SQLException {
        int ask = JOptionPane.showConfirmDialog(null, "Modules related to this instructor will not be deleted, However instructor cannot assign marks to their respective modules", "Delete", JOptionPane.YES_NO_OPTION);
        if (ask == 0) {
            con = DBUtils.getDbConnection();
            insert = con.prepareStatement("DELETE from instructor where Ins_id='" + insIdField.getText() + "' ");
            insert.executeUpdate();
        }

    }

    private void delModData() throws SQLException {
        int ask = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this module?", "Delete", JOptionPane.YES_NO_OPTION);
        if (ask == 0) {
            con = DBUtils.getDbConnection();
            insert = con.prepareStatement("DELETE from modules where Mod_id='" + modIdField.getText() + "' ");
            insert.executeUpdate();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////SHOW DATA////////////////////////////////////////////////////////////////////////////////////////
    public void showModData() {
        ArrayList<extract> list = extractData();
        DefaultTableModel model = (DefaultTableModel) modTable.getModel();
        Object[] row = new Object[7];
        for (Course.Management.System.extract extract : list) {
            if (extract.getStatus() == null && extract.getFullName() == null) {
                row[0] = extract.getMod_id();
                row[1] = extract.getname();
                row[2] = extract.getcourse_id();
                row[3] = extract.getLevel();
                row[4] = extract.getSemester();
                row[5] = extract.getins_id();
                row[6] = extract.getState();
                model.addRow(row);
            }
        }
    }

    public void showCouData() {
        ArrayList<extract> list = extractData();
        DefaultTableModel model = (DefaultTableModel) couTable.getModel();
        Object[] row = new Object[3];
        for (Course.Management.System.extract extract : list) {
            if (extract.getMod_id() == null && extract.getFullName() == null) {
                row[0] = extract.getCourse_id();
                row[1] = extract.getName();
                row[2] = extract.getStatus();
                model.addRow(row);
            }
        }
    }

    public void showInsData() {
        ArrayList<extract> list = extractData();
        DefaultTableModel model = (DefaultTableModel) insTable.getModel();
        Object[] row = new Object[2];
        for (Course.Management.System.extract extract : list) {
            if (extract.getMod_id() == null && extract.getStatus() == null) {
                row[0] = extract.getIns_id();
                row[1] = extract.getFullName();
                model.addRow(row);
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////Generate Report card///////////////////////////////////////////////////////////////////////////////////
    private void generate() throws SQLException {
        con = DBUtils.getDbConnection();
        sql = "Select * from std_mod where Student_id='" + searchField.getText() + "'";
        insert = con.prepareStatement(sql);
        ResultSet ex = insert.executeQuery(sql);
        StdId = null;
        String marks = null;
        if (ex != null) {
            while (ex.next()) {
                StdId = ex.getString("Student_id");
                marks = ex.getString("Marks");
            }
        }
        assert marks != null;
        if (StdId != null && !marks.equals("")) {
            JFrame frame3 = new JFrame("Course");
            frame3.setContentPane(new Report(StdId).mainPanel);
            frame3.setMinimumSize(new Dimension(800, 550));
            frame3.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));
            frame3.setLocationRelativeTo(null);
            frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame3.pack();
            frame3.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Student ID does not exist or marks has not been assigned by Instructor");
        }
    }

    public couseForm() throws SQLException {
        createTable();
        showCouData();
        showModData();
        showInsData();
        extractData();
        forCourseCombobox();
        forInsCombobox();
        optBox.setVisible(false);
//////////////////////////////////////////////////////////////////////////////////////Action Listeners//////////////////////////////////////////////////////////////////////////////////
//---------------------------------------------------------------------------------Button to add Course-------------------------------------------------------------------------------//
        addCourseButton.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) couTable.getModel();
            t1.setRowCount(0);
            if (emptyCouWarning()) {
                courseBox.removeAllItems();
                addCourseData(courseIdField.getText(), courseNameField.getText(), stat);
                try {
                    forCourseCombobox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            showCouData();
            clearField();
        });
//---------------------------------------------------------------------------------Button to add Instructor-------------------------------------------------------------------------------//
        addInsButton.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) insTable.getModel();
            t1.setRowCount(0);
            if (emptyInsWarning()) {
                insBox.removeAllItems();
                addInsData(insIdField.getText(), insNameField.getText());
                try {
                    forInsCombobox();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            showInsData();
            clearField();
        });
//---------------------------------------------------------------------------------Button to add Modules-------------------------------------------------------------------------------//
        modButton.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) modTable.getModel();
            t1.setRowCount(0);
            if (emptyModWarning()) {
                addModuleData(modIdField.getText(), modNameField.getText(), sem, opt);
            }
            showModData();
            clearField();
        });
//--------------------------------------------------------------------------------------Check Box---------------------------------------------------------------------------------//
        activeCheckBox.addActionListener(actionEvent -> {
            if (activeCheckBox.isSelected()) {
                activeCheckBox.setSelected(true);
                canceledCheckBox.setSelected(false);
                stat = "Active";
                stats = "Active";
            }
        });
        canceledCheckBox.addActionListener(actionEvent -> {
            if (canceledCheckBox.isSelected()) {
                canceledCheckBox.setSelected(true);
                activeCheckBox.setSelected(false);
                stat = "Canceled";
                stats = "Canceled";
            }
        });
        optBox.addActionListener(actionEvent -> {
            if (optBox.isSelected()) {
                compBox.setSelected(false);
                opt = "Optional";
                opts = "Optional";
            }
        });
        compBox.setSelected(true);
        opt = "Compulsory";
        opts = "Compulsory";
        compBox.addActionListener(actionEvent -> {
            if (compBox.isSelected()) {
                optBox.setSelected(false);
                opt = "Compulsory";
                opts = "Compulsory";
            }
        });
//---------------------------------------------------------------------------------------Radio Button---------------------------------------------------------------------------------//
        sem1Radio.addActionListener(actionEvent -> {
            if (sem1Radio.isSelected()) {
                sem2Radio.setSelected(false);
                sem = "Semester 1";
                sems = "Semester 1";
            }
        });
        sem2Radio.addActionListener(actionEvent -> {
            if (sem2Radio.isSelected()) {
                sem1Radio.setSelected(false);
                sem = "Semester 2";
                sems = "Semester 2";
            }
        });
//---------------------------------------------------------------------------------------MouseListeners---------------------------------------------------------------------------------//
        couTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                couMouse();
            }
        });
        insTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insMouse();
            }
        });
        modTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                modMouse();
            }
        });
//--------------------------------------------------------------------------------------Course Edit Button---------------------------------------------------------------------------------//
        couEdit.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) couTable.getModel();
            t1.setRowCount(0);
            try {
                editCourseData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            showCouData();
            clearField();
        });
//--------------------------------------------------------------------------------------Instructor Edit Button---------------------------------------------------------------------------------//
        insEdit.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) insTable.getModel();
            t1.setRowCount(0);
            try {
                editInsData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            showInsData();
            clearField();
        });
//---------------------------------------------------------------------------------------Module Edit Button---------------------------------------------------------------------------------//
        modEdit.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) modTable.getModel();
            t1.setRowCount(0);
            try {
                editModData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            showModData();
            clearField();
        });
//--------------------------------------------------------------------------------------Course Delete Button---------------------------------------------------------------------------------//
        couDelete.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) couTable.getModel();
            DefaultTableModel t2 = (DefaultTableModel) modTable.getModel();
            t2.setRowCount(0);
            t1.setRowCount(0);

            try {
                delCouData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            courseBox.removeAllItems();
            try {
                forCourseCombobox();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            showModData();
            showCouData();
            clearField();
        });
//--------------------------------------------------------------------------------------Instructor Delete Button---------------------------------------------------------------------------------//
        insDelete.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) insTable.getModel();
            DefaultTableModel t2 = (DefaultTableModel) modTable.getModel();
            t2.setRowCount(0);
            t1.setRowCount(0);
            try {
                delInsData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            insBox.removeAllItems();
            try {
                forInsCombobox();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            showInsData();
            showModData();
            clearField();
        });
//-----------------------------------------------------------------------------------Module Delete Button------------------------------------------------------------------------------------//
        ModDelete.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) modTable.getModel();
            t1.setRowCount(0);
            try {
                delModData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            showModData();
            clearField();
        });
//------------------------------------------------------------------------------------ComboBox Action listener---------------------------------------------------------------------------------//
        levBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.levels = Objects.requireNonNull(levBox.getSelectedItem()).toString();
                if (levels.equals("6")) {
                    optBox.setVisible(true);
                } else {
                    optBox.setVisible(false);
                }
            }
        });
//--------------------------------------------------------------------------------------Result Generate Button ---------------------------------------------------------------------------------//
        generateButton.addActionListener(actionEvent -> {
            try {
                generate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
