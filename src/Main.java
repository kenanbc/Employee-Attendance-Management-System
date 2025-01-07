import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.kenanbabicipia.example.controller.Style;
import com.kenanbabicipia.example.view.LoginForm;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        Style.setDefaultTheme();

        LoginForm window = new LoginForm();
        window.showLoginPanel();

    }
}