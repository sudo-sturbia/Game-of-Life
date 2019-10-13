package life;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Defines UI used in the application.
 */
public class UserInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes program.
     *
     * @param primaryStage application's main stage.
     */
    @Override
    public void start(Stage primaryStage) {

        // Create and initialize program's main layout
        final GridPane mainPane = new GridPane();
        initMainLayout(mainPane);

        createInitialLayoutElements(mainPane);

        // Create main scene
        Scene scene = new Scene(mainPane, 750, 750);

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(UserInterface.class.getResource("UserInterface.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * Initialize main layout pane and set defaults.
     */
    private void initMainLayout(GridPane mainPane) {

        mainPane.setId("main-pane");

        // Set padding and gaps
        mainPane.setPadding(new Insets(20));
        mainPane.setVgap(10);
        mainPane.setHgap(10);

        // Set column constraints
        // Column 1: %75, Column 2: %25
        ColumnConstraints colConstraint1 = new ColumnConstraints();
        ColumnConstraints colConstraint2 = new ColumnConstraints();

        colConstraint1.setPercentWidth(75);
        colConstraint2.setPercentWidth(25);

        mainPane.getColumnConstraints().addAll(colConstraint1, colConstraint2);

        // Set row constraints
        // Row 1: %25, Row 2: %75
        RowConstraints rowConstraint1 = new RowConstraints();
        RowConstraints rowConstraint2 = new RowConstraints();

        rowConstraint1.setPercentHeight(25);
        rowConstraint2.setPercentHeight(75);

        mainPane.getRowConstraints().addAll(rowConstraint1, rowConstraint2);
    }

    /**
     * Create initial layout elements to be displayed before game start.
     */
    private void createInitialLayoutElements(GridPane mainPane) {

        /*
        Create game title inside a StackPane
         */
        StackPane titlePane = new StackPane();
        Text title = new Text("Game of Life");
        title.setId("title-text");

        titlePane.getChildren().add(title);
        titlePane.setPadding(new Insets(40));

        // Add stack pane to top corner of main pane
        mainPane.add(titlePane, 0, 0);

        /*
        Create initial game grid
         */
        GridPane initialGrid = new GridPane();

        // Set grid gaps
        initialGrid.setVgap(1);
        initialGrid.setHgap(1);

        // Create a default 20 x 20 grid of equal columns and rows
        // Set constraints
        ColumnConstraints[] colConstraints = new ColumnConstraints[20];
        RowConstraints[] rowConstraints = new RowConstraints[20];

        for (int i = 0; i < 20; i++)
        {
            colConstraints[i] = new ColumnConstraints();
            colConstraints[i].setPercentWidth(5);          // Each column represents 2% of the grid
        }

        for (int i = 0; i < 20; i++)
        {
            rowConstraints[i] = new RowConstraints();
            rowConstraints[i].setPercentHeight(5);          // Each row represents 2% of the grid
        }

        initialGrid.getColumnConstraints().addAll(colConstraints);
        initialGrid.getRowConstraints().addAll(rowConstraints);

        mainPane.add(initialGrid, 0, 1);

        /*
        Create initial iterations label inside of a StackPane
         */
        StackPane iterationsPane = new StackPane();
        Label iterations = new Label("Iteration: 0");

        iterationsPane.getChildren().add(iterations);

        mainPane.add(iterationsPane, 1, 1);
    }
}
