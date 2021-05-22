package Course.Management.System;

import Helpers.DBUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;



public class DataBase {
    Connection connectDataBase;
    public Connection connection;
    private Connection con;
    private PreparedStatement insert;

    private String CreateDataBase = "create database np03a190140";

    private String CoursesTable = "create table courses(" +
            "Course_id varchar(20) not null," +
            "Name varchar(150) not null," +
            "Status varchar(20) not null," +
            "UNIQUE(Course_id)," +
            "primary key(Course_id)" +
            ");";

    private String InstructorTable = "create table instructor(" +
            "Ins_id varchar(20) not null," +
            "FullName varchar(100) not null," +
            "primary key(Ins_id)," +
            "UNIQUE(Ins_id)" +
            ");";

    private String ModulesTable = "create table modules(" +
            "Mod_id varchar(20) not null," +
            "Name varchar(200) not null," +
            "Course_id varchar(20) not null," +
            "level varchar(15) not null," +
            "Semester varchar(15) not null," +
            "Ins_id varchar(20) not null," +
            "State varchar(20) not null," +
            "primary key(Mod_id)," +
            "UNIQUE(Mod_id)," +
            "foreign key(Course_id) references courses(Course_id) " +
            "on Delete Cascade " +
            "on Update Cascade" +
            ");";

    private String StudentTable = "create table student(" +
            "ID varchar(15) not null," +
            "FirstName varchar(50) not null," +
            "LastName varchar(50) not null," +
            "Gender varchar(15) not null," +
            "Address varchar(50) not null," +
            "Email varchar(100) not null," +
            "Course varchar(20) not null," +
            "Level int(50) not null," +
            "primary key(ID)," +
            "foreign key(Course) references courses(Course_id) " +
            "on Delete Cascade " +
            "on Update Cascade" +
            ");";

    private String StudentModuleTable = "create table std_mod(" +
            "Student_id varchar(20) not null," +
            "Student_Name varchar(100) not null," +
            "Mod_id varchar(50) not null," +
            "Mod_name varchar(100) not null,"+
            "Ins_id varchar(20) not null," +
            "Marks varchar(50) not null," +
            "Result varchar(20) not null," +
            "foreign key(Student_id) references student(ID) " +
            "on Delete Cascade " +
            "on Update Cascade," +
            "foreign key(Mod_id) references modules(Mod_id) " +
            "on Delete Cascade " +
            "on Update Cascade," +
            "foreign key(Ins_id) references instructor(Ins_id) " +
            "on Delete Cascade " +
            "on Update Cascade" +
            ");";

    public DataBase() throws SQLException {
        try {
            connectDataBase = DBUtils.getDbConnectionDataBase();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL not connected, Connect sql");
        }
    }
    public ArrayList<String> getFileData(String path) {
        ArrayList<String> Data;
        Data = new ArrayList<String>();

        path = new File("src").getAbsolutePath() +"\\"+path;
        try {
            File F = new File(path);
            Scanner FileReader = new Scanner(F);
            while (FileReader.hasNextLine()) {
                Data.add(FileReader.nextLine().trim());                 //## it reads and returns all the lines found
            }                                                           //## in the file
            FileReader.close();
        } catch (FileNotFoundException err) {
            JOptionPane.showMessageDialog(null,"File not Found!!");
        }
        return Data;
    }

    public void loadCourseFromFile() throws SQLException {
        con = DBUtils.getDbConnection();
        PreparedStatement st;
        String CoursesTable = "insert into `courses`(`Course_id`, `Name`, `Status`)values(?,?,?)";

        ArrayList<String> CourseId = this.getFileData("Course\\Management\\System\\Courses\\CourseId.txt");
        ArrayList<String> CourseName = this.getFileData("Course\\Management\\System\\Courses\\CourseName.txt");
        ArrayList<String> Status= this.getFileData("Course\\Management\\System\\Courses\\Status.txt");

        for(int i=0; i<CourseId.size();i++){
            extractCourse cou = new extractCourse();
            cou.CourseId = CourseId.get(i);
            cou.CourseName= CourseName.get(i);
            cou.Status= Status.get(i);

            st = con.prepareStatement(CoursesTable);
            st.setString(1, cou.CourseId);
            st.setString(2, cou.CourseName);
            st.setString(3, cou.Status);
            st.executeUpdate();
        }
    }

