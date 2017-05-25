package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import tankRevolution.services.AssetsManager;
import tankRevolution.services.Id;
import tankRevolution.model.Explosion;

/**
 * <p>The animation of a explosion</p>
 */
class ExplosionAnimation{

    /** Keeps track of the time so that the animation is correct */
    private float time;

    /** The texture atlas of all the textures that represents the explosions that is animated. */
    private final Array<Sprite> sprites;

    /** The object that handel the animation */
    private final Animation<Sprite> animation;

    /**
     * The constructor of this class that is creating all the object that is needed
     * to animate the explosion.
     *
     * @param explosion The explosion that will be animated.
     * @param pixelsPerMeter The ratio between meters in the world to pixels on the screen.
     */
    ExplosionAnimation(Explosion explosion, float pixelsPerMeter){
        sprites = AssetsManager.getInstance().getSpriteArray(Id.EXPLOSION);
        setDimension(explosion.blastRadius, explosion.x, explosion.y, pixelsPerMeter);
        animation = new Animation<>(1 / 30f, sprites);
        time = 0;
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
            s.setSize(2 * blastRadius * pixelsPerMeter, 2 * blastRadius * pixelsPerMeter);
            s.setPosition((x * pixelsPerMeter) - s.getWidth()/2, (y * pixelsPerMeter) - s.getHeight()/2);
        }
    }

}
