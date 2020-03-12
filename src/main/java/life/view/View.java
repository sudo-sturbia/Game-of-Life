package life.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * View is responsible for loading ui's components such
 * as fxml, css, and font.
 */
public class View extends Application {
    public static ScheduledExecutorService scheduledExecutorService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // Load fxml, css, and font
        Parent root = FXMLLoader.load(View.class.getResource("layout.fxml"));

        Font.loadFont(View.class.getResource("Righteous-Regular.ttf").toExternalForm(), 10);

        Scene scene = new Scene(root, 750, 750);
        scene.getStylesheets().add(View.class.getResource("style.css").toExternalForm());

        // Set scene and initialize application.
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shutdown scheduled executor when program is closed.
     */
    @Override
    public void stop() {
        scheduledExecutorService.shutdown();
    }
}
