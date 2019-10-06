package life;

/**
 * Main game class.
 * Represents game board and performs game operations.
 */
public class GameOfLife {
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
    class Square {
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
         * Changes state of Square.
         */
        void changeState() {
            stateOfLife = !stateOfLife;
        }

        /**
         * Get number of neighbours of Square.
         */
        int getNumberOfNeighbours() {
            return this.numberOfNeighbours;
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

            // Loop through neighbours of the Square.
            for (int i = this.y - 1; i < this.y + 1; i++)
            {
                for (int j = this.x - 1; j < this.x + 1; j++)
                {
                    if (i >= 0 && i <= GRID_ROWS && j >= 0 && j <= GRID_COLS && !(i == y && j == x))
                    {
                        if (grid[i][j].stateOfLife)
                        {
                            this.numberOfNeighbours++;
                        }
                    }
                }
            }
        }
    }
}
