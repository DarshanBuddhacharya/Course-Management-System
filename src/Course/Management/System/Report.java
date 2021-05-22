package Course.Management.System;

import Helpers.DBUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Report implements CreateTable {
    private JTable reportTable;
    private JLabel idText;
    private JLabel firstNameText;
    private JLabel lastNameText;
    private JLabel genderText;
    private JLabel courseText;
    private JLabel levelText;
    public JPanel mainPanel;
    private JLabel resultText;
    private JLabel gpaText;

    private String ID;
    ///////////////////////////////////////////////////////////////////////////////////Database Connectors///////////////////////////////////////////////////////////////////////////
    private Connection con;
    ///////////////////////////////////////////////////////////////////////////////////Table creator///////////////////////////////////////////////////////////////////////////
    @Override
    public void createTable() {
        reportTable.setModel(new DefaultTableModel(
                null, new String[]{"Module ID", "Module Name", "Full Marks", "Pass Marks", "Obtained Marks","Result"}
        ));
    }
    ////////////////////////////////////////////////////////////////////////////////Extracting data from sql server/////////////////////////////////////////////////////////////
    public ArrayList<extracter> extractData() throws SQLException {
        ArrayList<extracter> extractsData = new ArrayList<>();
        try {
            con = DBUtils.getDbConnection();
            Statement st = con.createStatement();
            ResultSet x = st.executeQuery("SELECT * From std_mod where Student_id='" + ID + "'");
            extracter extractRes;
            while (x.next()) {
                extractRes = new extracter(x.getString("Mod_id"), x.getString("Mod_name"),x.getString("Marks"), x.getString("Result"));
                extractsData.add(extractRes);
            }
            x = st.executeQuery("SELECT * From student where ID='" + ID + "'");
            while (x.next()) {
                idText.setText(x.getString("ID"));
                firstNameText.setText(x.getString("FirstName"));
                lastNameText.setText(x.getString("LastName"));
                genderText.setText(x.getString("Gender"));
                courseText.setText(x.getString("Course"));
                levelText.setText(x.getString("Level"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extractsData;
    }
    ///////////////////////////////////////////////////////////////////////////////////Show report card/////////////////////////////////////////////////////////////////////
    private void showReport() throws SQLException {
        ArrayList<extracter> list = extractData();
        DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
        Object[] row = new Object[6];
        for (Course.Management.System.extracter extracter : list) {
            row[0] = extracter.getMod_id();
            row[1] = extracter.getMod_name();
            row[2] = "100";
            row[3] = "40";
            row[4] = extracter.getMarks();
            row[5] = extracter.getResult();
            model.addRow(row);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////To calculate gpa and result/////////////////////////////////////////////////////////////
    private void calculate() {
        int sum = 0;
        float GPA;
        for (int i = 0; i < reportTable.getRowCount(); i++) {
            sum = sum + Integer.parseInt(reportTable.getValueAt(i, 4).toString());
            String res = reportTable.getValueAt(i,5).toString();
            if(res.equals("Fail")){
                resultText.setText("Failed");
            }
            else{
                resultText.setText("Passed");
            }
        }
        sum = Integer.parseInt(String.valueOf(sum));
        GPA = ((sum * 100f) / 800f) / 25f;
        gpaText.setText(String.valueOf(GPA));
    }
    public Report(String StdId) throws SQLException {
        ID = StdId;
        createTable();
        showReport();
        calculate();
    }
}
