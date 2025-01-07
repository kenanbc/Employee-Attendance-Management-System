package com.kenanbabicipia.example;

import com.kenanbabicipia.example.model.Employee;
import com.kenanbabicipia.example.service.ActivityService;
import com.kenanbabicipia.example.service.EmployeeService;
import com.kenanbabicipia.example.view.NewEmployeeWindow;
import com.kenanbabicipia.example.view.SuperAdminWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

import static java.lang.Integer.parseInt;

public class RemoveEmployeeWindow {
    private JTable previewEmployee;
    private JButton backButton;
    private JButton removeSelectedButton;
    private JPanel removePanel;
    private JLabel selectedEmployee;
    private JTextField employeeIDField;
    private JLabel errorLabel;

    EmployeeService employeeService = new EmployeeService();


    public RemoveEmployeeWindow(Employee employee) {
        fillTable();
        employeeIDField.getDocument().addDocumentListener(new FormValidationListener());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuperAdminWindow adminWindow = new SuperAdminWindow(employee);
                adminWindow.showSuperAdminWindow(employee);
                SwingUtilities.getWindowAncestor(removePanel).dispose();
            }
        });
        employeeIDField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                employeeIDField.getDocument().addDocumentListener(new FormValidationListener());
            }
        });
        removeSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    employeeService.removeEmployee(parseInt(employeeIDField.getText()));
            }
        });
    }

    private void fillTable(){
        DefaultTableModel model = (DefaultTableModel) previewEmployee.getModel();
        model.setColumnIdentifiers(new Object[]{"EmployeeID", "First Name", "Last Name", "E-mail", "Phone Number", "Role", "Username"});
        List<Employee> employees  = employeeService.selectAllEmployees();

        for(Employee employee : employees){
            model.addRow(new Object[]{
                    employee.getEmployeeID(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getPhoneNumber(),
                    employee.getRole(),
                    employee.getUsername()
            });
        }
    }

    public void showRemoveEmployeeWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new RemoveEmployeeWindow(employee).removePanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(removePanel, employee.getEmployeeID());
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
        if(employeeIDField.equals("") || !isNumeric(employeeIDField.getText()))
            removeSelectedButton.setEnabled(false);
        else
            removeSelectedButton.setEnabled(true);
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private class FormValidationListener implements DocumentListener {
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
