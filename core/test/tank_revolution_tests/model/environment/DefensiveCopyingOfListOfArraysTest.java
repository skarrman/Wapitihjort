package tank_revolution_tests.model.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.quailshillstudio.polygonClippingUtils.GroundFixture;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import tankRevolution.framework.terrain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 */
public class DefensiveCopyingOfListOfArraysTest {
/*    @Test
    public void test() {
        TerrainHandler terrainHandler = new TerrainHandler(new World(new Vector2(4f, 4f), false), "Burning Desert Wolf");
        //GroundFixture[] polyVerts = new GroundFixture[4];
        //®List<float[]> oldPolyVerts = new ArrayList<float[]>();
        List<float[]> oldPolyVerts = new ArrayList<float[]>();


            for (int j = 0; j < 4; j++) {
                float[] points;
                points = new float[]{1, 2, 3};
                oldPolyVerts.add(points);
            }

        List<float[]> newPolyVerts = terrainHandler.defenciveCopyVerticesList(oldPolyVerts);

        for(int i = 0; i < oldPolyVerts.size(); i++){
            for(int j = 0; j < oldPolyVerts.get(i).length; j++){
                assertTrue(newPolyVerts.get(i)[j] == oldPolyVerts.get(i)[j]);
            }
        }
    }*/
}
