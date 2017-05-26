package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Representing the animation of a vertical arrow bouncing.
 * The main purpose of this arrow is to indicate to the player whose turn it is.
 */
class TurnIndicatorAnimation {

    /** Keeps track of the time so that the animation is correct */
    private float time = 0;

    /** Describes the ratio between meters in the world to pixels on the screen */
    private final float metersToPixels;

    /** The texture atlas of the arrow */
    private final TextureAtlas textureAtlas;

    /** The object that handel the animation */
    private final Animation<TextureRegion> animation;

    /**
     * The only constructor of the class that creates all the objects that is needed.
     * @param metersToPixels the ratio between meters in the world and pixels on the screen.
     */
    TurnIndicatorAnimation(float metersToPixels) {
        textureAtlas = new TextureAtlas(Gdx.files.internal("CurrentPlayerArrow.txt"));
        animation = new Animation<TextureRegion>(1 / 15f, textureAtlas.getRegions());
        this.metersToPixels = metersToPixels;
    }

    /**
     * A method that draws the arrow in the right frame with help of the animation.
     * @param batch The batch that the arrow is drawn on.
     * @param pos The position of the current player.
     */
    public void draw(Batch batch, Vector2 pos) {
        time += Gdx.graphics.getDeltaTime();
        TextureRegion animationFrame = new TextureRegion(animation.getKeyFrame(time, true));
        int distanceToTank = 10;
        batch.draw(animationFrame, pos.x * metersToPixels - animationFrame.getRegionWidth()/2,
                ((pos.y + distanceToTank) * metersToPixels) - animationFrame.getRegionHeight()/2);
    }

    /**
     * Disposes all the disposable objects in the class.
     */
    void dispose() {
        textureAtlas.dispose();
    }
}
