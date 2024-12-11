package view.auth.login;

import datastore.UserDatastore;
import driver.Connect;
import driver.Results;
import enums.Role;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.user.User;
import model.user.impl.EOUser;
import model.user.impl.GuestUser;
import model.user.impl.VendorUser;
import util.AlertUtil;
import view.SFView;
import view.StageManager;
import view.auth.register.RegisterView;

import java.sql.ResultSet;

public class LoginView extends SFView {

    private TextField emailInput;
    private PasswordField passwordInput;

    public LoginView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Login";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        formContainer.getChildren().add(titleLabel);

        HBox emailRow = this.createEmailInput();
        formContainer.getChildren().add(emailRow);

        HBox passwordRow = this.createPasswordInput();
        formContainer.getChildren().add(passwordRow);

        Button registerButton = this.createLoginButton();
        formContainer.getChildren().add(registerButton);

        Label loginRedirectLabel = this.createRegisterRedirectLabel();
        formContainer.getChildren().add(loginRedirectLabel);

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);
    }

    private Button createLoginButton() {
        Button registerButton = new Button("Login");
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        registerButton.setPrefWidth(200);

        registerButton.setOnMouseClicked(e -> {
            String email = this.emailInput.getText();
            String password = this.passwordInput.getText();

            if (email.isEmpty()) {
                AlertUtil.showError("Email is required", "Please enter your email");
                return;
            }

            if (password.isEmpty()) {
                AlertUtil.showError("Password is required", "Please enter your password");
                return;
            }

            User user;
            if ((user = this.loginUser(email, password)) == null) {
                AlertUtil.showError("Login failed", "Invalid email or password");
                return;
            }

            this.stageManager.switchScene(SFView.getViewNameOf(user.getHomeView()));
        });

        return registerButton;
    }

    private Label createRegisterRedirectLabel() {
        Label registerRedirectLabel = new Label("Don't have an account? Register");
        registerRedirectLabel.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 14px;");

        registerRedirectLabel.setOnMouseClicked(e -> {
            this.stageManager.switchScene(SFView.getViewNameOf(RegisterView.class));
        });

        registerRedirectLabel.setOnMouseEntered(e -> {
            registerRedirectLabel.setStyle("-fx-text-fill: #1976D2; -fx-font-size: 14px; -fx-underline: true;");
        });

        return registerRedirectLabel;
    }

    private HBox createEmailInput() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Email");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.emailInput = new TextField();
        row.getChildren().add(this.emailInput);

        return row;
    }

    private HBox createPasswordInput() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER);

        Label label = new Label("Password");
        label.setMinWidth(100);
        row.getChildren().add(label);

        this.passwordInput = new PasswordField();
        row.getChildren().add(this.passwordInput);

        return row;
    }

    private User loginUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Results results = Connect.getInstance().executeQuery(query, email, password)) {
            ResultSet set = results.getResultSet();
            if (set.next()) {
                long id = set.getLong("id");
                String username = set.getString("username");
                String roleString = set.getString("role");

                Role role = Role.getRole(roleString);
                assert role != null;

                User user;
                switch (role) {
                    case EVENT_ORGANIZER:
                        user = new EOUser(id, email, username);
                        break;
                    case VENDOR:
                        user = new VendorUser(id, email, username);
                        break;
                    case GUEST:
                        user = new GuestUser(id, email, username);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + role);
                }

                UserDatastore.getInstance().setCurrentUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
