package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;


/**
 * Created by simonkarrman on 2017-05-15.
 */
public class CustomGameView implements Viewable {

    private Batch batch;

    private BitmapFont bigFont;

    private BitmapFont smallFont;

    private GlyphLayout label;

    private StringBuilder str;

    private Sprite rightMapArrow;

    private Sprite leftMapArrow;

    private Sprite numberOfPlayersPicker;

    private Sprite npcOrPlayerPicker;

    private Sprite npcDifficutlyPicker;

    private Sprite startGameButton;

    private List<String> mapNames;

    private int selectedMap = 0;

    public CustomGameView(){
        batch = new SpriteBatch();
        str = new StringBuilder();
        AssetsManager assetsManager = AssetsManager.getInstance();
        bigFont = assetsManager.getFonts().get(1);
        label = new GlyphLayout();
        bigFont.setColor(0, 0, 0, 1);
        smallFont = assetsManager.getFonts().get(0);
        smallFont.setColor(0,0,0,1);
        mapNames = assetsManager.getMapNames();

        leftMapArrow = new Sprite(new Texture(Gdx.files.internal("LeftSwitchWeaponArrow.png")));
        rightMapArrow = new Sprite(new Texture(Gdx.files.internal("RightSwitchWeaponArrow.png")));
        numberOfPlayersPicker = new Sprite(new Texture(Gdx.files.internal("NumberOfPlayersPicker1.png")));
        npcOrPlayerPicker = new Sprite((new Texture(Gdx.files.internal("NPCPicker1.png"))));
        npcDifficutlyPicker = new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker1.png")));
        startGameButton = new Sprite(new Texture(Gdx.files.internal("StartGameButton.png")));
        setSizes();
        setPositions();

    }

    @Override
    public void update() {
        batch.begin();
        Gdx.gl.glClearColor(1f,1, 1f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        label.setText(smallFont, mapNames.get(selectedMap));
        float width = label.width;
        float height = label.height;
        smallFont.draw(batch, mapNames.get(selectedMap), Gdx.graphics.getWidth()/2 - width/2,
                14 * Gdx.graphics.getHeight()/16 + leftMapArrow.getHeight()/2 + height/2);

        leftMapArrow.draw(batch);
        rightMapArrow.draw(batch);

        numberOfPlayersPicker.draw(batch);
        drawPlayerOptions();
        startGameButton.draw(batch);

        batch.end();

    }

    @Override
    public void dispose() {

    }

    private void setPositions(){
        Vector2 leftArrowPos = Constants.getLeftWeaponPosition();
        leftMapArrow.setPosition(leftArrowPos.x, leftArrowPos.y);

        Vector2 rightArrowPos = Constants.getRightWeaponPosition();
        rightMapArrow.setPosition(rightArrowPos.x, rightArrowPos.y);

        numberOfPlayersPicker.setPosition(Gdx.graphics.getWidth()/2 - numberOfPlayersPicker.getWidth()/2 ,
                12 * Gdx.graphics.getHeight()/16);
        npcOrPlayerPicker.setPosition(Gdx.graphics.getWidth()/2, 10 * Gdx.graphics.getHeight()/16);

        npcDifficutlyPicker.setPosition(numberOfPlayersPicker.getX()+numberOfPlayersPicker.getWidth()-npcDifficutlyPicker.getWidth(),
                                        10 * Gdx.graphics.getHeight()/16);

    }

    private void setSizes(){
        float arrowSize = Constants.getWeaponArrowDimension();
        leftMapArrow.setSize(arrowSize, arrowSize);
        rightMapArrow.setSize(arrowSize, arrowSize);

        Vector2 numberOfPlayersSize = new Vector2(6 * Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/10);
        numberOfPlayersPicker.setSize(numberOfPlayersSize.x, numberOfPlayersSize.y);

        Vector2 playerSettingsPickerSize = new Vector2(4 * Gdx.graphics.getWidth()/24, Gdx.graphics.getHeight()/10);
        npcOrPlayerPicker.setSize(playerSettingsPickerSize.x, playerSettingsPickerSize.y);
        npcDifficutlyPicker.setSize(playerSettingsPickerSize.x, playerSettingsPickerSize.y);

    }

    private void drawPlayerOptions(){
        for(int i = 0; i < 4; i++){
            str.delete(0, str.length());
            str.append("Player ").append(i+1);
            label.setText(smallFont, str);
            float height = label.height;
            smallFont.draw(batch, str, numberOfPlayersPicker.getX(),
                        (10 - i * 2)*Gdx.graphics.getHeight()/16 + npcDifficutlyPicker.getHeight()/2 + height/2);
            npcOrPlayerPicker.setY((10 - i * 2)*Gdx.graphics.getHeight()/16);
            npcDifficutlyPicker.setY((10 - i * 2)*Gdx.graphics.getHeight()/16);
            npcOrPlayerPicker.draw(batch);
            npcDifficutlyPicker.draw(batch);
        }
    }
}
