package life;

/**
 * Main game class.
 * Represents game board and performs game operations.
 */
public class GameOfLife implements Game {
    private Square[][] grid;

    /**
     * Game constructor
     *
     * @param gridStates array containing the state of each Square on the grid.
     */
    public GameOfLife(boolean[][] gridStates)
    {
        grid = new Square[gridStates.length][gridStates[0].length];

        for (int i = 0, rows = gridStates.length; i < rows; i ++)
        {
            for (int j = 0, cols = gridStates[0].length; j < cols; j++)
            {
                grid[i][j] = new Square(i, j, gridStates[i][j], this.grid);
            }
        }
    }

    /**
     * Represents a square on the game grid.
     */
    static class Square {
        // Position of the square on the grid
        private int x;
        private int y;

        private Square[][] grid;
        private boolean stateOfLife;
        private int numberOfNeighbours;

        /**
         * Square constructor.
         *
         * @param row specifies Square's row.
         * @param col specifies Square's column.
         * @param squareState specifies Square's state of life.
         * @param containingGrid reference to the grid which contains the Square.
         */
        Square (int row, int col, boolean squareState, Square[][] containingGrid) {
            this.x = col;
            this.y = row;

            this.grid = containingGrid;
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
                if (this.numberOfNeighbours < 2)
                {
                    this.stateOfLife = false;
                }
                else if (this.numberOfNeighbours > 3)
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
        void countSquareNeighbours() {
            // Check if array is null
            if (grid == null)
            {
                throw new NullPointerException();
            }

            final int GRID_ROWS = grid.length;
            final int GRID_COLS = grid[0].length;

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
                    if (!(i == this.y && j == this.x))
                    {
                        if (grid[i][j].getStateOfLife())
                        {
                            this.numberOfNeighbours++;
                        }
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
     * Find next configuration of the game based on the current configuration.
     */
    public void findNextConfiguration() {
        this.countNeighbours();

        for (Square[] row : this.grid)
        {
            for (Square square : row)
            {
                square.changeState();
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
                square.countSquareNeighbours();
            }
        }
    }
}
