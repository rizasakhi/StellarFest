package view.admin;

import driver.Connect;
import enums.Role;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.user.User;
import model.user.impl.AdminUser;
import model.user.impl.EOUser;
import model.user.impl.VendorUser;
import util.AlertUtil;
import view.SFView;
import view.StageManager;

public class UserManagementView extends SFView {

    private final ObservableList<User> users = FXCollections.observableArrayList();
    private TableView<User> userTable;

    public UserManagementView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Manage Users";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        BorderPane borderPane = (BorderPane) root;

        this.userTable = createUserTable();
        borderPane.setCenter(userTable);


        Button deleteButton = new Button("Delete Selected User");
        deleteButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 14px;");
        deleteButton.setOnAction(event -> deleteSelectedUser());

        HBox buttonContainer = new HBox(deleteButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        borderPane.setBottom(buttonContainer);
    }

    private TableView<User> createUserTable() {
        TableView<User> tableView = new TableView<>();

        TableColumn<User, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUsername()));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(this.getRole(cellData.getValue())));

        tableView.getColumns().addAll(idColumn, emailColumn, usernameColumn, roleColumn);
        tableView.setItems(users);

        return tableView;
    }

    private void deleteSelectedUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            AlertUtil.showError("No user selected", "Please select a user to delete");
            return;
        }

        try {
            int update = Connect.getInstance().executeUpdate("DELETE FROM users WHERE id = " + selectedUser.getId());
            if (update <= 0) {
                AlertUtil.showError("Error deleting user", "No user was deleted");
                return;
            }

            users.remove(selectedUser);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showError("Error deleting user", "An error occurred while deleting the user");
        }
    }

    private String getRole(User user) {
        Role role;
        if (user instanceof AdminUser) {
            role = Role.ADMIN;
        } else if (user instanceof EOUser) {
            role = Role.EVENT_ORGANIZER;
        } else if (user instanceof VendorUser) {
            role = Role.VENDOR;
        } else {
            role = Role.GUEST;
        }

        return role.getRole();
    }
}
