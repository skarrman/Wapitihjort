package tankRevolution.view;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

/**
 * Handles the graphical representation of a projectile that is flying.
 */
public class GraphicalProjectile {

    /** The projectiles body */
    private final Body body;

    /** The projectiles sprite. I will represent the body graphically */
    private final Sprite sprite;

    GraphicalProjectile(Body body){
        this.body = body;
        sprite = AssetsManager.getInstance().getProjectileSprite();
    }

    public void draw(Batch batch){
        Vector2 projectilePos = new Vector2(body.getPosition().x, body.getPosition().y);
        sprite.setPosition(projectilePos.x * Constants.pixelsPerMeter() - sprite.getWidth() / 2,
                            projectilePos.y * Constants.pixelsPerMeter() - sprite.getHeight() / 2);
        sprite.draw(batch);

    }
}
