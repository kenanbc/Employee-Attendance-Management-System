package com.kenanbabicipia.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
                ActivityService activityService = new ActivityService();
                activityService.updateLogOut(employee.getEmployeeID());

                LoginForm loginForm = new LoginForm();
                loginForm.showLoginPanel();
                SwingUtilities.getWindowAncestor(employeePanel).dispose();
            }
        });
        createVacationRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VacationRequest vacationRequest = new VacationRequest(employee);
                vacationRequest.showVacationRequest(employee);
                SwingUtilities.getWindowAncestor(employeePanel).dispose();
            }
        });
        yourActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeActivityWindow employeeActivityWindow = new EmployeeActivityWindow(employee);
                employeeActivityWindow.showEmployeeActivityWindow(employee);
                SwingUtilities.getWindowAncestor(employeePanel).dispose();
            }
        });
    }

    public void showEmployeeWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new EmployeeWindow(employee).employeePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                Boolean shouldClose = activityService.closingLogOut(employeePanel, employee.getEmployeeID());
                if(shouldClose){
                    frame.dispose();
                }
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
