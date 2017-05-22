package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.services.AssetsManager;
import tankRevolution.services.Constants;


/**
 * The class that handles the graphical elements that is shown when the game is over.
 */
public class GameOverView {

    /** The font that is used */
    private final BitmapFont font;

    /** An instance that helps with the layout of texts */
    private final GlyphLayout label;

    /** The sprite that represents the button that takes you back to the start menu */
    private final Sprite toMenuSprite;

    /** The string that is drawn */
    private final String str;

    /**
     * Initializes everything and putting everything where it should be.
     */
    GameOverView(){
        AssetsManager assetsManager = AssetsManager.getInstance();
        font = assetsManager.getFonts().get(1);
        label = new GlyphLayout();
        font.setColor(0, 0, 0, 1);
        str = "GAME OVER!";
        toMenuSprite = new Sprite(AssetsManager.getInstance().getPauseMenuTextures().get(2));
        float width = Constants.getPauseMenuButtonWidth();
        float height = Constants.getPauseMenuButtonHeight();
        Vector2 pos = Constants.getToMenuPosition();
        toMenuSprite.setBounds(pos.x, pos.y, width, height);
    }

    /**
     * The method that render the graphical items on the screen-
     * @param batch The batch that we draw on.
     */
    public void draw(Batch batch){
        toMenuSprite.draw(batch);
        label.setText(font, str);
        float width = label.width;
        font.draw(batch, str, Gdx.graphics.getWidth()/2 - width/2, Constants.getResumeButtonPosition().y);

    }
}
