package view.admin;

import controller.view.admin.EventManagementViewController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Event;
import util.DateUtil;
import util.StringUtil;
import view.SFView;
import view.StageManager;

public class EventManagementView extends SFView {

    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private TableView<Event> eventTable;

    public EventManagementView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();

        this.prepareView(root);
        EventManagementViewController.loadEvents(events);

        this.windowTitle = "Manage Events";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        BorderPane borderPane = (BorderPane) root;

        this.eventTable = createEventTable();
        borderPane.setCenter(eventTable);

        this.eventTable.setOnMouseClicked(event -> {
            Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                showEventDetailsWindow(selectedEvent);
            }
        });
    }

    private TableView<Event> createEventTable() {
        TableView<Event> tableView = new TableView<>();

        TableColumn<Event, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<Event, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

        TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
        nameColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(DateUtil.formatDate(cellData.getValue().getDate(), DateUtil.DATE_FORMAT)));

        TableColumn<Event, String> locationColumn = new TableColumn<>("Location");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(StringUtil.truncate(cellData.getValue().getLocation(), 200)));

        TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(StringUtil.truncate(cellData.getValue().getDescription(), 200)));

        tableView.getColumns().addAll(idColumn, nameColumn, dateColumn, locationColumn, descriptionColumn);
        tableView.setItems(events);

        return tableView;
    }

    private void showEventDetailsWindow(Event event) {
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Event Details");

        VBox detailsContainer = new VBox(10);
        detailsContainer.setPadding(new Insets(20));

        detailsContainer.getChildren().addAll(
                createDetailRow("ID:", String.valueOf(event.getId())),
                createDetailRow("Name:", event.getName()),
                createDetailRow("Date:", DateUtil.formatDate(event.getDate(), DateUtil.DATE_FORMAT)),
                createDetailRow("Location:", event.getLocation()),
                createDetailRow("Description:", event.getDescription())
        );

        Scene detailsScene = new Scene(detailsContainer, 400, 300);
        detailsStage.setScene(detailsScene);

        detailsStage.show();
    }

    private HBox createDetailRow(String labelText, String contentText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-min-width: 100px;");
        Label content = new Label(contentText);
        content.setWrapText(true);

        HBox row = new HBox(10);
        row.getChildren().addAll(label, content);
        return row;
    }
}
