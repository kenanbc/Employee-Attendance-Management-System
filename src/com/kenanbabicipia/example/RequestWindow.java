package com.kenanbabicipia.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RequestWindow {
    private JPanel requestPanel;
    private JLabel titleLabel;
    private JTable table1;
    private JButton backButton;

    public RequestWindow(Employee employee){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerWindow managerWindow = new ManagerWindow(employee);
                managerWindow.showManagerWindow(employee);
                SwingUtilities.getWindowAncestor(requestPanel).dispose();
            }
        });
    }

    public void showRequestWindow(Employee employee){
            JFrame frame = new JFrame("Employee Attendance Management System");
            frame.setContentPane(new RequestWindow(employee).requestPanel);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(requestPanel, employee.getEmployeeID());
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
