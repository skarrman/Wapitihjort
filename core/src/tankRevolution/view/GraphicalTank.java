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
 * Class holding information about the graphical representation of a tank.
 */
public class GraphicalTank {

    private final Body tankBody;
    private final Array<Sprite> sprites;
    private final float pixelsPerMeter = Constants.pixelsPerMeter();

    GraphicalTank(Body body, Id id, float width, float height){
        this.tankBody = body;
        sprites = AssetsManager.getInstance().getSpriteArray(id);
        setSpriteDimensions(width, height);
    }


    public void draw(Batch batch){
        Sprite sprite = sprites.get(0);
        Vector2 pos = tankBody.getPosition();
        sprite.setPosition((pixelsPerMeter * pos.x) - sprite.getWidth()/2, (pixelsPerMeter * pos.y) - sprite.getHeight()/2);
        sprite.draw(batch);
    }

    private void setSpriteDimensions(float width, float height){
        for(Sprite s : sprites){
            s.setSize(width * pixelsPerMeter, height * pixelsPerMeter);
        }
    }
}
