package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Array;
import tankRevolution.utils.Constants;

import java.util.List;

/**
 * Class holding information about the graphical representation of the terrain.
 */
public class GraphicalTerrain {

    /** Array containing all polygons that the terrain consist of. */
    private final Array<Polygon> polygons;

    /** The texture that is the graphical representation of the terrain. */
    private Texture texture;

    /** Pixmap purpose is to create the terrain graphically */
    private final Pixmap pixmap;

    /** The radius of the circles that build the terrain */
    private final float radius = Constants.pixelsPerMeter()/2;

    /**
     * Initializing
     */
    GraphicalTerrain(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(0,1,0,1);
        polygons = new Array<Polygon>();
        pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        setPoints();
    }

    /**
     * Checks if the terrain has changed since the last rendering. If it has changed
     * the polygon and pixmap is recalculated.
     * @param vertices The vertices that the terrain polygon consists of.
     * @param isTerrainChanged Tells if the terrain has changed since the last rendering loop.
     * @param batch The batch to draw the terrain on.
     */
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

    /**
     * Draws the texture.
     * @param batch The batch to draw the texture on.
     */
    private void draw(Batch batch){
        batch.draw(texture, 0,0);
    }

    /**
     * Recalculates the pixmap and then sets the texture to the draw the new pixmap.
     */
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
