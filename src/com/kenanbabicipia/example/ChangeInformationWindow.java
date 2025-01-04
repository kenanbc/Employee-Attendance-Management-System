package com.kenanbabicipia.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ChangeInformationWindow {
    private JPanel changePanel;
    private JTextField employeeIDField;
    private JButton searchButton;
    private JButton backButton;
    private JTable selectedTable;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField numberField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JComboBox<String> roleOptions;
    private JButton saveChangesButton;

    public ChangeInformationWindow(Employee employee) {

        roleOptions.addItem("Employee");
        roleOptions.addItem("Manager");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuperAdminWindow superAdminWindow = new SuperAdminWindow(employee);
                superAdminWindow.showSuperAdminWindow(employee);
                SwingUtilities.getWindowAncestor(changePanel).dispose();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(employeeIDField.getText().equals("")){
                    nameField.setText("");
                    lastNameField.setText("");
                    emailField.setText("");
                    numberField.setText("");
                    usernameField.setText("");
                    roleOptions.setSelectedItem("");
                    saveChangesButton.setEnabled(false);
                    return;
                }
                int employeeID = parseInt(employeeIDField.getText());

                EmployeeService employeeService = new EmployeeService();
                Employee selectedEmployee = employeeService.selectEmployeeInformation(employeeID);

                if(selectedEmployee != null){
                    fillSelectedFields(selectedEmployee.getEmployeeID());
                    saveChangesButton.setEnabled(true);
                }
                else{
                    saveChangesButton.setEnabled(false);
                }
            }
        });
    }

    private void fillSelectedFields(int employeID){

        EmployeeService employeeService = new EmployeeService();
        Employee selectedEmployee = employeeService.selectEmployeeInformation(employeID);
        nameField.setText(selectedEmployee.getFirstName());
        lastNameField.setText(selectedEmployee.getLastName());
        emailField.setText(selectedEmployee.getEmail());
        numberField.setText(selectedEmployee.getPhoneNumber());
        usernameField.setText(selectedEmployee.getUsername());
        roleOptions.setSelectedItem(selectedEmployee.getRole());

        nameField.setEnabled(true);
        lastNameField.setEnabled(true);
        emailField.setEnabled(true);
        numberField.setEnabled(true);
        usernameField.setEnabled(true);
        passwordField.setEnabled(true);
        roleOptions.setEnabled(true);

    }


    public void showChangeWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new ChangeInformationWindow(employee).changePanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(changePanel, employee.getEmployeeID());
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
