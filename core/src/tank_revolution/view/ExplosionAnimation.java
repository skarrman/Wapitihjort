package tank_revolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import tank_revolution.model.Explosion;

/**
 * Created by antonhagermalm on 2017-04-06.
 */
public class ExplosionAnimation{
    private float x;
    private float y;
    private float time;
    private int blastRadius;
    private float metersToPixels;

    Animation<TextureRegion> animation;

    ExplosionAnimation(Explosion explosion, float metersToPixels){
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("Explosion.txt"));
        animation = new Animation<TextureRegion>(1 / 20f, textureAtlas.getRegions());
        this.metersToPixels = metersToPixels;
        time = 0;
        TextureRegion animationFrame = new TextureRegion(animation.getKeyFrame(time, false));
        x = (explosion.x * metersToPixels) - (animationFrame.getRegionWidth() / 2);
        y = (explosion.y * metersToPixels) - (animationFrame.getRegionHeight() / 2);
        blastRadius = explosion.blastRadius;
    }

    boolean isAnimationFinished(){
        return animation.isAnimationFinished(time);
    }

    void draw(Batch batch){
        time += Gdx.graphics.getDeltaTime();
        TextureRegion animationFrame = new TextureRegion(animation.getKeyFrame(time, false));
        batch.draw(animationFrame, x, y);
    }




}
