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
    private JFormattedTextField formattedTextField1;
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
                int employeeID = parseInt(employeeIDField.getText());
                EmployeeService employeeService = new EmployeeService();
                Employee selectedEmployee = employeeService.selectEmployeeInformation(employeeID);
                selectedLabel.setText("Selected Employee: ");
                if(selectedEmployee != null){
                    selectedLabel.setText(selectedLabel.getText() + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() + ", Role: " + selectedEmployee.getRole());
                    fillReportTable(selectedEmployee.getEmployeeID());
                }
                else{
                    selectedLabel.setText(selectedLabel.getText() + "There is no Employee with entered ID! Try again!");
                }
            }
        });
    }

    private void fillReportTable(int employeID){
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setColumnIdentifiers(new Object[]{"EmployeeID", "Date", "Log-in Time", "Log-out Time", "Work Time"});
        List<Activity> activities = activityService.selectAllActivities(employeID);

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
