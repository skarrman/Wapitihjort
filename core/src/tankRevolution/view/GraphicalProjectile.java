package tankRevolution.view;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import tankRevolution.utils.AssetsManager;

/**
 * Created by simonkarrman on 2017-04-25.
 */
public class GraphicalProjectile {
    private Body body;
    private Sprite sprite;
    private float metersToPixels;

    public GraphicalProjectile(Body body, float metersToPixels){
        this.body = body;
        this.metersToPixels = metersToPixels;
        sprite = AssetsManager.getInstance().getProjectileSprite();
    }

    public void draw(Batch batch){
        Vector2 projectilePos = new Vector2(body.getPosition().x, body.getPosition().y);
        sprite.setPosition(projectilePos.x * metersToPixels - sprite.getWidth() / 2,
                            projectilePos.y * metersToPixels - sprite.getHeight() / 2);
        sprite.draw(batch);
    }


}
