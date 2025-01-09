package com.kenanbabicipia.view;

import com.kenanbabicipia.controller.Style;
import com.kenanbabicipia.model.Employee;
import com.kenanbabicipia.model.Request;
import com.kenanbabicipia.service.ActivityService;
import com.kenanbabicipia.service.EmployeeService;
import com.kenanbabicipia.service.RequestService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class RequestWindow {
    private JPanel requestPanel;
    private JLabel titleLabel;
    private JTable requestTable;
    private JButton backButton;

    RequestService requestService = new RequestService();
    EmployeeService employeeService = new EmployeeService();

    public RequestWindow(Employee employee){

        fillTable();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerWindow managerWindow = new ManagerWindow(employee);
                managerWindow.showManagerWindow(employee);
                SwingUtilities.getWindowAncestor(requestPanel).dispose();
            }
        });
    }

    private void fillTable(){
        requestTable.setModel(new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return column == 5;
            }
        });

        DefaultTableModel model = (DefaultTableModel) requestTable.getModel();
        model.setColumnIdentifiers(new Object[]{"Request ID","Employee", "Start Date", "End Date", "Description", "Actions"});
        requestTable.setRowHeight(80);
        List<Request> requests = requestService.selectWaitingRequest();

        for(Request request : requests){
            Employee employee = employeeService.selectEmployeeInformation(request.getEmployeeID());
            model.addRow(new Object[]{
                    request.getRequestID(),
                    employee.getFirstName() + " " + employee.getLastName(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getDescription() == null ? "---" : request.getDescription(),
                    ""
            });

        }

        Style.centerAlignTable(requestTable);
        requestTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        requestTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), requestService));

    }

    public void showRequestWindow(Employee employee){
        JFrame frame = new JFrame("Employee Attendance Management System");
        frame.setContentPane(new RequestWindow(employee).requestPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                ActivityService activityService = new ActivityService();
                boolean shouldClose = activityService.closingLogOut(requestPanel, employee.getEmployeeID());
                if(shouldClose){
                    frame.dispose();
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /********************************/
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton approveButton = new JButton("Approve");
        private final JButton denyButton = new JButton("Deny");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            add(approveButton);
            add(denyButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private final JPanel panel = new JPanel();
        private final JButton approveButton = new JButton("Approve");
        private final JButton denyButton = new JButton("Deny");

        private RequestService requestService;

        public ButtonEditor(JCheckBox checkBox, RequestService requestService) {
            super(checkBox);
            this.requestService = requestService;


            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            panel.add(approveButton);
            panel.add(denyButton);

            approveButton.addActionListener(e -> {
                fireEditingStopped();
                handleAction("Approved");
            });
            denyButton.addActionListener(e -> {
                fireEditingStopped();
                handleAction("Denied");
            });
        }

        private void handleAction(String action) {
            int row = requestTable.getSelectedRow();
            if (row >= 0) {
                int requestID = (int) requestTable.getValueAt(row, 0);
                System.out.println("requestID" + requestID);
                if (action.equals("Approved")) {
                    requestService.approveRequest(requestID);
                } else {
                    requestService.denyRequest(requestID);
                }

                fireEditingStopped();
                ((DefaultTableModel) requestTable.getModel()).removeRow(row);

            } else {
                JOptionPane.showMessageDialog(null, "No row selected or invalid row index.");
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

}
