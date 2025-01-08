package com.kenanbabicipia.example.controller;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Style {

    private static final Font defaultTitleFont = new Font("Inter", Font.PLAIN, 22);
    private static final Font defaultLabelFont = new Font("Inter", Font.PLAIN, 12);
    private static final Font italicFont = new Font("Inter", Font.ITALIC, 12);
    private static final Font boldFont = new Font("Inter", Font.BOLD, 12);
    private static final Font iconFont = new Font("Inter", Font.PLAIN, 96);


    public static void setDefaultTheme(){
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put( "Button.arc", 999 );
            UIManager.put( "TextComponent.arc", 999 );
            UIManager.put( "Component.arc", 999 );
            UIManager.put("Button.borderWidth", 2);
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf");
            e.printStackTrace();
        }
    }

    public static void switchTheme(ActionEvent e, Component frame) {
        try {
            if (UIManager.getLookAndFeel() instanceof FlatLightLaf) {
                FlatDarkLaf.setup();
                UIManager.put("Label.foreground", Color.WHITE);
                UIManager.put("Button.foreground", Color.WHITE);
                UIManager.put("TextField.foreground", Color.WHITE);
                UIManager.put("Table.foreground", Color.WHITE);
                UIManager.put("JComboBox.foreground", Color.WHITE);
                UIManager.put("TableHeader.foreground", Color.WHITE);
            } else {
                FlatLightLaf.setup();
                UIManager.put("Label.foreground", Color.BLACK);
                UIManager.put("Button.foreground", Color.BLACK);
                UIManager.put("TextField.foreground", Color.BLACK);
                UIManager.put("Table.foreground", Color.BLACK);
                UIManager.put("JComboBox.foreground", Color.BLACK);
                UIManager.put("TableHeader.foreground", Color.BLACK);
            }

            SwingUtilities.updateComponentTreeUI(frame);
            resetColors(frame);
            frame.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setIconFont(JLabel label){
        label.setFont(iconFont);
    }

    private static void resetColors(Component component) {
        if (component instanceof JLabel) {
            component.setForeground(UIManager.getColor("Label.foreground"));
        } else if (component instanceof JButton) {
            component.setForeground(UIManager.getColor("Button.foreground"));
        } else if (component instanceof JTextField) {
            component.setForeground(UIManager.getColor("TextField.foreground"));
        } else if (component instanceof JTextField) {
            component.setForeground(UIManager.getColor("Table.foreground"));
        } else if (component instanceof JTextField) {
            component.setForeground(UIManager.getColor("JComboBox.foreground"));
        } else if (component instanceof JTextField) {
            component.setForeground(UIManager.getColor("TableHeader.foreground"));
        }


        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                resetColors(child);
            }
        }
    }

//    public static void setManagerIconDarkTheme(JLabel labelActivity, JLabel labelReport, JLabel labelRequest){
//        labelActivity.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\dataReport.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
//        labelRequest.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\dataReport.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
//        labelReport.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\dataReportLight.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
//    }
//
//    public static void setManagerIconLightTheme(JLabel labelActivity, JLabel labelReport, JLabel labelRequest){
//        labelActivity.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\activity.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
//        labelRequest.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\request.png").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
//        labelReport.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Kenan\\IdeaProjects\\EmployeeAttendanceSystemMySQL\\src\\assets\\dataReport.jpg").getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
//    }


    public static void setDefaultTitleFont(JComponent component){
        component.setFont(defaultTitleFont);
    }

    public static void setDefaultLabelFont(JComponent component){
        component.setFont(defaultLabelFont);
    }

    public static void setItalicFont(JComponent component){
        component.setFont(italicFont);
    }

    public static void setBoldFont(JComponent component){
        component.setFont(boldFont);
    }

    public static void setTextColor(JComponent component, Color color) {
        component.setForeground(color);
    }

    public static void centerAlignTable(JTable table){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}
