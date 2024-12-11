package view.auth.login;

import javafx.scene.layout.VBox;
import view.SFView;
import view.StageManager;

public class LoginView extends SFView {

    public LoginView(StageManager stageManager) {
        super(stageManager);

        VBox root = new VBox();

        this.scene = stageManager.getSceneFactory().createScene(root);
    }

}
