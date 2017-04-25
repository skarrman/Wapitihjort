package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import tank_revolution.Utils.AssetsManager;
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
    private AssetsManager assetsManager;

    protected GraphicalTank(Body body, Id id, Integer angle, float metersToPixels){
        this.tankBody = body;
        this.id = id;
        this.angle = angle;
        this.metersToPixels = metersToPixels;
        assetsManager = AssetsManager.getInstance();
        textureAtlas = assetsManager.getTextureAtlas(id);
    }


    public void draw(Batch batch){
        Array<TextureAtlas.AtlasRegion> atlasRegions = textureAtlas.getRegions();
        TextureAtlas.AtlasRegion atlasRegion = atlasRegions.first();
        Vector2 pos = tankBody.getPosition();
        batch.draw(atlasRegion, (pos.x * metersToPixels) - atlasRegion.getRegionWidth() / 2,
                (pos.y * metersToPixels) - atlasRegion.getRegionHeight() / 4);
    }
}
