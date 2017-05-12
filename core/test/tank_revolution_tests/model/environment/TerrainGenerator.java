package tank_revolution_tests.model.environment;

import org.junit.Test;
import tankRevolution.framework.terrain.TerrainPartGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 */
public class TerrainGenerator {
    @Test public void TerrainTest(){
        tankRevolution.framework.terrain.TerrainGenerator terrainGenerator = new tankRevolution.framework.terrain.TerrainGenerator();
        float[] fl;
        fl = terrainGenerator.getTerrainVertexArray();

        for(int i = 0; i < fl.length; i++) {
            System.out.println(fl[i] + " ");
        }

    }
    @Test public void AddAllTest(){
        List<Float> list = new ArrayList<Float>();


    }
}
