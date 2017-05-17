package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;
import tankRevolution.utils.Id;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;


/**
 * Created by simonkarrman on 2017-05-15.
 */
public class CustomGameView implements Viewable {

    private Batch batch;

    private BitmapFont font;

    private GlyphLayout label;

    private StringBuilder str;

    private List<Sprite> numberOfPlayerSprites;

    private Sprite rightMapArrow;

    private Sprite leftMapArrow;

    private Sprite numberOfPlayersPicker;

    private OptionsPickerView player1;

    private OptionsPickerView player2;

    private OptionsPickerView player3;

    private OptionsPickerView player4;

    private Sprite startGameButton;

    private List<String> mapNames;

    private int selectedMap = 0;

    private int numberOfPlayers = 2;

    public CustomGameView() {
        batch = new SpriteBatch();
        str = new StringBuilder();
        AssetsManager assetsManager = AssetsManager.getInstance();
        label = new GlyphLayout();
        font = assetsManager.getFonts().get(2);
        font.setColor(0, 0, 0, 1);
        mapNames = assetsManager.getMapNames();

        leftMapArrow = new Sprite(assetsManager.getLeftSwitchWeaponButton());
        rightMapArrow = new Sprite(assetsManager.getRightSwitchWeaponButton());
        startGameButton = assetsManager.getCustomGameButtonSprite();

        numberOfPlayerSprites = assetsManager.getNumberOfPlayerSprites();


        player1 = new OptionsPickerView(Id.PLAYER1);
        player2 = new OptionsPickerView(Id.PLAYER2);
        player3 = new OptionsPickerView(Id.PLAYER3);
        player4 = new OptionsPickerView(Id.PLAYER4);

        setSizes();
        setPositions();
        setStartingValues();
    }

    @Override
    public void update() {
        batch.begin();
        Gdx.gl.glClearColor(1f, 1, 1f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        label.setText(font, mapNames.get(selectedMap));
        float width = label.width;
        float height = label.height;
        font.draw(batch, mapNames.get(selectedMap), Gdx.graphics.getWidth() / 2 - width / 2,
                14 * Gdx.graphics.getHeight() / 16 + leftMapArrow.getHeight() / 2 + height / 2);

        leftMapArrow.draw(batch);
        rightMapArrow.draw(batch);

        numberOfPlayersPicker.draw(batch);

        player1.draw(batch);
        player2.draw(batch);
        if(numberOfPlayers > 2) {
            player3.draw(batch);
            if(numberOfPlayers == 4) {
                player4.draw(batch);
            }
        }

        startGameButton.draw(batch);

        batch.end();

    }

    @Override
    public void dispose() {

    }

    public void setNPC(boolean isNPC, Id player){
        switch (player){
            case PLAYER1:
                player1.setNPC(isNPC);
                break;
            case PLAYER2:
                player2.setNPC(isNPC);
                break;
            case PLAYER3:
                player3.setNPC(isNPC);
                break;
            case PLAYER4:
                player4.setNPC(isNPC);
                break;
        }
    }

    public void setDifficulty(Id player, NPCDifficulty difficulty){
        switch (player){
            case PLAYER1:
                player1.setDifficulty(difficulty);
                break;
            case PLAYER2:
                player2.setDifficulty(difficulty);
                break;
            case PLAYER3:
                player3.setDifficulty(difficulty);
                break;
            case PLAYER4:
                player4.setDifficulty(difficulty);
                break;

        }
    }

    public void setNumberOfPlayers(int numberOfPlayers){

        switch (numberOfPlayers){
            case 2:
                numberOfPlayersPicker = numberOfPlayerSprites.get(0);
                break;
            case 3:
                numberOfPlayersPicker = numberOfPlayerSprites.get(1);
                if(this.numberOfPlayers == 2) {
                    player3.setNPC(true);
                    player3.setDifficulty(NPCDifficulty.MEDIUM);
                }
                break;
            case 4:

                numberOfPlayersPicker = numberOfPlayerSprites.get(2);
                if(this.numberOfPlayers == 2) {
                    player3.setNPC(true);
                    player3.setDifficulty(NPCDifficulty.MEDIUM);
                }
                player4.setNPC(true);
                player4.setDifficulty(NPCDifficulty.MEDIUM);
                break;
        }
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setNextMap(){
        selectedMap = (selectedMap + 1) % mapNames.size();
    }

    public void setPreviousMap() {
        selectedMap = (selectedMap - 1) % mapNames.size();
    }
    private void setSizes() {
        float arrowSize = Constants.getWeaponArrowDimension();
        leftMapArrow.setSize(arrowSize, arrowSize);
        rightMapArrow.setSize(arrowSize, arrowSize);

        float pickerHeight = Constants.getPickerHeight();

        float numberOfPlayersWidth = Constants.getNumberOfPlayerWidth();
        for(Sprite numOfPl : numberOfPlayerSprites){
            numOfPl.setSize(numberOfPlayersWidth, pickerHeight);
        }

        float startCustomGameWidth = Constants.getStartCustomGameWidth();
        float startCustomGameHeight = Constants.getStartCustomGameHeight();
        startGameButton.setSize(startCustomGameWidth, startCustomGameHeight);

    }

    private void setPositions() {
        Vector2 leftArrowPos = Constants.getLeftMapArrowPos();
        leftMapArrow.setPosition(leftArrowPos.x, leftArrowPos.y);

        Vector2 rightArrowPos = Constants.getRightMapArrowPos();
        rightMapArrow.setPosition(rightArrowPos.x, rightArrowPos.y);

        Vector2 numberOfPlayerPos = Constants.getNumberOfPlayersPos();
        for(Sprite numOfPlayer : numberOfPlayerSprites){
            numOfPlayer.setPosition(numberOfPlayerPos.x, numberOfPlayerPos.y);
        }

        Vector2 startButtonPos = Constants.getStartCustomGamePos();
        startGameButton.setPosition(startButtonPos.x, startButtonPos.y);

    }

    private void setStartingValues(){
        numberOfPlayersPicker = numberOfPlayerSprites.get(0);
        player1.setNPC(false);
        player2.setNPC(true);
        player2.setDifficulty(NPCDifficulty.MEDIUM);
    }
}
