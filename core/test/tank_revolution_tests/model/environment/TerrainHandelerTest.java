package tank_revolution_tests.model.environment;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.quailshillstudio.polygonClippingUtils.GroundFixture;
import org.junit.Test;
import tankRevolution.framework.terrain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 */
public class TerrainHandelerTest {
    @Test
    public void test() {
        TerrainHandler terrainHandler = new TerrainHandler(new World(new Vector2(4f, 4f), false));
        GroundFixture[] polyVerts = new GroundFixture[4];

        for (int i = 0; i < 4; i++) {
            List<float[]> verts = new ArrayList<float[]>();
            for (int j = 0; j < 4; j++) {
                float[] points;
                points = new float[]{1, 2, 3};
                verts.add(points);
            }
            GroundFixture polyVert = new GroundFixture(verts);
            polyVerts[i] = polyVert;
        }

        List<float[]> newPolyVerts = terrainHandler.defenciveCopyVerticeList(polyVerts);

        System.out.print("OG is: ");
        for (int k = 0; k < polyVerts.length; k++) {
            for (int i = 0; i < polyVerts[k].getVerts().size(); i++) {
                for (int j = 0; j < polyVerts[k].getVerts().get(i).length; j++) {
                    System.out.print(polyVerts[k].getVerts().get(i)[j]  + " ");
                }
            }
        }
        System.out.println("");
        System.out.print("new is: ");
        for(int i = 0; i < newPolyVerts.size(); i++){
            for(int j = 0; j < newPolyVerts.get(i).length; j++){
                System.out.print(newPolyVerts.get(i)[j] + " ");
            }
        }
    }
}
