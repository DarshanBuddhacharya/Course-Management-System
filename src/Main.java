import Course.Management.System.DataBase;
import Course.Management.System.firstPage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main extends firstPage{

    public static void main(String[]args) throws SQLException {
    JFrame frame = new JFrame("Enroll");
    frame.setContentPane(new firstPage().enroll);
    frame.setMinimumSize(new Dimension(800, 450));
    frame.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    DataBase Db = new DataBase();
    Db.findDatabase();
        if (!Db.findDatabase()) {
        Db.createDatabase();
        Db.createCoursesTable();
        Db.createModulesTable();
        Db.createInstructorsTable();
        Db.createStudentTable();
        Db.createStudentModuleTable();
        Db.loadCourseFromFile();
        Db.loadInstructorFromFile();
        try{
            Db.loadModuleFromFile();
        }
        catch (java.lang.IndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null, "Data added in DataBase");
        }
     }
    }
}
