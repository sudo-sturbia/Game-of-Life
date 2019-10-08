package life;

/**
 * Main game class.
 * Represents game board and performs game operations.
 */
public class GameOfLife implements Game {
    private  Square[][] grid;
    private boolean[][] statesOfSquares;     // An array to keep track of Squares' states

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

        for (int i = 0; i < ROWS; i ++)
        {
            for (int j = 0; j < COLS; j++)
            {
                grid[i][j] = new Square(i, j, gridStates[i][j]);
                statesOfSquares[i][j] = gridStates[i][j];
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

        private boolean stateOfLife;
        private int numberOfNeighbours;

        /**
         * Square constructor.
         *
         * @param row specifies Square's row.
         * @param col specifies Square's column.
         * @param squareState specifies Square's state of life.
         */
        Square (int row, int col, boolean squareState) {
            this.x = col;
            this.y = row;

            this.stateOfLife = squareState;
        }

        /**
         * @return stateOfLife.
         */
        boolean getStateOfLife() {
            return stateOfLife;
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
            if (this.stateOfLife)
            {
                if (this.numberOfNeighbours < 2 || this.numberOfNeighbours > 3)
                {
                    this.stateOfLife = false;
                }
            }
            else
            {
                if (this.numberOfNeighbours == 3)
                {
                    this.stateOfLife = true;
                }
            }
        }

        /**
         * Find number of neighbours for the square.
         */
        void countSquareNeighbours(boolean[][] grid) {
            final int GRID_ROWS = grid.length;
            final int GRID_COLS = grid[0].length;

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
                    if (!(i == this.y && j == this.x) && grid[i][j])
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
     * @return boolean array states of squares.
     */
    public boolean[][] getStates() {
        return this.statesOfSquares;
    }

    /**
     * Find next configuration of the game based on the current configuration.
     */
    public void findNextConfiguration() {
        this.countNeighbours();

        final int ROWS = this.grid.length;
        final int COLS = this.grid[0].length;

        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                this.grid[i][j].changeState();
                this.statesOfSquares[i][j] = this.grid[i][j].getStateOfLife();
            }
        }
    }

    /**
     * Count the number of neighbours for each square of the state grid.
     */
    public void countNeighbours() {
        for (Square[] row : this.grid)
        {
            for (Square square : row)
            {
                square.countSquareNeighbours(this.statesOfSquares);
            }
        }
    }
}
