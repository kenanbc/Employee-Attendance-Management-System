package com.kenanbabicipia.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RemoveEmployeeWindow {
    private JTable previewEmployee;
    private JButton backButton;
    private JButton removeSelectedButton;
    private JPanel removePanel;
    private JLabel selectedEmployee;

    public RemoveEmployeeWindow(Employee employee) {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuperAdminWindow adminWindow = new SuperAdminWindow(employee);
                adminWindow.showSuperAdminWindow(employee);
                SwingUtilities.getWindowAncestor(removePanel).dispose();
            }
        });
    }

    public void showRemoveEmployeeWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new RemoveEmployeeWindow(employee).removePanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(removePanel, employee.getEmployeeID());
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
