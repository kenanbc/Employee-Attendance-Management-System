import com.kenanbabicipia.controller.Style;
import com.kenanbabicipia.view.LoginForm;


public class Main {
    public static void main(String[] args) {

        Style.setDefaultTheme();

        LoginForm window = new LoginForm();
        window.showLoginPanel();

    }
}