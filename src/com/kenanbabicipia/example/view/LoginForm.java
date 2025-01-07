package com.kenanbabicipia.example.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.kenanbabicipia.example.controller.Style;
import com.kenanbabicipia.example.model.Activity;
import com.kenanbabicipia.example.model.Employee;
import com.kenanbabicipia.example.service.ActivityService;
import com.kenanbabicipia.example.service.EmployeeService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LoginForm {
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel titleLabel;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    private JLabel developedLabel;
    private JButton logInButton;
    private JLabel wrongLabel;
    private JButton themeButton;

    public LoginForm() {
        Style.setDefaultTitleFont(titleLabel);
        Style.setItalicFont(developedLabel);
        Style.setBoldFont(logInButton);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                EmployeeService employeeService = new EmployeeService();

                try {
                    Employee employee = employeeService.verifyLogIn(username, password);
                    if(employee != null){
                        trackLogInTime(employee);
                        String role = employee.getRole();
                        switch (role) {
                            case "Manager":
                                ManagerWindow managerWindow = new ManagerWindow(employee);
                                managerWindow.showManagerWindow(employee);
                                break;

                            case "Employee":
                                EmployeeWindow employeeWindow = new EmployeeWindow(employee);
                                employeeWindow.showEmployeeWindow(employee);
                                break;

                            case "Superadmin":
                                SuperAdminWindow superAdminWindow = new SuperAdminWindow(employee);
                                superAdminWindow.showSuperAdminWindow(employee);
                                break;

                            default:
                                return;
                        }

                        SwingUtilities.getWindowAncestor(loginPanel).dispose();

                    }else{
                        wrongLabel.setVisible(true);
                    }
                }catch (SQLException er){
                    System.out.println("Error: " + e);
                }
            }
        });
        themeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Style.switchTheme(e);
            }
        });
    }

    public void showLoginPanel(){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new LoginForm().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void trackLogInTime(Employee employee){
        LocalDate date = LocalDate.now();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        LocalTime time = LocalTime.now();
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Activity activity = new Activity(employee.getEmployeeID(), formattedDate, Time.valueOf(formattedTime));
        ActivityService activityService = new ActivityService();
        activityService.addActivitiy(activity);
    }

}
