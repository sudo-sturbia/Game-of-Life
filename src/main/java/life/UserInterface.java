package life;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Defines UI used in the application.
 */
public class UserInterface extends Application {
    // Application's main layout pane
    private BorderPane mainPane;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes program.
     * @param primaryStage application's main stage.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create panes used in the application
        mainPane = new BorderPane();

        createTitle();

        // Find initial grid
        // ...

        // Create main scene
        Scene scene = new Scene(mainPane, 500, 500);

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(UserInterface.class.getResource("UserInterface.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * Create game title.
     */
    private void createTitle() {
        // Create title text and insert in top of border pane
        StackPane topPane = new StackPane();
        Text title = new Text("Game of Life");
        title.setId("title-text");

        topPane.getChildren().add(title);
        topPane.setPadding(new Insets(40));

        // Add stack pane to top part of main pane
        mainPane.setTop(topPane);
    }
}
