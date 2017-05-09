package tankRevolution.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;
import tankRevolution.utils.Id;


/**
 * Created by simonkarrman on 2017-04-24.
 */
public class GraphicalTank {

    private Body tankBody;
    private Id id;
    private Integer angle;
    private float pixelsPerMeter;
    private Array<Sprite> sprites;

    protected GraphicalTank(Body body, Id id, Integer angle, float pixelsPerMeter, float width, float height){
        this.tankBody = body;
        this.id = id;
        this.angle = angle;
        this.pixelsPerMeter = pixelsPerMeter;
        sprites = AssetsManager.getInstance().getSpriteArray(id);
        setSpriteDimensions(width, height);
    }


    public void draw(Batch batch){
        Sprite sprite = sprites.get(0);
        Vector2 pos = tankBody.getPosition();
        sprite.setPosition((pixelsPerMeter * pos.x) - sprite.getWidth()/2, (pixelsPerMeter * pos.y) - sprite.getHeight()/4);
        sprite.setRotation((float)Math.toDegrees(tankBody.getAngle()));
        sprite.draw(batch);
    }

    private void setSpriteDimensions(float width, float height){
        for(Sprite s : sprites){
            s.setSize(2 * width * pixelsPerMeter, 2 * height * pixelsPerMeter);
        }
    }
}
