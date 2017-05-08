package tank_revolution.framework.terrain;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;
import com.quailshillstudio.polygonClippingUtils.UserData;

import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-04.
 */
public interface ITerrainHandler {
    void create();
    void switchGround(List<PolygonBox2DShape> polygonBox2DShapes);
    void update();
    void clippingGround(Body a, Body b, int blastRadius);

}
