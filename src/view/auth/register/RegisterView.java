package view.auth.register;

import driver.Connect;
import driver.Results;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.AlertUtil;
import view.SFView;
import view.StageManager;
import view.auth.login.LoginView;

import java.util.List;

public class RegisterView extends SFView {

    private TextField emailInput;
    private TextField usernameInput;
    private PasswordField passwordInput;
    private ComboBox<String> roleSelector;

    public RegisterView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Register";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));

        Label titleLabel = new Label("Register");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        formContainer.getChildren().add(titleLabel);

        HBox emailRow = this.createEmailInput();
        formContainer.getChildren().add(emailRow);

        HBox usernameRow = this.createUsernameInput();
        formContainer.getChildren().add(usernameRow);

        HBox passwordRow = this.createPasswordInput();
        formContainer.getChildren().add(passwordRow);

        HBox roleRow = this.createRoleSelector();
        formContainer.getChildren().add(roleRow);

        Button registerButton = this.createRegisterButton();
        formContainer.getChildren().add(registerButton);

        Button loginRedirectButton = this.createLoginRedirectButton();
        formContainer.getChildren().add(loginRedirectButton);

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);
    }

    private Button createRegisterButton() {
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        registerButton.setPrefWidth(200);

        registerButton.setOnMouseClicked(e -> {
            String email = this.emailInput.getText();
            String username = this.usernameInput.getText();
            String password = this.passwordInput.getText();
            String role = this.roleSelector.getValue();

            if (email.isEmpty()) {
                AlertUtil.showError("Email is required", "Please enter your email");
                return;
            }

            if (this.isUnique("email", email)) {
                AlertUtil.showError("Email is already taken", "Please enter a different email");
                return;
            }

            if (username.isEmpty()) {
                AlertUtil.showError("Username is required", "Please enter your username");
                return;
            }

            if (this.isUnique("username", username)) {
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

            boolean registered = this.registerUser(email, username, password, role);
            if (!registered) {
                AlertUtil.showError("Registration failed", "An error occurred while registering your account");
                return;
            }

            AlertUtil.showInfo("Registration successful", "Your account has been registered successfully", () -> {
                this.stageManager.switchScene(SFView.getViewNameOf(LoginView.class));
            });
        });

        return registerButton;
    }

    private Button createLoginRedirectButton() {
        Button loginRedirectButton = new Button("Already have an account? Login");
        loginRedirectButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");
        loginRedirectButton.setPrefWidth(200);

        loginRedirectButton.setOnMouseClicked(e -> {
            this.stageManager.switchScene(SFView.getViewNameOf(LoginView.class));
        });

        return loginRedirectButton;
    }

    private HBox createEmailInput() {
        HBox row = new HBox(10);
        Label label = new Label("Email");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.emailInput = new TextField();
        row.getChildren().add(this.emailInput);

        return row;
    }

    private HBox createUsernameInput() {
        HBox row = new HBox(10);
        Label label = new Label("Username");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.usernameInput = new TextField();
        row.getChildren().add(this.usernameInput);

        return row;
    }

    private HBox createPasswordInput() {
        HBox row = new HBox(10);
        Label label = new Label("Password");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.passwordInput = new PasswordField();
        row.getChildren().add(this.passwordInput);

        return row;
    }

    private HBox createRoleSelector() {
        HBox row = new HBox(10);
        Label label = new Label("Role");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.roleSelector = new ComboBox<>();
        this.roleSelector.getItems().addAll(List.of("Event Organizer", "Vendor", "Guest"));
        this.roleSelector.setPromptText("Select a role");
        row.getChildren().add(this.roleSelector);

        return row;
    }

    private boolean isUnique(String column, String value) {
        String query = "SELECT * FROM users WHERE " + column + " = ?";
        try (Results results = Connect.getInstance().executeQuery(query, value)) {
            return !results.getResultSet().next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean registerUser(String email, String username, String password, String role) {
        String query = "INSERT INTO users (email, username, password, role) VALUES (?, ?, ?, ?)";
        try {
            Connect.getInstance().executeUpdate(query, email, username, password, role);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
