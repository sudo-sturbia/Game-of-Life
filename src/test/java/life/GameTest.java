package life;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test game class.
 */
class GameTest {
    /**
     * Test static patterns.
     */
    @Test
    void testStaticPatterns() {
        Game game;

        boolean[][] firstConfig = {
            {false, false, false, false},
            {false, true,  true,  false},
            {false, true,  true,  false},
            {false, false, false, false}
        };

        game = new GameOfLife(firstConfig);
        for (int i = 0; i < 5; i++)
        {
            assertStates(firstConfig, game.nextConfig());
        }

        boolean[][] secondConfig = {
            {false, false, false, false, false},
            {false, true,  true,  false, false},
            {false, true,  false, true,  false},
            {false, false, true,  false, false},
            {false, false, false, false, false}
        };

        game = new GameOfLife(secondConfig);
        for (int i = 0; i < 10; i++)
        {
            assertStates(secondConfig, game.nextConfig());
        }
    }

    @Test
    void testIsStatic() {
        boolean[][] config = {
            {false, false, false, false},
            {false, true,  true,  false},
            {false, true,  true,  false},
            {false, false, false, false}
        };

        Game game = new GameOfLife(config);

        for (int i = 0; i < 10; i++)
        {
            game.nextConfig();
            assertTrue(game.isStatic(), "isStatic produces false when true is expected.");
        }
    }

    /**
     * Test oscillating patterns.
     */
    @Test
    void testOscillatingPattern() {
        Game game;
        Random random = new Random();

        boolean[][][] firstConfigs = {
            {
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, true,  true,  true,  false},
                {false, true,  true,  true,  false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false}
            },
            {
                {false, false, false, false, false, false},
                {false, false, false, true,  false, false},
                {false, true,  false, false, true,  false},
                {false, true,  false, false, true,  false},
                {false, false, true,  false, false, false},
                {false, false, false, false, false, false}
            }
        };

        game = new GameOfLife(firstConfigs[0]);
        for (int i = 0, n = random.nextInt(20); i < n; i++)
        {
            if (i % 2 == 0)
            {
                assertStates(firstConfigs[1], game.nextConfig());
            }
            else
            {
                assertStates(firstConfigs[0], game.nextConfig());
            }
        }

        boolean[][][] secondConfigs = {
            {
                {false, false, false, false, false, false},
                {false, true,  true,  false, false, false},
                {false, true,  true,  false, false, false},
                {false, false, false, true,  true,  false},
                {false, false, false, true,  true,  false},
                {false, false, false, false, false, false}
            },
            {
                {false, false, false, false, false, false},
                {false, true,  true,  false, false, false},
                {false, true,  false, false, false, false},
                {false, false, false, false, true,  false},
                {false, false, false, true,  true,  false},
                {false, false, false, false, false, false}
            }
        };

        game = new GameOfLife(secondConfigs[0]);
        for (int i = 0, n = random.nextInt(20); i < n; i++)
        {
            if (i % 2 == 0)
            {
                assertStates(secondConfigs[1], game.nextConfig());
            }
            else
            {
                assertStates(secondConfigs[0], game.nextConfig());
            }
        }
    }

    /**
     * Asserts that calculated states are not null and are the same
     * as expected states. States are represented using boolean 2d
     * arrays.
     *
     * @param expected Expected states.
     * @param calculated Calculated states.
     */
    void assertStates(boolean[][] expected, boolean[][] calculated) {
        assertNotNull(calculated, "Calculated states array is null.");

        assertEquals(calculated.length, expected.length,
                "Calculated states array has incorrect number of rows.");

        for (int i = 0, rows = expected.length; i < rows; i++)
        {
            assertEquals(calculated[i].length, expected[i].length,
                    "Sub-array " + i + " of calculated states has incorrect length.");

            for (int j = 0, cols = expected[i].length; j < cols; j++)
            {
                assertEquals(calculated[i][j], expected[i][j],
                        "Calculated value at " + i + " , " + j + " is incorrect.");
            }
        }
    }
}
