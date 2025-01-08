package com.kenanbabicipia.example.view;

import com.kenanbabicipia.example.model.Activity;
import com.kenanbabicipia.example.model.Employee;
import com.kenanbabicipia.example.model.PDFGenerator;
import com.kenanbabicipia.example.service.ActivityService;
import com.kenanbabicipia.example.service.EmployeeService;

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
    private JLabel errorLabel;
    private final ActivityService activityService = new ActivityService();
    private final EmployeeService employeeService = new EmployeeService();
    private Employee selectedEmployee;

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
                DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
                model.setRowCount(0);
                if(employeeIDField.getText().isEmpty()){
                    selectedLabel.setText(selectedLabel.getText() + "No Employee ID entered! Try again!");

                    generateReportInPDFButton.setEnabled(false);
                    return;
                }
                int employeeID = parseInt(employeeIDField.getText());

                selectedEmployee = employeeService.selectEmployeeInformation(employeeID);
                if(selectedEmployee != null){
                    selectedLabel.setText(selectedLabel.getText() + selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() + ", Role: " + selectedEmployee.getRole());
                    if(!monthField.getText().isEmpty())
                    {
                        if(parseInt(monthField.getText()) > 12){
                            generateReportInPDFButton.setEnabled(false);
                            errorLabel.setVisible(true);
                            errorLabel.setText("Invalid month!");
                            return;
                        }

                    }
                    errorLabel.setVisible(false);
                    fillReportTable(selectedEmployee.getEmployeeID(), monthField.getText());
                    generateReportInPDFButton.setEnabled(true);
                }
                else{
                    selectedLabel.setText(selectedLabel.getText() + "There is no Employee with entered ID! Try again!");
                    errorLabel.setVisible(false);
                }

            }
        });
        generateReportInPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PDFGenerator pdfGenerator = new PDFGenerator();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save PDF");

                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".pdf";
                    pdfGenerator.generatePDF(activityTable, filePath, selectedEmployee.getFirstName(), selectedEmployee.getLastName(), selectedEmployee.getRole(), monthField.getText());
                }
            }
        });
    }

    private void fillReportTable(int employeeID, String month){
        activityTable.setModel(new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        });
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new Object[]{"Employee", "Date", "Log-in Time", "Log-out Time", "Work Time"});
        List<Activity> activities;

        if(month.isEmpty())
            activities = activityService.selectAllActivities(employeeID);
        else
            activities = activityService.selectAllActivities(employeeID, month);

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
    }

    public void showReportWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new ReportWindow(employee).reportPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
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
