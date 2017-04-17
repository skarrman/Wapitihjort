package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import sun.java2d.pipe.AAShapePipe;
import tank_revolution.model.Explosion;

/**
 * <p>The animation of a explosion</p>
 */
public class ExplosionAnimation{
    private float x;
    private float y;
    private float time;
    private int blastRadius;
    private float metersToPixels;
    TextureAtlas textureAtlas;
    Animation<TextureRegion> animation;

    ExplosionAnimation(Explosion explosion, float metersToPixels){
        textureAtlas = new TextureAtlas(Gdx.files.internal("Explosion.txt"));
        animation = new Animation<TextureRegion>(1 / 20f, textureAtlas.getRegions());
        this.metersToPixels = metersToPixels;
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

    void dispose() {
        textureAtlas.dispose();
    }




}
