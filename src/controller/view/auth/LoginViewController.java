package controller.view.auth;

import controller.UserController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.user.User;
import util.AlertUtil;
import view.SFView;
import view.StageManager;
import view.auth.register.RegisterView;

public class LoginViewController {

    public static void handleLogin(TextField emailInput, PasswordField passwordInput) {
        String email = emailInput.getText();
        String password = passwordInput.getText();

        if (email.isEmpty()) {
            AlertUtil.showError("Email is required", "Please enter your email");
            return;
        }

        if (password.isEmpty()) {
            AlertUtil.showError("Password is required", "Please enter your password");
            return;
        }

        User user;
        if ((user = UserController.login(email, password)) == null) {
            AlertUtil.showError("Login failed", "Invalid email or password");
            return;
        }

        StageManager.getInstance().switchScene(SFView.getViewNameOf(user.getHomeView()));
    }

    public static void handleRegisterRedirect() {
        StageManager.getInstance().switchScene(SFView.getViewNameOf(RegisterView.class));
    }


}
