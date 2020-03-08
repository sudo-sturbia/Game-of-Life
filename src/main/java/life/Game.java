package life;

/**
 * Game represents a game of life, and specifies methods to
 * be implemented.
 */
public interface Game {
    /**
     * @return true if board didn't change during previous
     * step, returns false otherwise
     */
    boolean isStatic();

    /**
     * Find new game configuration based on the current states.
     *
     * @return a boolean array containing new states.
     */
    boolean[][] nextConfig();
}
