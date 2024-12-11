package view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.SFView;
import view.StageManager;

public class AdminHomeView extends SFView {

    public AdminHomeView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);

        this.windowTitle = "Home - Admin";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(20));
        formContainer.setAlignment(Pos.CENTER);

        Button manageEventButton = this.createManageEventButton();
        formContainer.getChildren().add(manageEventButton);

        Button manageUserButton = this.createManageUserButton();
        formContainer.getChildren().add(manageUserButton);

        BorderPane borderPane = (BorderPane) root;
        borderPane.setCenter(formContainer);
    }

    private Button createManageEventButton() {
        Button button = new Button("Manage Events");
        button.setPrefWidth(200);

        button.setOnMouseClicked(e -> {
            //
        });

        return button;
    }

    private Button createManageUserButton() {
        Button button = new Button("Manage Users");
        button.setPrefWidth(200);

        button.setOnMouseClicked(e -> {
            //
        });

        return button;
    }

}
