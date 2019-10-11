package life;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Test game class.
 */
class GameTest {
    private static GameOfLife testGame;
    private static boolean[][] testGridStates;

    /**
     * Free object memory before each method.
     */
    @BeforeEach
    void freeMemory() {
        testGame = null;
        testGridStates = null;
    }

    /**
     * Test static patterns.
     */
    @Test
    void testStaticPattern() {
        boolean[][] staticConfiguration = {{false, false, false, false},
                                           {false, true, true, false},
                                           {false, true, true, false},
                                           {false, false, false, false}};

        testGame = new GameOfLife(staticConfiguration);

        // Find next configuration
        testGridStates = testGame.findNextConfiguration();

        // Test states
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                assertEquals(testGridStates[i][j], staticConfiguration[i][j]);
            }
        }
    }

    /**
     * Test an oscillating pattern.
     */
    @Test
    void testOscillatingPattern() {
        boolean[][] firstConfiguration = {{false, false, false, false, false, false},
                                          {false, false, false, false, false, false},
                                          {false, false, true, true, true, false},
                                          {false, true, true, true, false, false},
                                          {false, false, false, false, false, false},
                                          {false, false, false, false, false, false}};

        boolean[][] secondConfiguration = {{false, false, false, false, false, false},
                                           {false, false, false, true, false, false},
                                           {false, true, false, false, true, false},
                                           {false, true, false, false, true, false},
                                           {false, false, true, false, false, false},
                                           {false, false, false, false, false, false}};

        testGame = new GameOfLife(firstConfiguration);

        // Test first configuration
        testGridStates = testGame.findNextConfiguration();

        // Test states
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                assertEquals(secondConfiguration[i][j], testGridStates[i][j]);
            }
        }

        // Test second configuration
        testGridStates = testGame.findNextConfiguration();

        // Test states
        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                assertEquals(firstConfiguration[i][j], testGridStates[i][j]);
            }
        }
    }
}
