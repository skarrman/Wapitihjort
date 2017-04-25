package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import tank_revolution.Utils.Id;

/**
 * Created by simonkarrman on 2017-04-24.
 */
public class GraphicalTank {

    private Body tankBody;
    private Id id;
    private Integer angle;
    private float metersToPixels;
    private TextureAtlas textureAtlas;

    protected GraphicalTank(Body body, Id id, Integer angle, float metersToPixels){
        this.tankBody = body;
        this.id = id;
        this.angle = angle;
        this.metersToPixels = metersToPixels;
        setUp();
    }

    private void setUp(){
        textureAtlas = new TextureAtlas(Gdx.files.internal("GreenTank.txt"));
    }

    public void draw(Batch batch){
        TextureAtlas.AtlasRegion atlasRegion = textureAtlas.getRegions().first();
        Vector2 pos = tankBody.getPosition();
        batch.draw(atlasRegion, (pos.x * metersToPixels) - atlasRegion.getRegionWidth() / 2,
                (pos.y * metersToPixels) - atlasRegion.getRegionHeight() / 4);
    }
}
