package life;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;

/**
 * Test Square class.
 */
class SquareTest {
    private static GameOfLife testGame;
    private static GameOfLife.Square[][] gameGrid;

    /**
     * Test operations implemented on square.
     */
    @Test
    void testSquareOperations() {
        boolean[][] oldStates = {{true, false, true},
                                 {false, true, false},
                                 {true, false, true}};

        boolean[][] newStates = {{false, true, false},
                                 {true, false, true},
                                 {false, true, false}};

        int[][] numberOfNeighbours = {{1, 3, 1},
                                      {3, 4, 3},
                                      {1, 3, 1}};

        testGame = new GameOfLife(oldStates);
        gameGrid = testGame.getGrid();

        final int ROWS = gameGrid.length;
        final int COLS = gameGrid[0].length;

        // Test number of neighbours
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                gameGrid[i][j].countSquareNeighbours();

                // Test number of neighbours
                assertEquals(gameGrid[i][j].getNumberOfNeighbours(), numberOfNeighbours[i][j]);
            }
        }

        // Test new states
        boolean[][] stateArray = testGame.findNextConfiguration();
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                // Test new state
                assertEquals(stateArray[i][j], newStates[i][j]);
            }
        }
    }

    /**
     * Free memory.
     */
    @AfterAll
    static void freeUsedMemory() {
        testGame = null;
        gameGrid = null;
    }
}
