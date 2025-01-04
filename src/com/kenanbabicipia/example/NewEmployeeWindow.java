package com.kenanbabicipia.example;

import org.mindrot.jbcrypt.BCrypt;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewEmployeeWindow {
    private JPanel addNewPanel;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton backButton;
    private JButton addEmployeeButton;
    private JComboBox<String> roleField;

    private final EmployeeService employeeService = new EmployeeService();

    public NewEmployeeWindow(Employee employee) {

        roleField.addItem("Employee");
        roleField.addItem("Manager");

        nameField.getDocument().addDocumentListener(new FormValidationListener());
        lastNameField.getDocument().addDocumentListener(new FormValidationListener());
        emailField.getDocument().addDocumentListener(new FormValidationListener());
        phoneField.getDocument().addDocumentListener(new FormValidationListener());
        usernameField.getDocument().addDocumentListener(new FormValidationListener());
        passwordField.getDocument().addDocumentListener(new FormValidationListener());

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuperAdminWindow superAdminWindow = new SuperAdminWindow(employee);
                superAdminWindow.showSuperAdminWindow(employee);
                SwingUtilities.getWindowAncestor(addNewPanel).dispose();
            }
        });


        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = nameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneField.getText();
                String role = (String) roleField.getSelectedItem();
                String username = usernameField.getText();
                String password = BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt());
                Employee newEmployee = new Employee(firstName, lastName, email, phoneNumber, role, username, password);
                employeeService.addEmployee(newEmployee);
            }
        });
    }

    private void validateInput(){
        if(!nameField.getText().equals("") && !lastNameField.getText().equals("") && !emailField.getText().equals("") && !phoneField.getText().equals("") && !usernameField.getText().equals("") && !passwordField.getText().equals(""))
            addEmployeeButton.setEnabled(true);
        else
            addEmployeeButton.setEnabled(false);
    }


    public void showAddNewWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new NewEmployeeWindow(employee).addNewPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(addNewPanel, employee.getEmployeeID());
                if(shouldClose){
                    frame.dispose();
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class FormValidationListener implements DocumentListener{
        @Override
        public void insertUpdate(DocumentEvent e){
            validateInput();
        }

        @Override
        public void removeUpdate(DocumentEvent e){
            validateInput();
        }

        @Override
        public void changedUpdate(DocumentEvent e){
            validateInput();
        }
    }

}
