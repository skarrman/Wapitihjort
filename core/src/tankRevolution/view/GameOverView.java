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
import tankRevolution.utils.Constants;

/**
 * Created by simonkarrman on 2017-05-10.
 */
public class GameOverView {
    private BitmapFont font;

    private GlyphLayout label;

    private Sprite toMenuSprite;

    private StringBuilder str;

    public GameOverView(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Noteworthy-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getWidth()/16;
        font = generator.generateFont(parameter);
        generator.dispose();
        label = new GlyphLayout();
        font.setColor(0, 0, 0, 1);
        str = new StringBuilder();
        str.append("GAME OVER!");
        toMenuSprite = new Sprite(new Texture(Gdx.files.internal("MainMenuButton.png")));
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
