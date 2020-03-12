package life.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 * Controller interacts with game Player and ui components
 * to display prompts and updates throughout the game.
 */
public class Controller {
    private Player gamePlayer; // Used to play a game and update game board.

    @FXML
    private GridPane promptGrid; // Used to prompt user for number of rows, cols.

    @FXML
    private StackPane sideInfo; // Side information pane.

    @FXML
    private GridPane gameGrid; // Grid on which the game is displayed.

    @FXML
    private TextField rowField; // Used to prompt user for number of rows.

    @FXML
    private TextField colField; // Used to prompt user for number of cols.

    @FXML
    private Button start; // Start game button.

    @FXML
    private VBox infoBox; // A vertical box that contains side info.

    /**
     * Handles user prompt for column and row sizes.
     *
     * @param actionEvent A user event.
     */
    @FXML
    private void promptHandler(ActionEvent actionEvent) {
        int rows, cols;

        // Check if user entered valid input
        try {
            rows = Math.min(Integer.parseInt(this.rowField.getText()), 50);
            cols = Math.min(Integer.parseInt(this.colField.getText()), 50);
        }
        catch (NumberFormatException e) {
            // Set number of rows and columns to default value
            rows = cols = 20;
        }

        // Replace Text fields with static labels
        Label rowSize = new Label(Integer.toString(rows));
        Label colSize = new Label(Integer.toString(cols));

        this.promptGrid.getChildren().remove(this.rowField);
        this.promptGrid.getChildren().remove(this.colField);

        this.promptGrid.add(rowSize, 1, 0);
        this.promptGrid.add(colSize, 1, 1);

        this.initialConfig(rows, cols);
    }

    /**
     * Prompt user for initial grid configuration.
     *
     * @param rows number of grid rows.
     * @param cols number of grid columns.
     */
    private void initialConfig(int rows, int cols) {
        this.sideInfo.setDisable(false);
        this.gameGrid.setDisable(false);

        this.setConstraints(rows, cols);

        Pane[][] cells = this.initialGrid(rows, cols);

        start.setOnAction(event -> {
            // Remove event handlers
            for (Pane[] row : cells)
            {
                for (Pane pane : row)
                {
                    pane.setOnMouseClicked(null);
                }
            }

            gamePlayer = new Player();
            gamePlayer.playGame(cells, this.infoBox);
        });
    }


    /**
     * Prompt user for initial game grid.
     * Set grid panes accordingly.
     *
     * @param rows number of grid rows.
     * @param cols number of grid columns.
     * @return a 2d array of panes representing grid cells.
     */
    private Pane[][] initialGrid(int rows, int cols) {
        // Create a 2d array of panes to fill the grid
        final Pane[][] cells = new Pane[rows][cols];

        // Insert dead panes in the grid
        gameGrid.setId("");
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                cells[i][j] = new Pane();
                cells[i][j].setId("dead-cell");

                gameGrid.add(cells[i][j], j, i);
            }
        }

        // Handle when a pane is clicked
        EventHandler<MouseEvent> cellClick = event -> {
            Pane whichPane = (Pane) event.getSource();

            if (whichPane.getId().equals("dead-cell"))
            {
                whichPane.setId("live-cell");
            }
            else if (whichPane.getId().equals("live-cell"))
            {
                whichPane.setId("dead-cell");
            }
        };

        for (Pane[] row : cells)
        {
            for (Pane pane : row)
            {
                pane.setOnMouseClicked(cellClick);
            }
        }

        return cells;
    }

    /**
     * Set gameGrid's constraints.
     *
     * @param rows number of grid rows.
     * @param cols number of grid columns.
     */
    private void setConstraints(int rows, int cols) {
        double colPercentage = 100.0 / cols;
        double rowPercentage = 100.0 / rows;

        // Set column and row constraints.
        ColumnConstraints[] colCons = new ColumnConstraints[cols];
        RowConstraints[] rowCons    = new RowConstraints[rows];

        for (int i = 0; i < cols; i++)
        {
            colCons[i] = new ColumnConstraints();
            colCons[i].setPercentWidth(colPercentage);
        }

        for (int i = 0; i < rows; i++)
        {
            rowCons[i] = new RowConstraints();
            rowCons[i].setPercentHeight(rowPercentage);
        }

        this.gameGrid.getColumnConstraints().addAll(colCons);
        this.gameGrid.getRowConstraints().addAll(rowCons);
    }
}
