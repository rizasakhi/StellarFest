package view.auth.register;

import javafx.scene.layout.VBox;
import view.SFView;
import view.StageManager;

public class RegisterView extends SFView {

    public RegisterView(StageManager stageManager) {
        super(stageManager);

        VBox root = new VBox();

        this.scene = stageManager.getSceneFactory().createScene(root);
    }

}
