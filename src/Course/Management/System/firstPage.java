package Course.Management.System;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class firstPage {
    private JButton studentButton;
    private JButton instructorButton;
    private JButton courseAdministratorButton1;
    public JPanel enroll;
    private JPanel Course;
    private JPanel Student;
    private JPanel Instructor;

    public firstPage(){
        studentButton.addActionListener(actionEvent -> {
            JFrame frame1 = new JFrame("Student registration form");
            try {
                frame1.setContentPane(new Student().mainPanel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            frame1.setMinimumSize(new Dimension(1200, 700));
            frame1.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));
            frame1.setLocationRelativeTo(null);
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame1.pack();
            frame1.setVisible(true);
        });
        instructorButton.addActionListener(actionEvent -> {
            JFrame frame3 = new JFrame("Instructor Login");
            frame3.setContentPane(new insForm().mainPanel);
            frame3.setMinimumSize(new Dimension(650, 550));
            frame3.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));
            frame3.setLocationRelativeTo(null);
            frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame3.pack();
            frame3.setVisible(true);
        });
        courseAdministratorButton1.addActionListener(actionEvent -> {
            JFrame frame2 = new JFrame("courseAdministrator Login");
            try {
                frame2.setContentPane(new courseLogin().mainPanel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            frame2.setMinimumSize(new Dimension(800, 450));
            frame2.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));
            frame2.setLocationRelativeTo(null);
            frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame2.pack();
            frame2.setVisible(true);
        });
    }
}
