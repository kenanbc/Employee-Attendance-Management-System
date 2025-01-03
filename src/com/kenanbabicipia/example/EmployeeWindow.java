package com.kenanbabicipia.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeWindow {
    private JPanel employeePanel;
    private JLabel welcomeLabel;
    private JButton yourActivityButton;
    private JButton createVacationRequestButton;
    private JButton logOutButton;
    private JLabel activityLabel;
    private JLabel requestLabel;

    public EmployeeWindow(Employee employee) {

        welcomeLabel.setText(welcomeLabel.getText() + " " + employee.getFirstName() + " " + employee.getLastName());

        activityLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\activity.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        requestLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\request.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));


        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm();

                loginForm.showLoginPanel();
                SwingUtilities.getWindowAncestor(employeePanel).dispose();
            }
        });
    }

    public void showEmployeeWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new EmployeeWindow(employee).employeePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
