package life.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import life.model.Game;
import life.model.GameOfLife;
import life.view.View;

import java.util.concurrent.TimeUnit;

/**
 * Player initiates and plays a Game and updates board's
 * state through out the game.
 * Player is invoked by the controller class and doesn't
 * handle any input from the user.
 */
public class Player {
    private Game game; // Game of Life object.

    /**
     * Plays a game using the configuration (extracted from the panes)
     * chosen by the user.
     *
     * @param cells a 2d array of panes that represent grid cells.
     * @param infoBox a vertical box that holds side information.
     */
    void playGame(Pane[][] cells, VBox infoBox) {
        game = new GameOfLife(this.extractStates(cells));

        // Set up iterations label
        SimpleIntegerProperty integerProperty = this.createIterationsLabel(infoBox);

        // Play a game and update the board through out
        View.scheduledExecutorService.scheduleWithFixedDelay(() -> {
            boolean[][] nextConfig = game.nextConfig();

            // Show configuration
            for (int i = 0, rows = nextConfig.length; i < rows; i++)
            {
                for (int j = 0, cols = nextConfig[i].length; j < cols; j++)
                {
                    if (nextConfig[i][j])
                    {
                        cells[i][j].setId("live-cell");
                    }
                    else
                    {
                        cells[i][j].setId("dead-cell");
                    }
                }
            }

            // Check if board is static
            if (game.isStatic())
            {
                View.scheduledExecutorService.shutdown();
            }
            else
            {
                // Update iterations label
                Platform.runLater(() -> integerProperty.set(integerProperty.getValue() + 1));
            }
        }, 0, 400, TimeUnit.MILLISECONDS);
    }

    /**
     * Extract boolean states from the panes that represent
     * the game grid.
     *
     * @param cells a 2d array of panes that represent grid cells.
     * @return a boolean 2d array containing cell states.
     */
    private boolean[][] extractStates(Pane[][] cells) {
        // Create a boolean array containing cell states
        final int ROWS = cells.length;
        final int COLS = cells[0].length;

        boolean[][] states = new boolean[ROWS][COLS];
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                // Check cell's state
                if (cells[i][j].getId().equals("live-cell"))
                {
                    states[i][j] = true;
                }
                else if (cells[i][j].getId().equals("dead-cell"))
                {
                    states[i][j] = false;
                }
            }
        }

        return states;
    }

    /**
     * Create number of iterations label in side info pane.
     *
     * @return an integer property representing the number of iterations.
     */
    private SimpleIntegerProperty createIterationsLabel(VBox infoBox) {
        final Label name = new Label("Iteration: ");
        final Label number = new Label();

        SimpleIntegerProperty integerProperty = new SimpleIntegerProperty(0);
        number.textProperty().bind(integerProperty.asString());

        infoBox.getChildren().clear();
        infoBox.getChildren().addAll(name, number);

        return integerProperty;
    }
}
