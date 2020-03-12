package life.model;

import java.util.Arrays;

/**
 * GameOfLife implements Game interface an is used
 * to simulate the game of life.
 */
public class GameOfLife implements Game {
    private Square[][] grid; // A grid of Squares representing current game position.

    private boolean[][] gridStates;         // Current states of Squares collected in an array.
    private boolean[][] previousGridStates; // Previous states of Squares.

    /**
     * GameOfLife constructor.
     *
     * @param states An array containing initial square states.
     */
    public GameOfLife(boolean[][] states) {
        final int ROWS = states.length;
        final int COLS = states[0].length;

        // Initialize arrays.
        this.grid = new Square[ROWS][COLS];
        this.gridStates = new boolean[ROWS][COLS];
        this.previousGridStates = new boolean[ROWS][COLS];

        // Copy given states and create Square objects.
        for (int i = 0; i < ROWS; i ++)
        {
            for (int j = 0; j < COLS; j++)
            {
                this.gridStates[i][j] = states[i][j];
                this.grid[i][j] = new GameSquare(j, i, this.gridStates);
            }
        }
    }

    /**
     * Compare current grid configuration with previous configuration.
     *
     * @return true if Squares' states didn't change during previous
     * step, false otherwise.
     */
    @Override
    public boolean isStatic() {
        return Arrays.deepEquals(this.gridStates, this.previousGridStates);
    }

    /**
     * Find new game configuration based on the current states and
     * update Squares grid accordingly.
     *
     * @return a boolean array containing new states.
     */
    @Override
    public boolean[][] nextConfig() {
        this.currentToPrevious();

        this.updateNeighbourCount();
        this.updateState();

        return this.gridStates;
    }

    /**
     * Recalculate number of neighbours of the entire grid.
     */
    private void updateNeighbourCount() {
        for (Square[] row : this.grid)
        {
            for (Square square : row)
            {
                square.updateNeighbourCount();
            }
        }
    }

    /**
     * Update the state of the entire grid.
     */
    private void updateState() {
        for (Square[] row : this.grid)
        {
            for (Square square : row)
            {
                square.updateState();
            }
        }
    }

    /**
     * Write current Square states to previous states.
     */
    private void currentToPrevious() {
        for (int i = 0, rows = this.gridStates.length; i < rows; i++)
        {
            this.previousGridStates[i] = Arrays.copyOf(this.gridStates[i], this.gridStates[i].length);
        }
    }
}
