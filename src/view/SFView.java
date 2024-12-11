package view;

import javafx.scene.Scene;

public abstract class SFView {

    protected final StageManager stageManager;
    protected Scene scene;
    protected String windowTitle;

    protected SFView(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public static String getViewNameOf(Class<? extends SFView> viewClass) {
        return viewClass.getSimpleName();
    }

    public String getViewName() {
        return this.getClass().getSimpleName();
    }

    public String getWindowTitle() {
        if (this.windowTitle == null) {
            return this.getViewName();
        }

        return this.windowTitle;
    }

    public Scene getScene() {
        assert this.scene != null : "Scene for " + this.getClass().getSimpleName() + " is null";
        return this.scene;
    }
}
