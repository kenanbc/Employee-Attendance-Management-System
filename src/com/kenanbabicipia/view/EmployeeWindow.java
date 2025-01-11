package com.kenanbabicipia.view;

import com.kenanbabicipia.controller.Style;
import com.kenanbabicipia.model.Employee;
import com.kenanbabicipia.service.ActivityService;

import javax.swing.*;
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
    private JButton themeButton;
    private JButton notificationButton;
    private JLabel developedLabel;

    public EmployeeWindow(Employee employee) {

        welcomeLabel.setText(welcomeLabel.getText() + " " + employee.getFirstName() + " " + employee.getLastName());
        Style.setDefaultTitleFont(welcomeLabel);
        Style.developedByWaterMark(developedLabel);

        //activityLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\activity.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        //requestLabel.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\request.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));


        activityLabel.setText("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB");
        requestLabel.setText("\uD83D\uDCDD");
        Style.setIconFont(activityLabel);
        Style.setIconFont(requestLabel);

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActivityService activityService = new ActivityService();
                int confirmation = JOptionPane.showConfirmDialog(employeePanel, "Would you like to log out?","Warning!", JOptionPane.YES_NO_OPTION);
                if(confirmation == JOptionPane.YES_OPTION){
                    activityService.updateLogOut(employee.getEmployeeID());
                    LoginForm loginForm = new LoginForm();
                    loginForm.showLoginPanel();
                    SwingUtilities.getWindowAncestor(employeePanel).dispose();
                }
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
        themeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Style.switchTheme(e,  SwingUtilities.getRoot((JButton) e.getSource()));
                Style.developedByWaterMark(developedLabel);
            }
        });
        notificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationWindow.showNotificationWindow(employee);
            }
        });
    }

    public void showEmployeeWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new EmployeeWindow(employee).employeePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(employeePanel, employee.getEmployeeID());
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
