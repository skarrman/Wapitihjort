package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.lwjgl.Sys;
import tankRevolution.utils.Constants;
import tankRevolution.utils.Vector;

import java.awt.*;
import java.util.List;

/**
 * Created by JakobErlandsson on 2017-05-10.
 */
public class GraphicalTerrain {

    private Array<Polygon> polygons;

    private ShapeRenderer shapeRenderer;

    private Texture texture;

    private Pixmap pixmap;

    private float radius = Constants.pixelsPerMeter()/2;

    public GraphicalTerrain(){
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(0,1,0,1);
        polygons = new Array<Polygon>();
        pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        setPoints();
    }

    public void draw(List<float[]> vertices, boolean isTerrainChanged, Batch batch){
        if(isTerrainChanged){
            polygons.clear();
            for(float[] v : vertices) {
                if(v.length > 2) {
                    Polygon polygon = new Polygon(v);
                    polygons.add(polygon);
                }
            }
            setPoints();
        }
        draw(batch);
    }

    private void draw(Batch batch){
        batch.draw(texture, 0,0);
    }

    private  void setPoints() {
        float pixelsToMeter = Constants.pixelsPerMeter();
        pixmap.setColor(0,1,0,0);
        pixmap.fill();
        for(Polygon polygon : polygons) {
            for (int i = 0; i < 300; i++) {
                for (int j = 0; j < 300; j++) {
                    float x = Constants.getMapWidth() / 300 * i;
                    float y = Constants.getMapWidth() / 300 * j;
                    if (polygon.contains(x, y)) {
                        pixmap.setColor(0,1,0,1);
                        pixmap.fillCircle((int)(x * pixelsToMeter), pixmap.getHeight()-(int)(y * pixelsToMeter), (int)radius);
                    }
                }
            }
        }
        texture = new Texture(pixmap);
    }
}
