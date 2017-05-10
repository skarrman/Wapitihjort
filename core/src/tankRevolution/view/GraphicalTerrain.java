package tankRevolution.view;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

/**
 * Created by JakobErlandsson on 2017-05-10.
 */
public class GraphicalTerrain {

    private static ShapeRenderer shapeRenderer;

    public GraphicalTerrain(){
    }

    public static void draw(float[] vertices){
        shapeRenderer.polygon(vertices);
    }


}
