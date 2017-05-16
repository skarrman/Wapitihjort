package tankRevolution.view;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

/**
 * Created by simonkarrman on 2017-04-25.
 */
public class GraphicalProjectile {
    private Body body;
    private Sprite sprite;

    public GraphicalProjectile(Body body, float side){
        this.body = body;
        sprite = AssetsManager.getInstance().getProjectileSprite();
        setSpriteDimensions(side);
    }

    public void draw(Batch batch){
        Vector2 projectilePos = new Vector2(body.getPosition().x, body.getPosition().y);
        sprite.setPosition(projectilePos.x * Constants.pixelsPerMeter() - sprite.getWidth() / 2,
                            projectilePos.y * Constants.pixelsPerMeter() - sprite.getHeight() / 2);
        sprite.draw(batch);

    }

    public void setSpriteDimensions(float side){

    }


}
