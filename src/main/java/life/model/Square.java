package life.model;

/**
 * Represents a square on the game grid.
 */
public interface Square {
    /**
     * Count the number of square's neighbours and update
     * the value accordingly.
     */
    public void updateNeighbourCount();

    /**
     * Update square's state based on the number of current
     * grid neighbours.
     */
    public void updateState();
}
