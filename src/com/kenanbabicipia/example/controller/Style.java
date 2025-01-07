package com.kenanbabicipia.example.controller;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Style {

    private static final Font defaultTitleFont = new Font("Inter", Font.PLAIN, 22);
    private static final Font defaultLabelFont = new Font("Inter", Font.PLAIN, 12);
    private static final Font italicFont = new Font("Inter", Font.ITALIC, 12);
    private static final Font boldFont = new Font("Inter", Font.BOLD, 12);

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

    public static void switchTheme(ActionEvent e){
        try {
            if (UIManager.getLookAndFeel() instanceof FlatLightLaf) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }

            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((JButton) e.getSource()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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
}
