package tank_revolution_tests.model.environment;

import org.junit.Test;
import tankRevolution.framework.terrain.TerrainPartGenerator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by antonhagermalm on 2017-05-11.
 */
public class TerrainPartGeneratorTest {
    @Test
    public void generateHillTest() {

        float[] vertArr = TerrainPartGenerator.generateHill(1, 1, 4, 5);
        float[] expectedVertArr = new float[]{2, 2.38f, 3, 4.61f, 4, 4.61f, 5, 2.38f, 6, 1f};

        for (float f : vertArr) {
            System.out.print(f + " ");
        }
        assertArrayEquals(expectedVertArr, vertArr, 0.1f);
    }

    @Test
    public void generateVallyTest() {
        float[] vertArr = TerrainPartGenerator.generateValley(1, 1, 4, 5);
        float[] expectedVertArr = new float[]{2, -1, 3, -3, 4, -1, 5, 1};


        for (float f : vertArr) {
            System.out.print(f + " ");
        }
    }
}
