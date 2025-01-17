package com.kenanbabicipia.view;

import com.kenanbabicipia.controller.Style;
import com.kenanbabicipia.model.Activity;
import com.kenanbabicipia.model.Employee;
import com.kenanbabicipia.service.ActivityService;
import com.kenanbabicipia.service.EmployeeService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static java.lang.Integer.parseInt;

public class EmployeeActivityWindow {
    private JTable activityTable;
    private JPanel activityPanel;
    private JLabel titleLabel;
    private JButton backButton;
    private JLabel totalLabel;
    private JTextField searchField;
    private JLabel searchLabel;
    private final EmployeeService employeeService = new EmployeeService();
    private final ActivityService activityService = new ActivityService();

    public EmployeeActivityWindow(Employee employee){

        fillTable(employee.getRole(), employee.getEmployeeID(), 1);
        totalLabel.setText(totalLabel.getText() + " " + activityService.calculateTotalTime(activityTable, 4));
        if(employee.getRole().equals("Manager"))
            searchLabel.setText(searchLabel.getText() + " Employee ID:");
        else if(employee.getRole().equals("Employee"))
            searchLabel.setText(searchLabel.getText() + " month:");
        searchField.getDocument().addDocumentListener(new FormValidationListener(employee.getRole(), employee.getEmployeeID()));

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

    private void fillTable(String role, int employeeID, int option){
        activityTable.setModel(new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        });
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setColumnIdentifiers(new Object[]{"Employee", "Date", "Log-in Time", "Log-out Time", "Work Time"});

        List<Activity> activities = null;

        switch (option) {
            case 1:
                if (role.equals("Manager"))
                    activities = activityService.selectAllActivities();
                else
                    activities = activityService.selectAllActivities(employeeID);
                break;
            case 2:
                activities = activityService.selectAllActivities(parseInt(searchField.getText()));
                break;
            case 3:
                activities = activityService.selectAllActivities(employeeID, searchField.getText());
                break;
        }
        assert activities != null;
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

    private void filterTable(String role, int employeeID){
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        if(role.equals("Manager")){
            if(isNumeric(searchField.getText())){
                model.setRowCount(0);
                fillTable(role, employeeID,2);
                totalLabel.setText("Total work-time: " + activityService.calculateTotalTime(activityTable, 4));
            }else if(searchField.getText().isEmpty()){
                fillTable(role, employeeID, 1);
                totalLabel.setText("Total work-time: " + activityService.calculateTotalTime(activityTable, 4));
            }
        }else if(role.equals("Employee")){
            model.setRowCount(0);
            if(isNumeric(searchField.getText()) && searchField.getText().length() < 3){
                model.setRowCount(0);
                fillTable(role, employeeID,3);
                totalLabel.setText("Total work-time: " + activityService.calculateTotalTime(activityTable, 4));
            }else if(searchField.getText().isEmpty()){
                fillTable(role, employeeID, 1);
                totalLabel.setText("Total work-time: " + activityService.calculateTotalTime(activityTable, 4));
            }

        }
    }

    private boolean isNumeric(String str) {
        try {
            parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    class FormValidationListener implements DocumentListener {
        String role;
        int employeeID;
        public FormValidationListener(String role, int employeeID) {
            this.role = role;
            this.employeeID = employeeID;
        }

        @Override
        public void insertUpdate(DocumentEvent e){filterTable(role, employeeID);}
        @Override
        public void removeUpdate(DocumentEvent e){filterTable(role, employeeID);}
        @Override
        public void changedUpdate(DocumentEvent e){filterTable(role, employeeID);}
    }
}