    public void loadInstructorFromFile() throws SQLException {
        con = DBUtils.getDbConnection();
        PreparedStatement st;
        String insTable = "insert into instructor(Ins_id, FullName)values(?,?)";

        ArrayList<String> insId = this.getFileData("Course\\Management\\System\\Instructor\\InsId.txt");
        ArrayList<String> insName = this.getFileData("Course\\Management\\System\\Instructor\\InsName.txt");

        for(int i=0; i<insId.size();i++){
            extractInstructor ins = new extractInstructor();
            ins.insId = insId.get(i);
            ins.insName= insName.get(i);

            st = con.prepareStatement(insTable);
            st.setString(1, ins.insId);
            st.setString(2, ins.insName);
            st.executeUpdate();
        }
    }

    public void loadModuleFromFile() throws SQLException {
        con = DBUtils.getDbConnection();
        PreparedStatement st;
        String modTable = "insert into modules(Mod_id, Name, Course_id, Level, Semester, Ins_id, State)values(?,?,?,?,?,?,?)";

        ArrayList<String> modId = this.getFileData("Course\\Management\\System\\Modules\\ModId.txt");
        ArrayList<String> modName = this.getFileData("Course\\Management\\System\\Modules\\ModName.txt");
        ArrayList<String> modCou = this.getFileData("Course\\Management\\System\\Modules\\CourseId.txt");
        ArrayList<String> modLev = this.getFileData("Course\\Management\\System\\Modules\\Level.txt");
        ArrayList<String> modSem = this.getFileData("Course\\Management\\System\\Modules\\Semester.txt");
        ArrayList<String> modIns = this.getFileData("Course\\Management\\System\\Modules\\Ins_Id.txt");
        ArrayList<String> modState = this.getFileData("Course\\Management\\System\\Modules\\State.txt");

        for(int i=0; i<=modId.size();i++){
            extractModules mod = new extractModules();
            mod.ModId = modId.get(i);
            mod.modName = modName.get(i);
            mod.modCou = modCou.get(i);
            mod.modLev = modLev.get(i);
            mod.modSem = modSem.get(i);
            mod.modIns = modIns.get(i);
            mod.modState =modState.get(i);

            st = con.prepareStatement(modTable);
            st.setString(1, mod.ModId);
            st.setString(2, mod.modName);
            st.setString(3, mod.modCou);
            st.setString(4, mod.modLev);
            st.setString(5, mod.modSem);
            st.setString(6, mod.modIns);
            st.setString(7, mod.modState);
            st.executeUpdate();
        }
    }

    public boolean findDatabase() throws SQLException {
            connectDataBase = DBUtils.getDbConnectionDataBase();

            String Query = "show databases";
            PreparedStatement statement = connectDataBase.prepareStatement(Query);

            ResultSet resultSet = statement.executeQuery();
            boolean databaseFound = false;

            while (resultSet.next()) {
                String DbName = resultSet.getString(1);
                if (DbName.equals("np03a190140")) {
                    databaseFound = true;
                }
            }
        return databaseFound;
    }

    public void createDatabase() throws SQLException {
        System.out.println("Creating a new Database");
        connectDataBase = DBUtils.getDbConnectionDataBase();
        PreparedStatement statement = connectDataBase.prepareStatement(this.CreateDataBase);
        statement.executeUpdate();
    }

    public void createCoursesTable() throws SQLException {
        connection = DBUtils.getDbConnection();
        PreparedStatement statement = connection.prepareStatement(this.CoursesTable);
        statement.executeUpdate();
    }

    public void createModulesTable() throws SQLException {
        connection = DBUtils.getDbConnection();
        PreparedStatement statement = connection.prepareStatement(this.ModulesTable);
        statement.executeUpdate();
    }

    public void createInstructorsTable() throws SQLException {
        connection = DBUtils.getDbConnection();
        PreparedStatement statement = connection.prepareStatement(this.InstructorTable);
        statement.executeUpdate();
    }

    public void createStudentTable() throws SQLException {
        connection = DBUtils.getDbConnection();
        PreparedStatement statement = connection.prepareStatement(this.StudentTable);
        statement.executeUpdate();
    }

    public void createStudentModuleTable() throws SQLException {
        connection = DBUtils.getDbConnection();
        PreparedStatement statement = connection.prepareStatement(this.StudentModuleTable);
        statement.executeUpdate();
        System.out.println("Database Created");
    }


}
