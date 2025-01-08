package com.kenanbabicipia.example.view;

import com.kenanbabicipia.example.controller.Style;
import com.kenanbabicipia.example.model.Employee;
import com.kenanbabicipia.example.service.ActivityService;
import com.kenanbabicipia.example.service.EmployeeService;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
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
    private JPasswordField passwordField;
    private JButton backButton;
    private JButton addEmployeeButton;
    private JComboBox<String> roleField;
    private JLabel messageLabel;

    private final EmployeeService employeeService = new EmployeeService();

    public NewEmployeeWindow(Employee employee) {

        roleField.addItem("Employee");
        roleField.addItem("Manager");
        roleField.addItem("Superadmin");

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
                String password = BCrypt.hashpw(new String(passwordField.getPassword()), BCrypt.gensalt());
                Employee newEmployee = new Employee(firstName, lastName, email, phoneNumber, role, username, password);
                boolean isAdded = employeeService.addEmployee(newEmployee);
                if(isAdded){
                    messageLabel.setText("Employee successfully added!");
                    Style.setTextColor(messageLabel, Color.decode("#5CB338"));
                    addEmployeeButton.setEnabled(false);
                }
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
        frame.setResizable(false);
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

    public void validUsername(){
        String username = usernameField.getText();
        boolean valid = employeeService.validateUsername(username);
        if(valid){
            messageLabel.setText(" ");
        }else{
            messageLabel.setText("Username already in use.");
            Style.setTextColor(messageLabel, Color.RED);
            addEmployeeButton.setEnabled(false);
        }
    }

    class FormValidationListener implements DocumentListener{
        @Override
        public void insertUpdate(DocumentEvent e){
            validateInput();
            validUsername();
        }
        @Override
        public void removeUpdate(DocumentEvent e){validateInput(); validUsername();}
        @Override
        public void changedUpdate(DocumentEvent e){
            validateInput(); validUsername();
        }
    }

}
