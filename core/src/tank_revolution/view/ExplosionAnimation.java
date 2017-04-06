package tank_revolution.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import tank_revolution.model.Explosion;

/**
 * Created by antonhagermalm on 2017-04-06.
 */
public class ExplosionAnimation{
    float x;
    float y;
    float time;
    int blastRadius;
    Animation<TextureRegion> explosionAnimation;

    ExplosionAnimation(Explosion explosion){
        x = explosion.x;
        y = explosion.y;
        blastRadius = explosion.blastRadius;
        time = 0;
    }

    void draw(SpriteBatch batch){
        TextureRegion animationFrame = new TextureRegion(explosionAnimation.getKeyFrame(animationTime, false));
        float x = (explosionPosition.x * metersToPixels) - (animationFrame.getRegionWidth() / 2);
        float y = (explosionPosition.y * metersToPixels) - (animationFrame.getRegionHeight() / 2);
        batch.draw(animationFrame, x, y);
    }


}
