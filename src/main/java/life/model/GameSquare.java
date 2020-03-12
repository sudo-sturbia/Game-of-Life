package life.model;

/**
 * GameSquare implements Square interface to be used by
 * a Game object.
 */
public class GameSquare implements Square {
    private int x; // Square's current position.
    private int y;

    private int numberOfNeighbours; // Number of alive neighbours.

    private boolean[][] grid; // A reference to the grid containing the square.

    /**
     * GameSquare constructor.
     *
     * @param x Represents Square's x coordinate.
     * @param y Represents Square's y coordinate.
     * @param grid A reference to the grid containing the square.
     */
    GameSquare(int x, int y, boolean[][] grid) {
        this.x = x;
        this.y = y;
        this.grid = grid;
    }

    /**
     * Count the number of square's neighbours and update
     * the value accordingly.
     */
    @Override
    public void updateNeighbourCount() {
        // Calculate start and end positions of square's neighbours
        // while avoiding edges.
        int rowStart = Math.max(this.y - 1, 0);
        int colStart = Math.max(this.x - 1, 0);

        int rowEnd = Math.min(this.y + 1, grid.length - 1);
        int colEnd = Math.min(this.x + 1, grid.length - 1);

        // Update Square's neighbour count.
        this.numberOfNeighbours = 0;
        for (int i = rowStart; i <= rowEnd; i++)
        {
            for (int j = colStart; j <= colEnd; j++)
            {
                if (!(i == this.y && j == this.x) && this.grid[i][j])
                {
                    this.numberOfNeighbours++;
                }
            }
        }
    }

    /**
     * Update square's state based on the number of current
     * grid neighbours.
     */
    @Override
    public void updateState() {
        if (this.grid[y][x])
        {
            if (this.numberOfNeighbours < 2 || this.numberOfNeighbours > 3)
            {
                this.grid[y][x] = false;
            }
        }
        else
        {
            if (this.numberOfNeighbours == 3)
            {
                this.grid[y][x] = true;
            }
        }
    }
}
