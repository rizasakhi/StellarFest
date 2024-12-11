package controller.view.auth;

import controller.UserController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.AlertUtil;
import view.SFView;
import view.StageManager;
import view.auth.login.LoginView;

public class RegisterViewController {

    public static void handleRegister(TextField emailInput, TextField usernameInput, TextField passwordInput, ComboBox<String> roleSelector) {
        String email = emailInput.getText();
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String role = roleSelector.getValue();

        if (email.isEmpty()) {
            AlertUtil.showError("Email is required", "Please enter your email");
            return;
        }

        if (UserController.isUnique("email", email)) {
            AlertUtil.showError("Email is already taken", "Please enter a different email");
            return;
        }

        if (username.isEmpty()) {
            AlertUtil.showError("Username is required", "Please enter your username");
            return;
        }

        if (UserController.isUnique("username", username)) {
            AlertUtil.showError("Username is already taken", "Please enter a different username");
            return;
        }

        if (password.isEmpty()) {
            AlertUtil.showError("Password is required", "Please enter your password");
            return;
        }

        if (password.length() < 5) {
            AlertUtil.showError("Password is too short", "Please enter a password with at least 5 characters");
            return;
        }

        if (role == null) {
            AlertUtil.showError("Role is required", "Please select a role");
            return;
        }

        boolean registered = UserController.registerUser(email, username, password, role);
        if (!registered) {
            AlertUtil.showError("Registration failed", "An error occurred while registering your account");
            return;
        }

        AlertUtil.showInfo("Registration successful", "Your account has been registered successfully", () -> {
            StageManager.getInstance().switchScene(SFView.getViewNameOf(LoginView.class));
        });
    }

    public static void handleLoginRedirect() {
        StageManager.getInstance().switchScene(SFView.getViewNameOf(LoginView.class));
    }

}
