package com.kenanbabicipia.view;

import com.kenanbabicipia.model.Employee;
import com.kenanbabicipia.service.ActivityService;
import com.kenanbabicipia.service.RequestService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.Integer.parseInt;

public class VacationRequest {
    private JPanel vacationPanel;
    private JButton backButton;
    private JComboBox startDayField;
    private JComboBox startMonthField;
    private JComboBox startYearField;
    private JComboBox endDayField;
    private JComboBox endMonthField;
    private JComboBox endYearField;
    private JButton sendRequestButton;
    private JLabel successLabel;
    private JTextArea descriptionField;
    private JLabel descriptionLabel;

    public VacationRequest(Employee employee){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeWindow employeeWindow = new EmployeeWindow(employee);
                employeeWindow.showEmployeeWindow(employee);
                SwingUtilities.getWindowAncestor(vacationPanel).dispose();
            }
        });
        generateDates(startDayField, startMonthField, startYearField);
        generateDates(endDayField, endMonthField, endYearField);
        sendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestService requestService = new RequestService();
                String startDate = getStartDate();
                String endDate = getEndDate();
                if(!checkDates(getStartDayField(), getStartMonthField(), getStartYearField(), getEndDayField(), getEndMonthField(), getEndYearField())){
                    successLabel.setText("Enter valid dates!");
                    successLabel.setVisible(true);
                    return;
                }
                String description = descriptionField.getText().length() > 0 ? descriptionField.getText() : null;
                requestService.insertNewRequest(employee.getEmployeeID(), startDate, endDate, description);
                successLabel.setText("Vacation request successfully created.");
                successLabel.setVisible(true);
                chanegeStateForm(false);
            }
        });
    }

    private String getStartDate(){
        String fullDate = (String)startDayField.getSelectedItem() + "." + (String)startMonthField.getSelectedItem() + "." + (String)startYearField.getSelectedItem();
        return fullDate;
    }

    private String getEndDate(){
        String fullDate = (String)endDayField.getSelectedItem() + "." + (String)endMonthField.getSelectedItem() + "." + (String)endYearField.getSelectedItem();
        return fullDate;
    }

    private void chanegeStateForm(boolean state){
        startDayField.setEnabled(state);
        startMonthField.setEnabled(state);
        startYearField.setEnabled(state);
        endYearField.setEnabled(state);
        endMonthField.setEnabled(state);
        endDayField.setEnabled(state);
        descriptionField.setEnabled(state);
        sendRequestButton.setEnabled(state);
    }

    public void showVacationRequest(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new VacationRequest(employee).vacationPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(vacationPanel, employee.getEmployeeID());
                if(shouldClose){
                    frame.dispose();
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void generateDates(JComboBox dayBox, JComboBox monthBox, JComboBox yearBox){
        for (int i = 1; i <= 31; i++) {
            dayBox.addItem(String.valueOf(i));
        }

        for (int i = 1; i <= 12; i++) {
            monthBox.addItem(String.valueOf(i));
        }

        for (int i = 2025; i <= 2030; i++) {
            yearBox.addItem(String.valueOf(i));
        }
    }

    private boolean checkDates(String startDay, String startMonth, String startYear, String endDay, String endMonth, String endYear){
        if(parseInt(startMonth) > parseInt(endMonth)) return false;
        if(parseInt(startMonth) == parseInt(endMonth) && parseInt(startDay) == parseInt(endDay)) return false;
        if(parseInt(startMonth) == parseInt(endMonth)){
            if(parseInt(startDay) > parseInt(endDay)) return false;
        }
        if(parseInt(startYear) > parseInt(endYear)) return false;
        return true;
    }

    public String getStartDayField() {
        return (String)startDayField.getSelectedItem();
    }

    public String getStartMonthField() {
        return (String)startMonthField.getSelectedItem();
    }

    public String getStartYearField() {
        return (String) startYearField.getSelectedItem();
    }

    public String getEndDayField() {
        return (String)endDayField.getSelectedItem();
    }

    public String getEndMonthField() {
        return (String)endMonthField.getSelectedItem();
    }

    public String getEndYearField() {
        return (String)endYearField.getSelectedItem();
    }
}
