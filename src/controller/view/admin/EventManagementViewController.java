package controller.view.admin;

import controller.event.EventController;
import javafx.collections.ObservableList;
import model.Event;

public class EventManagementViewController {

    public static void loadEvents(ObservableList<Event> events) {
        events.addAll(EventController.getAll());
    }

}
