package wcc.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AlertMaker {
    public static void showErrorMessage(Exception ex, String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error occured");
        alert.setHeaderText(title);
        alert.setContentText(content);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    /**
     * Shows a modal looking dialog on top of the given {@link StackPane} and blurs the
     * content behind it. The dialog is closed by any of the given buttons.
     */
    public static void showMaterialDialog(StackPane root, Node nodeToBeBlurred, List<Button> controls, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);

        List<Button> buttons = new ArrayList<>(controls);
        if (buttons.isEmpty()) {
            buttons.add(new Button("Okay"));
        }

        Label heading = new Label(header);
        heading.getStyleClass().add("dialog-heading");
        heading.setMaxWidth(Double.MAX_VALUE);

        VBox layout = new VBox();
        layout.getStyleClass().add("dialog-layout");
        layout.setMaxWidth(420);
        layout.setMaxHeight(Region.USE_PREF_SIZE);
        layout.getChildren().add(heading);

        if (body != null && !body.isEmpty()) {
            Label bodyLabel = new Label(body);
            bodyLabel.setWrapText(true);
            bodyLabel.getStyleClass().add("dialog-body");
            layout.getChildren().add(bodyLabel);
        }

        StackPane overlay = new StackPane(layout);
        overlay.getStyleClass().add("dialog-overlay");

        HBox actions = new HBox();
        actions.getStyleClass().add("dialog-actions");
        for (Button button : buttons) {
            button.getStyleClass().add("dialog-button");
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                root.getChildren().remove(overlay);
                nodeToBeBlurred.setEffect(null);
            });
        }
        actions.getChildren().addAll(buttons);
        layout.getChildren().add(actions);

        root.getChildren().add(overlay);
        nodeToBeBlurred.setEffect(blur);
    }

}
