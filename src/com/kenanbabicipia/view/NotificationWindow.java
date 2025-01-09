package com.kenanbabicipia.view;

import com.kenanbabicipia.controller.Style;
import com.kenanbabicipia.model.Employee;
import com.kenanbabicipia.model.Request;
import com.kenanbabicipia.service.RequestService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class NotificationWindow {
    private JPanel notificationPanel;
    private JTable notificationTable;
    private final JFrame frame;
    private static NotificationWindow instance;
    RequestService requestService = new RequestService();


    public NotificationWindow(Employee employee){
        this.frame = new JFrame("Notification");
        fillTable(employee.getEmployeeID());
    }
    public static void showNotificationWindow(Employee employee){
        if (instance == null || !instance.frame.isVisible()) {
            instance = new NotificationWindow(employee);
            instance.frame.setContentPane(instance.notificationPanel);
            instance.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            instance.frame.setResizable(false);
            instance.frame.pack();
            instance.frame.setLocationRelativeTo(null);
            instance.frame.setVisible(true);
        }else{
            instance.frame.dispose();
        }
    }

    public void fillTable(int employeeID){
        notificationTable.setModel(new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        });
        notificationTable.setRowHeight(50);
        DefaultTableModel model = (DefaultTableModel) notificationTable.getModel();
        model.setColumnIdentifiers(new Object[]{"Type", "Date", "Status"});

        List<Request> requests = requestService.selectEmployeeRequest(employeeID);
        String type = "Resolved request";
        for(Request request : requests){
            model.addRow(new Object[]{
                    type,
                    request.getStartDate() + " - " +request.getEndDate(),
                    request.getStatus()
            });
        }

        Style.centerAlignTable(notificationTable);
    }
}
