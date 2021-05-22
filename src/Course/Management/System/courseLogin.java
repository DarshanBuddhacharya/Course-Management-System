package Course.Management.System;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;

public class courseLogin extends couseForm implements ClearField {
    private JButton loginButton;
    private JPanel panel1;
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField IDField;
    private JButton cancelButton;
    JPanel mainPanel;
    private JPanel Header;
    private JPanel Body;
    private JLabel Username;
    private JLabel Password;
    private JLabel ID;

    String path = new  File("src").getAbsolutePath() +"\\"+"Course\\Management\\System";
    File f = new File(path);

    public void checkData(String userField, String passwordField, String IDField) throws IOException {
        try {
            RandomAccessFile raf = new RandomAccessFile(f + "\\logData.txt", "rw");
            String id = raf.readLine();
            String userName = raf.readLine();
            String passWord = raf.readLine();
            if (userName.equals(userField) & passwordField.equals(passWord) & IDField.equals(id)) {
                JFrame frame3 = new JFrame("Course");
                frame3.setContentPane(new couseForm().mainPanelCourse);
                frame3.setMinimumSize(new Dimension(800, 550));
                frame3.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));
                frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame3.pack();
                frame3.setVisible(true);
                JOptionPane.showMessageDialog(null, "Login successful");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username / Password");
            }
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearField() {
        IDField.setText("");
        userField.setText("");
        passwordField.setText("");
    }

    public courseLogin() throws SQLException {
        super();
        loginButton.addActionListener(actionEvent -> {
            try {
                checkData(userField.getText(),
                        passwordField.getText(),
                        IDField.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            clearField();
        });
        cancelButton.addActionListener(actionEvent -> System.exit(0));
    }
}
