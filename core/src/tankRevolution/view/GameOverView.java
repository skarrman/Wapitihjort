package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

import java.util.List;

/**
 * Created by simonkarrman on 2017-05-10.
 */
public class GameOverView {
    private BitmapFont font;

    private GlyphLayout label;

    private Sprite toMenuSprite;

    private StringBuilder str;

    public GameOverView(){
        AssetsManager assetsManager = AssetsManager.getInstance();
        font = assetsManager.getFonts().get(1);
        label = new GlyphLayout();
        font.setColor(0, 0, 0, 1);
        str = new StringBuilder();
        str.append("GAME OVER!");
        toMenuSprite = new Sprite(AssetsManager.getInstance().getPauseMenuTextures().get(2));
        float width = Constants.getPauseMenuButtonWidth();
        float height = Constants.getPauseMenuButtonHeight();
        Vector2 pos = Constants.getToMenuPosition();
        toMenuSprite.setBounds(pos.x, pos.y, width, height);
    }

    public void draw(Batch batch){
        toMenuSprite.draw(batch);
        label.setText(font, str);
        float width = label.width;
        font.draw(batch, str, Gdx.graphics.getWidth()/2 - width/2, Constants.getResumeButtonPosition().y);

    }
}
