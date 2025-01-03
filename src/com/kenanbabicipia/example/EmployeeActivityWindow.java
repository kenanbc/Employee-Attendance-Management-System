package com.kenanbabicipia.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class EmployeeActivityWindow {
    private JTable activityTable;
    private JPanel activityPanel;
    private JLabel titleLabel;
    private JButton backButton;
    private EmployeeService employeeService = new EmployeeService();
    private ActivityService activityService = new ActivityService();

    public EmployeeActivityWindow(Employee employee){
        this.employeeService = new EmployeeService();
        fillTable();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerWindow managerWindow = new ManagerWindow(employee);
                managerWindow.showManagerWindow(employee);
                SwingUtilities.getWindowAncestor(activityPanel).dispose();
            }
        });

    }

    public void showEmployeeActivityWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new EmployeeActivityWindow(employee).activityPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                Boolean shouldClose = activityService.closingLogOut(activityPanel, employee.getEmployeeID());
                if(shouldClose){
                    frame.dispose();
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void fillTable(){
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setColumnIdentifiers(new Object[]{"EmployeeID", "Date", "Work Time", "Log-in Time", "Log-out Time"});
        List<Activity> activities = activityService.selectAllActivities();

        for(Activity activity : activities){
            model.addRow(new Object[]{
                    activity.getEmployeeID(),
                    activity.getDate(),
                    activity.getLogin(),
                    activity.getLogout(),
                    activity.getTotalWork()
            });
        }
    }
}