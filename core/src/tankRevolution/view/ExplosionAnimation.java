package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Id;
import tankRevolution.model.Explosion;

import java.util.List;

/**
 * <p>The animation of a explosion</p>
 */
public class ExplosionAnimation{

    /** Keeps track of the time so that the animation is correct */
    private float time;

    /** The texture atlas of all the textures that represents the explosions that is animated. */
    private Array<Sprite> sprites;

    /** The object that handel the animation */
    private Animation<Sprite> animation;

    /**
     * The constructor of this class that is creating all the object that is needed
     * to animate the explosion.
     *
     * @param explosion The explosion that will be animated.
     * @param metersToPixels The ratio between meters in the world to pixels on the screen.
     */
    ExplosionAnimation(Explosion explosion, float metersToPixels){
        sprites = AssetsManager.getInstance().getSpriteArray(Id.EXPLOSION);
        animation = new Animation<Sprite>(1 / 20f, sprites);
        time = 0;
        setDimension(explosion.blastRadius, explosion.x, explosion.y, metersToPixels);
    }

    /**
     * @return if the animation is finished
     */
    boolean isAnimationFinished(){
        return animation.isAnimationFinished(time);
    }

    /**
     * The method for drawing the animation, is called from the view
     * @param batch The batch the animation will be painted in
     */
    void draw(Batch batch){
        time += Gdx.graphics.getDeltaTime();
        Sprite sprite = animation.getKeyFrame(time, false);
        sprite.draw(batch);
    }

    private void setDimension(int blastRadius, float x, float y, float pixelsPerMeter){
        for(Sprite s : sprites){
            s.setSize(blastRadius * pixelsPerMeter, blastRadius * pixelsPerMeter);
            s.setPosition((x * pixelsPerMeter) - s.getWidth()/2, (y * pixelsPerMeter) - s.getHeight()/2);
        }
    }

    /**
     * Disposes all the disposes.
     */
    void dispose() {
        //textureAtlas.dispose();
    }

}
