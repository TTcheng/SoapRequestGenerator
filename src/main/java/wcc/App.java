package wcc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Pane root = loader.load();
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setTitle("WsdlRequestGenerator");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setOnCloseRequest(event -> {
            // soapui leaves non daemon threads behind (a java.util.Timer and, in some
            // paths, AWT threads) as soon as it has imported a WSDL, so the JVM would
            // keep running after the window is gone. End it explicitly.
            System.exit(0);
        });
        primaryStage.show();
    }
}
