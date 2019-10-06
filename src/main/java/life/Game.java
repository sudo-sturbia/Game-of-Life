package life;

/**
 * Main game interface.
 * Used to specify methods to be implemented by the game.
 */
public interface Game {
    /**
     * Find next configuration of the game based on the current configuration.
     */
    void findNextConfiguration();

    /**
     * Count the number of neighbours for each square of the state grid.
     */
    void countNeighbours();
}
