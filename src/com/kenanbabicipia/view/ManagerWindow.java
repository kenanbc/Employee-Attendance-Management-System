package com.kenanbabicipia.view;

import com.kenanbabicipia.controller.Style;
import com.kenanbabicipia.model.Employee;
import com.kenanbabicipia.service.ActivityService;

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
    private JButton themeButton;
    private JLabel developedLabel;

    public ManagerWindow(Employee employee){
        welcomeLabel.setText(welcomeLabel.getText() + " " + employee.getFirstName() + " " + employee.getLastName());
        Style.setDefaultTitleFont(welcomeLabel);
        Style.developedByWaterMark(developedLabel);

        //activityLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\activity.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        //requestLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\request.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        //reportLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\dataReport.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));

        activityLabel.setText("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB");
        requestLabel.setText("\uD83D\uDCDD");
        reportLabel.setText("\uD83D\uDCC4");
        Style.setIconFont(reportLabel);
        Style.setIconFont(activityLabel);
        Style.setIconFont(requestLabel);


        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActivityService activityService = new ActivityService();

                int confirmation = JOptionPane.showConfirmDialog(managerPanel, "Would you like to log out?","Warning!", JOptionPane.YES_NO_OPTION);
                if(confirmation == JOptionPane.YES_OPTION){
                    activityService.updateLogOut(employee.getEmployeeID());
                    LoginForm loginForm = new LoginForm();
                    loginForm.showLoginPanel();
                    SwingUtilities.getWindowAncestor(managerPanel).dispose();
                }
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
        themeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Style.switchTheme(e,  SwingUtilities.getRoot((JButton) e.getSource()));
                Style.developedByWaterMark(developedLabel);
            }
        });
    }

    public void showManagerWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new ManagerWindow(employee).managerPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(managerPanel, employee.getEmployeeID());
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
