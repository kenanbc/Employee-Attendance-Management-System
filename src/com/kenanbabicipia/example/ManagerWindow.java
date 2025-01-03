package com.kenanbabicipia.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ManagerWindow {


    private JPanel managerPanel;
    private JLabel welcomeLabel;
    private JButton employeeActivityButton;
    private JButton createMonthlyReportButton;
    private JButton viewRequestsButton;
    private JButton logOutButton;
    private JLabel activityLabel;
    private JLabel reportLabel;
    private JLabel requestLabel;

    public ManagerWindow(Employee employee){
        welcomeLabel.setText(welcomeLabel.getText() + " " + employee.getFirstName() + " " + employee.getLastName());
        activityLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\activity.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        requestLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\request.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        reportLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\report.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));


        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActivityService activityService = new ActivityService();
                activityService.updateLogOut(employee.getEmployeeID());

                LoginForm loginForm = new LoginForm();
                loginForm.showLoginPanel();
                SwingUtilities.getWindowAncestor(managerPanel).dispose();
            }
        });

        employeeActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeActivityWindow activityWindow = new EmployeeActivityWindow(employee);
                activityWindow.showEmployeeActivityWindow(employee);
                SwingUtilities.getWindowAncestor(managerPanel).dispose();
            }
        });
        viewRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestWindow requestWindow = new RequestWindow(employee);
                requestWindow.showRequestWindow(employee);
                SwingUtilities.getWindowAncestor(managerPanel).dispose();
            }
        });
        createMonthlyReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportWindow reportWindow = new ReportWindow(employee);
                reportWindow.showReportWindow(employee);
                SwingUtilities.getWindowAncestor(managerPanel).dispose();
            }
        });
    }

    public void showManagerWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new ManagerWindow(employee).managerPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                Boolean shouldClose = activityService.closingLogOut(managerPanel, employee.getEmployeeID());
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
