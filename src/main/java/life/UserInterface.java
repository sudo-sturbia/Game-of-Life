package life;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Defines UI used in the application.
 */
public class UserInterface extends Application {

    private Game game;
    private Pane[][] layoutPanes;

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

        initializeGame(mainPane);

        // Create main scene
        Scene scene = new Scene(mainPane, 750, 750);

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(UserInterface.class.getResource("UserInterface.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * Initialize main layout pane and set defaults.
     *
     * @param mainPane main layout pane.
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
     *
     * @param mainPane main layout pane.
     */
    private void createInitialLayoutElements(GridPane mainPane) {
        // Initialize layout panes array
        layoutPanes = new Pane[2][2];

        /*
        Create game title inside a StackPane
         */
        StackPane titlePane = new StackPane();
        Text title = new Text("Game of Life");
        title.setId("title-text");

        titlePane.getChildren().add(title);
        titlePane.setPadding(new Insets(40));

        // Add stack pane to main layout
        layoutPanes[0][0] = titlePane;
        mainPane.add(titlePane, 0, 0);

        titlePane.setId("layout-pane");

        /*
        Create a dummy pane to fill the position of game grid
         */
        Pane dummyGrid = new Pane();

        // Add pane to main layout
        layoutPanes[1][0] = dummyGrid;
        mainPane.add(dummyGrid, 0, 1);

        dummyGrid.setId("layout-pane");

        /*
        Create dummy pane to fill position of info pane.
         */
        Pane dummyPane = new Pane();

        // Add pane to main layout
        layoutPanes[1][1] = dummyPane;
        mainPane.add(dummyPane, 1, 1);

        dummyPane.setId("layout-pane");
    }

    /**
     * Prompt user for grid size and initialize a game.
     *
     * @param mainPane main layout pane.
     */
    private void initializeGame(GridPane mainPane) {
        // Create Labels and Text fields to prompt user for initial grid size
        final Label rowLabel = new Label("Rows: ");
        final Label colLabel = new Label("Cols: ");

        final TextField rowField = new TextField("20");
        final TextField colField = new TextField("20");

        rowField.setPrefColumnCount(5);
        colField.setPrefColumnCount(5);

        // Create a grid to layout elements
        final GridPane promptGrid = new GridPane();
        promptGrid.setHgap(5);
        promptGrid.setVgap(5);

        promptGrid.setAlignment(Pos.CENTER);

        promptGrid.addRow(0, rowLabel, rowField);
        promptGrid.addRow(1, colLabel, colField);

        // Add grid to main layout
        layoutPanes[0][1] = promptGrid;
        mainPane.add(promptGrid, 1, 0);

        promptGrid.setId("layout-pane");

        // Create an event handler to initialize game
        EventHandler<ActionEvent> inputHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int rows, cols;

                // Check if user entered valid input
                try {
                    rows = Integer.parseInt(rowField.getText());
                    cols = Integer.parseInt(colField.getText());
                }
                catch (Exception e) {
                    // Set number of rows and columns to default value
                    rows = cols = 20;
                }

                // Replace Text fields with static labels
                final Label rowSizeLabel = new Label(Integer.toString(rows));
                final Label colSizeLabel = new Label(Integer.toString(cols));

                promptGrid.getChildren().remove(rowField);
                promptGrid.getChildren().remove(colField);

                promptGrid.add(rowSizeLabel, 1, 0);
                promptGrid.add(colSizeLabel, 1, 1);

                UserInterface.this.findInitialConfiguration(mainPane, rows, cols);
            }
        };

        // Assign event handler to Text fields
        rowField.setOnAction(inputHandler);
        colField.setOnAction(inputHandler);
    }

    /**
     * Create a grid of a given size and prompt user for initial configuration.
     *
     * @param mainPane main layout pane.
     * @param rows number of rows in grid.
     * @param cols number of columns in grid.
     */
    private void findInitialConfiguration(GridPane mainPane, int rows, int cols) {
        final StackPane sideInfo = new StackPane();
        sideInfo.setId("layout-pane");

        // Create VBox to hold side info
        final VBox vbox = new VBox(50);

        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        // Create info label and start button
        final Label instructions = new Label("click a cell to change its state.");
        final Button start = new Button("start");

        instructions.setWrapText(true);

        vbox.getChildren().addAll(instructions, start);
        sideInfo.getChildren().add(vbox);

        // Remove dummy pane from layout and replace with side info
        mainPane.getChildren().remove(layoutPanes[1][1]);

        layoutPanes[1][1] = sideInfo;
        mainPane.add(sideInfo, 1, 1);

        // Create a grid pane of given size
        GridPane gameGrid = new GridPane();

        // Set grid gaps
        gameGrid.setVgap(1);
        gameGrid.setHgap(1);

        // Create column and row constraints
        double colPercentage = 100.0 / cols;
        double rowPercentage = 100.0 / rows;

        ColumnConstraints[] colConstraints = new ColumnConstraints[cols];
        RowConstraints[] rowConstraints = new RowConstraints[rows];

        for (int i = 0; i < cols; i++)
        {
            colConstraints[i] = new ColumnConstraints();
            colConstraints[i].setPercentWidth(colPercentage);
        }

        for (int i = 0; i < rows; i++)
        {
            rowConstraints[i] = new RowConstraints();
            rowConstraints[i].setPercentHeight(rowPercentage);
        }

        gameGrid.getColumnConstraints().addAll(colConstraints);
        gameGrid.getRowConstraints().addAll(rowConstraints);

        // Remove dummy pane and replace it with grid
        mainPane.getChildren().remove(layoutPanes[1][0]);

        layoutPanes[1][0] = gameGrid;
        mainPane.add(gameGrid, 0, 1);
    }
}
