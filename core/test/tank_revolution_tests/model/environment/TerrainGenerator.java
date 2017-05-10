package tank_revolution_tests.model.environment;

import org.junit.Test;
import tankRevolution.framework.terrain.TerrainHandler;

/**
 * Created by antonhagermalm on 2017-05-10.
 */
public class TerrainGenerator {
    @Test public void TerrainTest(){
        tankRevolution.framework.terrain.TerrainGenerator terrainGenerator = new tankRevolution.framework.terrain.TerrainGenerator();
        float[] fl;
        fl = terrainGenerator.getSinusArray();

        for(int i = 0; i < fl.length; i++) {
            System.out.println(fl[i] + " ");
        }

    }
}
