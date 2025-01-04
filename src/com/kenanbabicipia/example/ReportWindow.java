package com.kenanbabicipia.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ReportWindow {
    private JPanel reportPanel;
    private JLabel titleLabel;
    private JButton searchButton;
    private JButton backButton;
    private JTable activityTable;
    private JButton generateReportInPDFButton;
    private JLabel selectedLabel;
    private JFormattedTextField employeeIDField;
    private JFormattedTextField monthField;
    private JLabel monthLabel;
    private ActivityService activityService = new ActivityService();

    public ReportWindow(Employee employee) {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerWindow managerWindow = new ManagerWindow(employee);
                managerWindow.showManagerWindow(employee);
                SwingUtilities.getWindowAncestor(reportPanel).dispose();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedLabel.setText("Selected Employee: ");
                if(employeeIDField.getText().equals("")){
                    selectedLabel.setText(selectedLabel.getText() + "No Employee ID entered! Try again!");
                    DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
                    model.setRowCount(0);
                    generateReportInPDFButton.setEnabled(false);
                    return;
                }
                int employeeID = parseInt(employeeIDField.getText());
                EmployeeService employeeService = new EmployeeService();

                Employee selectedEmployee = employeeService.selectEmployeeInformation(employeeID);
                if(selectedEmployee != null){
                    selectedLabel.setText(selectedLabel.getText() + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() + ", Role: " + selectedEmployee.getRole());
                    if(monthField.equals("")) fillReportTable(selectedEmployee.getEmployeeID(), monthField.getText());
                    else fillReportTable(selectedEmployee.getEmployeeID(), monthField.getText());
                    generateReportInPDFButton.setEnabled(true);
                }
                else{
                    selectedLabel.setText(selectedLabel.getText() + "There is no Employee with entered ID! Try again!");
                }
            }
        });
    }

    private void fillReportTable(int employeID, String month){
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{"EmployeeID", "Date", "Log-in Time", "Log-out Time", "Work Time"});
        List<Activity> activities;

        if(month.equals(""))
            activities = activityService.selectAllActivities(employeID);
        else
            activities = activityService.selectAllActivities(employeID, month);

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

    public void showReportWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new ReportWindow(employee).reportPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(reportPanel, employee.getEmployeeID());
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
