package tankRevolution.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import tankRevolution.services.AssetsManager;
import tankRevolution.services.Constants;
import tankRevolution.services.Id;


/**
 * Class holding information about the graphical representation of a tank.
 */
class GraphicalTank {

    /** The tanks body */
    private final Body tankBody;

    /** The sprite that represents the tank */
    private final Sprite sprite;

    /** Constant to convert meters in the model to pixels on the screen */
    private final float pixelsPerMeter = Constants.pixelsPerMeter();

    /**
     * Sets up everything to be able to draw the tank.
     * @param body The tanks body. Necessary to get the tanks position.
     * @param id The player ID to know which sprite belong to this tank.
     * @param width The width of the tank.
     * @param height The height of the tank.
     */
    GraphicalTank(Body body, Id id, float width, float height){
        this.tankBody = body;
        sprite = AssetsManager.getInstance().getSpriteArray(id).first();
        sprite.setSize(width * pixelsPerMeter, height * pixelsPerMeter);
    }

    /**
     * Draws the sprite on the body's position.
     * @param batch The batch to draw the sprite on.
     */
    public void draw(Batch batch){
        Vector2 pos = tankBody.getPosition();
        sprite.setPosition((pixelsPerMeter * pos.x) - sprite.getWidth()/2, (pixelsPerMeter * pos.y) - sprite.getHeight()/2);
        sprite.draw(batch);
    }
}
