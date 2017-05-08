package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Id;
import tankRevolution.model.Explosion;

/**
 * <p>The animation of a explosion</p>
 */
public class ExplosionAnimation{

    /** The explosion's x-coordinate. */
    private float x;

    /** The explosion's x-coordinate. */
    private float y;

    /** Keeps track of the time so that the animation is correct */
    private float time;

    /** The value of the width of the explosion's blast radius */
    private int blastRadius;

    /** The texture atlas of all the textures that represents the explosions that is animated. */
    private  TextureAtlas textureAtlas;

    /** The object that handel the animation */
    private Animation<TextureRegion> animation;

    /**
     * The constructor of this class that is creating all the object that is needed
     * to animate the explosion.
     *
     * @param explosion The explosion that will be animated.
     * @param metersToPixels The ratio between meters in the world to pixels on the screen.
     */
    ExplosionAnimation(Explosion explosion, float metersToPixels){
        textureAtlas = AssetsManager.getInstance().getTextureAtlas(Id.EXPLOSION);
        animation = new Animation<TextureRegion>(1 / 20f, textureAtlas.getRegions());
        time = 0;
        TextureRegion animationFrame = new TextureRegion(animation.getKeyFrame(time, false));
        x = (explosion.x * metersToPixels) - (animationFrame.getRegionWidth() / 2);
        y = (explosion.y * metersToPixels) - (animationFrame.getRegionHeight() / 2);
        blastRadius = explosion.blastRadius;

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
        TextureRegion animationFrame = new TextureRegion(animation.getKeyFrame(time, false));
        batch.draw(animationFrame, x, y);
    }

    /**
     * Disposes all the disposes.
     */
    void dispose() {
        textureAtlas.dispose();
    }

}
