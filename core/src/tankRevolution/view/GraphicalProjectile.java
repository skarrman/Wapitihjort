package tankRevolution.view;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import tankRevolution.services.AssetsManager;
import tankRevolution.utils.Constants;

/**
 * Handles the graphical representation of a projectile that is flying.
 */
public class GraphicalProjectile {

    /** The projectiles body */
    private final Body body;

    /** The projectiles sprite. I will represent the body graphically */
    private final Sprite sprite;

    /**
     * Initializes the sprite.
     * @param body The projectile's body. Necessary to know where to draw the sprite.
     */
    GraphicalProjectile(Body body){
        this.body = body;
        sprite = AssetsManager.getInstance().getProjectileSprite();
    }

    /**
     * Draw the projectile sprite on the position of the projectile's body.
     * @param batch The batch to draw the sprite on.
     */
    public void draw(Batch batch){
        Vector2 projectilePos = new Vector2(body.getPosition().x, body.getPosition().y);
        sprite.setPosition(projectilePos.x * Constants.pixelsPerMeter() - sprite.getWidth() / 2,
                            projectilePos.y * Constants.pixelsPerMeter() - sprite.getHeight() / 2);
        sprite.draw(batch);

    }
}
