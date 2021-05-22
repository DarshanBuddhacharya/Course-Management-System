package Course.Management.System;

import Helpers.DBUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class insForm implements ClearField, CreateTable {
    JPanel mainPanel;
    private JPanel Header;
    private JButton enrollButton;
    private JButton cancelButton;
    private JTextField insIdfield;
    private JTextField insFNField;
    private JTable markTable;
    private JTextField stdId;
    private JTextField modId;
    private JTextField markField;
    private JButton subButton;
    private JPanel marksPanel;
    private JLabel markStdId;
    private JLabel markModId;

    private String res;
    private String ret;

    private int j;
    ///////////////////////////////////////////////////////////////////////////////////Database Connectors///////////////////////////////////////////////////////////////////////////
    private Connection con;
    private PreparedStatement insert;

    /////////////////////////////////////////////////////////////////////////////////////CREATE TABLE////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void createTable() {
        markTable.setModel(new DefaultTableModel(
                null, new String[]{"Student ID", "Module ID", "Instructor ID", "Marks", "Result"}
        ));
    }

    ////////////////////////////////////////////////////////////////////////////////////Show Marks Table/////////////////////////////////////////////////////////////////////////////
    public void showMod() throws SQLException {
        con = DBUtils.getDbConnection();
        String sql = "Select * from std_mod where Ins_id='" + insIdfield.getText() + "'";
        insert = con.prepareStatement(sql);
        ResultSet rs = insert.executeQuery(sql);

        if (rs != null) {
            while (rs.next()) {
                String stdId = rs.getString("Student_id");
                String modId = rs.getString("Mod_id");
                String insID = rs.getString("Ins_id");
                String mark = rs.getString("Marks");
                String Result = rs.getString("Result");
                String[] tbData = {stdId, modId, insID, mark, Result};
                DefaultTableModel t1 = (DefaultTableModel) markTable.getModel();
                t1.addRow(tbData);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////Mouse Listeners////////////////////////////////////////////////////////////////////////////
    private void markMouse() {
        j = markTable.getSelectedRow();
        TableModel mark = markTable.getModel();
        markStdId.setText(mark.getValueAt(j, 0).toString());
        markModId.setText(mark.getValueAt(j, 1).toString());
        markField.setText(mark.getValueAt(j, 3).toString());
    }

    ///////////////////////////////////////////////////////////////////////////////To Determine Pass or fail/////////////////////////////////////////////////////////////////////
    private void result(){
        if(!markField.getText().equals("")){
            if(Integer.parseInt(markField.getText())<40){
                res = "Fail";
            }
            else{
                res = "Pass";
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////Add Marks in table/////////////////////////////////////////////////////////////////////////////
    private void addMark() throws SQLException {
        con = DBUtils.getDbConnection();
        insert = con.prepareStatement("Update std_mod set Marks='" +markField.getText() + "',Result='" + res + "' where Mod_id='" + markModId.getText() + "'and Student_id='" + markStdId.getText() + "' ");
        insert.executeUpdate();
        JOptionPane.showMessageDialog(null, "Marks Added");
    }
    ////////////////////////////////////////////////////////////////////////////////Clear text Field///////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void clearField() {
        markField.setText("");
    }
    ////////////////////////////////////////////////////////////////////////////////Empty warnings////////////////////////////////////////////////////////////////////////////////////
   private boolean emptyWarning() {
        boolean result = true;
        if (insIdfield.getText().length() <= 0) {
            JOptionPane.showMessageDialog(null, "Please enter your Instructor ID");
            result = false;
        }
        return result;
    }
    public insForm() {
        createTable();
        marksPanel.setVisible(false);
    ///////////////////////////////////////////////////////////////////////////////Action Listeners///////////////////////////////////////////////////////////////////////////////////
    //---------------------------------------------------------------------------Instructor ID Button----------------------------------------------------------------------------//
        enrollButton.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) markTable.getModel();
            t1.setRowCount(0);
            if(emptyWarning()){
                marksPanel.setVisible(true);
                try {
                    showMod();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        //---------------------------------------------------------------------------Cancel Button----------------------------------------------------------------------------//
        cancelButton.addActionListener(actionEvent -> System.exit(0));
        //--------------------------------------------------------------------------Mouse Action Listeners----------------------------------------------------------------------------//
        markTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                markMouse();
            }
        });
        //-------------------------------------------------------------------------Marks Submission Button----------------------------------------------------------------------------//
        subButton.addActionListener(actionEvent -> {
            DefaultTableModel t1 = (DefaultTableModel) markTable.getModel();
            t1.setRowCount(0);
            try {
                result();
                addMark();
                showMod();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            clearField();
        });
    }

}
