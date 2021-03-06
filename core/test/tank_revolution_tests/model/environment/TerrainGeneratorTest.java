package tank_revolution_tests.model.environment;

import org.junit.Test;
import tankRevolution.framework.terrain.TerrainGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by antonhagermalm on 2017-05-10.
 */
public class TerrainGeneratorTest {

    @Test public void AddAllTest(){
        List<Float> list = new ArrayList<>();
        List<Float> expectedList = new ArrayList<>();
        expectedList.add(1f);
        expectedList.add(2f);
        expectedList.add(3f);

        float[] array = new float[]{1,2,3};
        list = TerrainGenerator.addAllToList(list, array);

        assertTrue(list.equals(expectedList));

    }


}
