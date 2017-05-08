package tankRevolution.framework.terrain;

import com.badlogic.gdx.physics.box2d.Body;
import com.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;

import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-04.
 */
public interface ITerrainHandler {
    void update();
    void explode(Body projectileBody, int blastRadius);
}
