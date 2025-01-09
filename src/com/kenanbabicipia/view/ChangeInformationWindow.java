package com.kenanbabicipia.view;

import com.kenanbabicipia.controller.Style;
import com.kenanbabicipia.model.Employee;
import com.kenanbabicipia.service.ActivityService;
import com.kenanbabicipia.service.EmployeeService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    private JComboBox<String> roleOptions;
    private JButton saveChangesButton;
    private JLabel messageLabel;
    private static final EmployeeService employeeService = new EmployeeService();

    public ChangeInformationWindow(Employee employee) {

        roleOptions.addItem("Employee");
        roleOptions.addItem("Manager");
        roleOptions.addItem("Superadmin");

        nameField.getDocument().addDocumentListener(new FormValidationListener());
        lastNameField.getDocument().addDocumentListener(new FormValidationListener());
        emailField.getDocument().addDocumentListener(new FormValidationListener());
        numberField.getDocument().addDocumentListener(new FormValidationListener());
        usernameField.getDocument().addDocumentListener(new FormValidationListener());

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
                messageLabel.setText("");
                if(employeeIDField.getText().equals("")){
                    emptyForm();
                    chanegeStateForm(false);
                    return;
                }
                int employeeID = parseInt(employeeIDField.getText());


                Employee selectedEmployee = employeeService.selectEmployeeInformation(employeeID);

                if(selectedEmployee != null){
                    fillSelectedFields(selectedEmployee.getEmployeeID());
                }
                else{
                    emptyForm();
                    chanegeStateForm(false);
                }
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee selectedEmployee = getChanges();
                boolean isUpdated = employeeService.updateEmployeeInformation(selectedEmployee);
                if(isUpdated){
                    chanegeStateForm(false);
                    messageLabel.setText("Successfully updated employee information!");
                    Style.setTextColor(messageLabel, Color.decode("#5CB338"));
                }
                else{
                    messageLabel.setText("Unsuccessful update! Try again!");
                    Style.setTextColor(messageLabel, Color.RED);
                }
            }
        });
    }

    private void fillSelectedFields(int employeeID){

        Employee selectedEmployee = employeeService.selectEmployeeInformation(employeeID);
        nameField.setText(selectedEmployee.getFirstName());
        lastNameField.setText(selectedEmployee.getLastName());
        emailField.setText(selectedEmployee.getEmail());
        numberField.setText(selectedEmployee.getPhoneNumber());
        usernameField.setText(selectedEmployee.getUsername());
        roleOptions.setSelectedItem(selectedEmployee.getRole());

        chanegeStateForm(true);

    }

    private Employee getChanges(){
        int employeeID = parseInt(employeeIDField.getText());
        String firstName = nameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String number = numberField.getText();
        String username = usernameField.getText();
        String role = (String) roleOptions.getSelectedItem();
        Employee changedEmployee = new Employee(employeeID, firstName, lastName, email, number, role, username);
        return changedEmployee;
    }

    private void chanegeStateForm(boolean state){
        nameField.setEnabled(state);
        lastNameField.setEnabled(state);
        emailField.setEnabled(state);
        numberField.setEnabled(state);
        usernameField.setEnabled(state);
        roleOptions.setEnabled(state);
        saveChangesButton.setEnabled(state);
    }

    private void emptyForm(){
        nameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        numberField.setText("");
        usernameField.setText("");
        roleOptions.setSelectedItem("");
    }


    public void showChangeWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new ChangeInformationWindow(employee).changePanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
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

    private void validateInput(){
        if(!nameField.getText().equals("") && !lastNameField.getText().equals("") && !emailField.getText().equals("") && !numberField.getText().equals("") && !usernameField.getText().equals(""))
            saveChangesButton.setEnabled(true);
        else
            saveChangesButton.setEnabled(false);
    }

    public void validUsername(){
        String username = usernameField.getText();
        boolean valid = employeeService.validateUsername(username);
        if(valid){
            messageLabel.setText(" ");
        }else{
            messageLabel.setText("Username already in use.");
            Style.setTextColor(messageLabel, Color.RED);
            saveChangesButton.setEnabled(false);
        }
    }

    class FormValidationListener implements DocumentListener {
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
