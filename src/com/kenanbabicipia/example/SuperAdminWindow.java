package com.kenanbabicipia.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SuperAdminWindow {
    private JPanel adminPanel;
    private JButton addNewEmployeeButton;
    private JButton removeEmployeeButton;
    private JButton changeInformationButton;
    private JButton logOutButton;
    private JLabel changeLabel;
    private JLabel removeLabel;
    private JLabel addLabel;
    private JLabel welcomeLabel;

    public SuperAdminWindow(Employee employee) {
        welcomeLabel.setText(welcomeLabel.getText() + " " + employee.getFirstName() + " " + employee.getLastName());
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActivityService activityService = new ActivityService();
                activityService.updateLogOut(employee.getEmployeeID());

                LoginForm loginForm = new LoginForm();
                loginForm.showLoginPanel();
                SwingUtilities.getWindowAncestor(adminPanel).dispose();
            }
        });
        addNewEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewEmployeeWindow newEmployeeWindow = new NewEmployeeWindow(employee);
                newEmployeeWindow.showAddNewWindow(employee);
                SwingUtilities.getWindowAncestor(adminPanel).dispose();
            }
        });
    }

    public void showSuperAdminWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new SuperAdminWindow(employee).adminPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(adminPanel, employee.getEmployeeID());
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

