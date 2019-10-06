package life;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

class SquareTest {
    private static GameOfLife testGame;

    /**
     * Initialize testGame.
     */
    @BeforeAll
    static void initializeGame() {
        boolean[][] testStates = {{true, false, true},
                                  {false, true, false},
                                  {true, false, true}};

        testGame = new GameOfLife(testStates);
    }

    /**
     * Test operations implemented on square.
     */
    @Test
    void testSquareOperations() {
        GameOfLife.Square[][] gameGrid = testGame.getGrid();

        boolean[][] correctNewStates = {{false, true, false},
                                 {true, false, true},
                                 {false, true, false}};

        int[][] correctNumberOfNeighbours = {{1, 3, 1},
                                             {3, 4, 3},
                                             {1, 3, 1}};

        for (int i = 0; i < gameGrid.length; i++)
        {
            for (int j = 0; j < gameGrid[0].length; j++)
            {
                gameGrid[i][j].countSquareNeighbours();

                // Test number of neighbours
                assertEquals(gameGrid[i][j].getNumberOfNeighbours(), correctNumberOfNeighbours[i][j]);
            }
        }

        for (int i = 0; i < gameGrid.length; i++)
        {
            for (int j = 0; j < gameGrid[0].length; j++)
            {
                gameGrid[i][j].changeState();

                // Test new state
                assertEquals(gameGrid[i][j].getStateOfLife(), correctNewStates[i][j]);
            }
        }
    }

    /**
     * Free memory.
     */
    @AfterAll
    static void freeUsedMemory() {
        testGame = null;
    }
}
