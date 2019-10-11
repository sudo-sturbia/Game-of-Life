package life;

/**
 * Main game interface.
 * Used to specify methods to be implemented by the game.
 */
public interface Game {
    /**
     * Find next configuration of the game based on the current configuration.
     *
     * @return array containing new states.
     */
    boolean[][] findNextConfiguration();
}
