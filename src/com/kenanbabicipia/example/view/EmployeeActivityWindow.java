package com.kenanbabicipia.example.view;

import com.kenanbabicipia.example.controller.Style;
import com.kenanbabicipia.example.model.Activity;
import com.kenanbabicipia.example.model.Employee;
import com.kenanbabicipia.example.service.ActivityService;
import com.kenanbabicipia.example.service.EmployeeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class EmployeeActivityWindow {
    private JTable activityTable;
    private JPanel activityPanel;
    private JLabel titleLabel;
    private JButton backButton;
    private JLabel totalLabel;
    private final EmployeeService employeeService = new EmployeeService();
    private final ActivityService activityService = new ActivityService();

    public EmployeeActivityWindow(Employee employee){

        fillTable(employee.getRole(), employee.getEmployeeID());
        totalLabel.setText(totalLabel.getText() + " " + activityService.calculateTotalTime(activityTable, 4));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(employee.getRole().equals("Manager")){
                    ManagerWindow managerWindow = new ManagerWindow(employee);
                    managerWindow.showManagerWindow(employee);
                }else if(employee.getRole().equals("Employee")){
                    EmployeeWindow employeeWindow = new EmployeeWindow(employee);
                    employeeWindow.showEmployeeWindow(employee);
                }
                SwingUtilities.getWindowAncestor(activityPanel).dispose();
            }
        });

    }

    public void showEmployeeActivityWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new EmployeeActivityWindow(employee).activityPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(activityPanel, employee.getEmployeeID());
                if(shouldClose){
                    frame.dispose();
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void fillTable(String role, int employeeID){
        activityTable.setModel(new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        });
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setColumnIdentifiers(new Object[]{"Employee", "Date", "Log-in Time", "Log-out Time", "Work Time"});

        List<Activity> activities;

        if(role.equals("Manager"))
            activities = activityService.selectAllActivities();
        else
            activities = activityService.selectAllActivities(employeeID);

        for(Activity activity : activities){
            Employee employee = employeeService.selectEmployeeInformation(activity.getEmployeeID());
            model.addRow(new Object[]{
                    employee.getFirstName() + " " + employee.getLastName(),
                    activity.getDate(),
                    activity.getLogin(),
                    activity.getLogout(),
                    activity.getTotalWork()
            });
        }
        Style.centerAlignTable(activityTable);
    }
}
