package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.StageManager;
import view.auth.login.LoginView;
import view.auth.register.RegisterView;

public class Main extends Application {

    private StageManager stageManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stageManager = new StageManager(stage);

        this.registerViews();

        this.stageManager.switchScene(LoginView.class);
    }

    private void registerViews() {
        this.stageManager.addScene(new LoginView(this.stageManager));
        this.stageManager.addScene(new RegisterView(this.stageManager));
    }

}
