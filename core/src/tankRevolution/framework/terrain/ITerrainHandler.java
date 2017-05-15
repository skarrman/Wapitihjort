package tankRevolution.framework.terrain;

import com.badlogic.gdx.physics.box2d.Body;
import com.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;

import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-04.
 */
public interface ITerrainHandler {
    /**
     * Called each time game updates, 60 times/second
     */
    void update();

    /**
     * Called when a projectile hits the ground or a tank. Determines how the ground should look after
     * impact.
     * @param projectileBody The flying projectile being checked
     * @param blastRadius Blast Radius of the projectile being checked
     */
    void explode(Body projectileBody, int blastRadius);
    List<float[]> getVertices();

}
