package life;

import java.util.Arrays;

/**
 * Main game class.
 * Represents game board and performs game operations.
 */
public class GameOfLife implements Game {
    private  Square[][] grid;
    private boolean[][] statesOfSquares;     // An array to keep track of Squares' states

    private boolean isStaticConfig;
    private boolean[][] previousStatesOfSquares;

    /**
     * Game constructor
     *
     * @param gridStates array containing the state of each Square on the grid.
     */
    public GameOfLife(boolean[][] gridStates)
    {
        final int ROWS = gridStates.length;
        final int COLS = gridStates[0].length;

        grid = new Square[ROWS][COLS];
        statesOfSquares = new boolean[ROWS][COLS];
        previousStatesOfSquares = new boolean[ROWS][COLS];

        for (int i = 0; i < ROWS; i ++)
        {
            for (int j = 0; j < COLS; j++)
            {
                statesOfSquares[i][j] = gridStates[i][j];

                grid[i][j] = new Square(i, j, statesOfSquares);
            }
        }
    }

    /**
     * Represents a square on the game grid.
     */
    public static class Square {
        // Position of the square on the grid
        private int x;
        private int y;
        private int numberOfNeighbours;

        private boolean[][] stateArray;   // Reference to states array

        /**
         * Square constructor.
         *
         * @param row specifies Square's row.
         * @param col specifies Square's column.
         * @param stateOfSquares a reference to states array.
         */
        Square (int row, int col, boolean[][] stateOfSquares) {
            this.x = col;
            this.y = row;

            this.stateArray = stateOfSquares;
        }

        /**
         * @return numberOfNeighbours.
         */
        int getNumberOfNeighbours() {
            return this.numberOfNeighbours;
        }

        /**
         * Changes state of Square based on number of neighbours.
         */
        void changeState() {
            if (this.stateArray[y][x])
            {
                if (this.numberOfNeighbours < 2 || this.numberOfNeighbours > 3)
                {
                    this.stateArray[y][x] = false;
                }
            }
            else
            {
                if (this.numberOfNeighbours == 3)
                {
                    this.stateArray[y][x] = true;
                }
            }
        }

        /**
         * Find number of neighbours for the square.
         */
        void countSquareNeighbours() {
            final int GRID_ROWS = stateArray.length;
            final int GRID_COLS = stateArray[0].length;

            // Reset number of neighbours to zero
            this.numberOfNeighbours = 0;

            // Calculate start and end positions of neighbours while avoiding edges
            int startOfRows, startOfCols, endOfRows, endOfCols;

            startOfRows = Math.max(this.y - 1, 0);
            startOfCols = Math.max(this.x - 1, 0);

            endOfRows = Math.min(this.y + 1, GRID_ROWS - 1);
            endOfCols = Math.min(this.x + 1, GRID_COLS - 1);

            // Iterate through Square's neighbours
            for (int i = startOfRows; i <= endOfRows; i++)
            {
                for (int j = startOfCols; j <= endOfCols; j++)
                {
                    if (!(i == this.y && j == this.x) && stateArray[i][j])
                    {
                        this.numberOfNeighbours++;
                    }
                }
            }
        }
    }

    /**
     * @return playing grid.
     */
    public Square[][] getGrid() {
        return this.grid;
    }

    /**
     * @return true if configuration doesn't change, false otherwise
     */
    public boolean isStatic() {
        return this.isStaticConfig;
    }

    /**
     * Find next configuration of the game based on the current configuration.
     *
     * @return array containing new states.
     */
    public boolean[][] findNextConfiguration() {
        // Save previous configuration
        for (int i = 0, rows = this.statesOfSquares.length; i < rows; i++)
        {
            this.previousStatesOfSquares[i] = Arrays.copyOf(this.statesOfSquares[i], this.statesOfSquares[i].length);
        }

        // Find new configuration
        this.countNeighbours();
        for (Square[] row : this.grid)
        {
            for (Square square : row)
            {
                square.changeState();
            }
        }

        // Compare current and previous configurations
        if (Arrays.deepEquals(this.statesOfSquares, this.previousStatesOfSquares))
        {
            this.isStaticConfig = true;
        }

        return this.statesOfSquares;
    }

    /**
     * Count the number of neighbours for each square of the state grid.
     */
    private void countNeighbours() {
        for (Square[] row : this.grid)
        {
            for (Square square : row)
            {
                square.countSquareNeighbours();
            }
        }
    }
}
