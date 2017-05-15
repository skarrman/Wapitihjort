package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tankRevolution.utils.AssetsManager;

/**
 * Created by simonkarrman on 2017-05-15.
 */
public class CustomGameView implements Viewable {

    private Batch batch;

    private BitmapFont font;

    private GlyphLayout label;

    private StringBuilder str;

    private Sprite rightMapArrow;

    private Sprite leftMapArrow;

    private Sprite numberOfPlayersPicker;

    private Sprite npcOrPlayerPicker;

    private Sprite npcDifficutlyPicker;

    private Sprite startGameButton;

    public CustomGameView(){
        AssetsManager assetsManager = AssetsManager.getInstance();
        font = assetsManager.getFonts().get(1);
        label = new GlyphLayout();
        font.setColor(0, 0, 0, 1);

        leftMapArrow = new Sprite(new Texture(Gdx.files.internal("LeftSwitchWeaponArrow.png")));
        rightMapArrow = new Sprite(new Texture(Gdx.files.internal("RightSwitchWeaponArrow.png")));
        numberOfPlayersPicker = new Sprite(new Texture(Gdx.files.internal("NumberOfPlayersPicker1.png")));
        npcOrPlayerPicker = new Sprite((new Texture(Gdx.files.internal("NPCPicker1.png"))));
        npcDifficutlyPicker = new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker1.png")));
        startGameButton = new Sprite(new Texture(Gdx.files.internal("StartGameButton.png")));

    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }
}
