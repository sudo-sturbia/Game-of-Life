package life.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import life.model.Game;
import life.model.GameOfLife;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Defines UI used in the application.
 */
public class UserInterface extends Application {

    private Pane[][] layoutPanes;
    private ScheduledExecutorService scheduledExecutorService;

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
        // Load font
        Font.loadFont(UserInterface.class.getResource("Righteous-Regular.ttf").toExternalForm(), 10);

        // Create and initialize program's main layout
        final GridPane mainPane = new GridPane();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

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
     * Clean up after program is finished.
     * Shuts down scheduled executor that runs thee program.
     */
    @Override
    public void stop() {
        scheduledExecutorService.shutdown();
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

        colConstraint1.setPercentWidth(80);
        colConstraint2.setPercentWidth(20);

        mainPane.getColumnConstraints().addAll(colConstraint1, colConstraint2);

        // Set row constraints
        // Row 1: %25, Row 2: %75
        RowConstraints rowConstraint1 = new RowConstraints();
        RowConstraints rowConstraint2 = new RowConstraints();

        rowConstraint1.setPercentHeight(20);
        rowConstraint2.setPercentHeight(80);

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
        final Label colLabel = new Label("Columns: ");

        final TextField rowField = new TextField("20");
        final TextField colField = new TextField("20");

        rowField.setPrefColumnCount(3);
        colField.setPrefColumnCount(3);

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
        sideInfo.setPadding(new Insets(20));

        // Create VBox to hold side info
        final VBox vbox = new VBox(25);

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
        gameGrid.setVgap(4);
        gameGrid.setHgap(4);

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

        // Create an array of cells (Panes) to fill the grid and a boolean array to hold states
        final Pane[][] gridPanes = new Pane[rows][cols];

        // Initialize Panes and insert them in grid
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                gridPanes[i][j] = new Pane();
                gridPanes[i][j].setId("dead-cell");

                gameGrid.add(gridPanes[i][j], j, i);
            }
        }

        // Create an event handler for panes
        EventHandler<MouseEvent> clickCell = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Pane whichPane = (Pane) event.getSource();

                if (whichPane.getId().equals("dead-cell"))
                {
                    whichPane.setId("live-cell");
                }
                else if (whichPane.getId().equals("live-cell"))
                {
                    whichPane.setId("dead-cell");
                }
            }
        };

        // Assign event to grid cells
        for (Pane[] panes : gridPanes)
        {
            for (Pane pane : panes)
            {
                pane.setOnMouseClicked(clickCell);
            }
        }

        // Create event for start button
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserInterface.this.playGame(mainPane, gridPanes);
            }
        });
    }

    /**
     * Create and play a game.
     *
     * @param mainPane main layout pane.
     * @param gridPanes grid of panes used to display game configurations.
     */
    private void playGame(GridPane mainPane, Pane[][] gridPanes) {
        // Remove event handlers
        for (Pane[] panes : gridPanes)
        {
            for (Pane pane : panes)
            {
                pane.setOnMouseClicked(null);
            }
        }

        // Create a boolean array containing cell states
        int rows = gridPanes.length;
        int cols = gridPanes[0].length;

        boolean[][] cellStates = new boolean[rows][cols];

        // Set states of cells
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                // Live cell
                if (gridPanes[i][j].getId().equals("live-cell"))
                {
                    cellStates[i][j] = true;
                }
                else if (gridPanes[i][j].getId().equals("dead-cell"))
                {
                    cellStates[i][j] = false;
                }
            }
        }

        // Create a Game object to find configurations
        final Game game = new GameOfLife(cellStates);

        // Create number of iterations label
        final Label iterationsName = new Label("Iteration: ");
        final Label iterationsNumber = new Label();

        SimpleIntegerProperty integerProperty = new SimpleIntegerProperty(0);
        iterationsNumber.textProperty().bind(integerProperty.asString());

        // Replace info pane with number of iterations
        GridPane infoGrid = new GridPane();
        infoGrid.setId("layout-pane");
        infoGrid.setAlignment(Pos.CENTER);

        infoGrid.add(iterationsName, 0, 0);
        infoGrid.add(iterationsNumber, 1, 0);

        mainPane.getChildren().remove(layoutPanes[1][1]);

        layoutPanes[1][1] = infoGrid;
        mainPane.add(infoGrid, 1, 1);

        // Use executor to display configurations with a fixed delay
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                boolean[][] nextConfiguration = game.nextConfig();

                // Show configuration
                for (int i = 0, rows = nextConfiguration.length; i < rows; i++)
                {
                    for (int j = 0, cols = nextConfiguration[i].length; j < cols; j++)
                    {
                        if (nextConfiguration[i][j])
                        {
                            gridPanes[i][j].setId("live-cell");
                        }
                        else
                        {
                            gridPanes[i][j].setId("dead-cell");
                        }
                    }
                }

                // Check if board is static
                if (game.isStatic())
                {
                    scheduledExecutorService.shutdown();
                }
                else
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            integerProperty.set(integerProperty.getValue() + 1);
                        }
                    });
                }
            }
        }, 0, 400, TimeUnit.MILLISECONDS);
    }
}
