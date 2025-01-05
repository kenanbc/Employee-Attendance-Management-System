package com.kenanbabicipia.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class RemoveEmployeeWindow {
    private JTable previewEmployee;
    private JButton backButton;
    private JButton removeSelectedButton;
    private JPanel removePanel;
    private JLabel selectedEmployee;

    EmployeeService employeeService = new EmployeeService();

    public RemoveEmployeeWindow(Employee employee) {
        fillTable();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuperAdminWindow adminWindow = new SuperAdminWindow(employee);
                adminWindow.showSuperAdminWindow(employee);
                SwingUtilities.getWindowAncestor(removePanel).dispose();
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
}
