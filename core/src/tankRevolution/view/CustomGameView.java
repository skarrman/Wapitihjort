package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.services.AssetsManager;
import tankRevolution.services.Constants;
import tankRevolution.services.Id;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;


/**
 * The graphical representation of the custom game screen.
 * {@inheritDoc}
 */
public class CustomGameView implements Viewable {

    /** The batch that all the graphical elements is drawn on */
    private final Batch batch;

    /** The font that is used in this view */
    private final BitmapFont font;

    /** The class that helps with logic when drawing texts */
    private final GlyphLayout label;

    /**
     * A list of sprites containing all the different sprite to represents
     * all the different number of players selected.
     */
    private final List<Sprite> numberOfPlayerSprites;

    /** The sprite that represents the right arrow in the map selector */
    private final Sprite rightMapArrow;

    /** The sprite that represents the right arrow in the map selector */
    private final Sprite leftMapArrow;

    /** The sprite that represents the current selected amount of players */
    private Sprite numberOfPlayersPicker;

    /** The graphical representation of the player options for player 1 */
    private final OptionsPickerView player1;

    /** The graphical representation of the player options for player 2 */
    private final OptionsPickerView player2;

    /** The graphical representation of the player options for player 3 */
    private final OptionsPickerView player3;

    /** The graphical representation of the player options for player 4 */
    private final OptionsPickerView player4;

    /** The sprite that represent the start game button */
    private final Sprite startGameButton;

    /** A list that contains the names of all the available maps */
    private final List<String> mapNames;

    /** The index in the mapNames array that is selected */
    private int selectedMap = 0;

    /** The amount of players selected */
    private int numberOfPlayers = 2;

    /**
     * Initialising all the elements in the view.
     */
    public CustomGameView() {
        batch = new SpriteBatch();
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
        batch.dispose();
    }

    /**
     * The method that handles when a player switching from being a NPC or vice versa.
     * @param isNPC Tells if the affected player is an NPC or not.
     * @param player The affected player.
     */
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

    /**
     * Handles changes where the difficulty of an NPC is changed.
     * @param player The affected player.
     * @param difficulty The new difficulty level.
     */
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

    /**
     * Handels changes in number of players.
     * @param numberOfPlayers the new amount of player chosen.
     */
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

    /**
     * Increases the index of the selected map.
     */
    public void setNextMap(){
        selectedMap = (selectedMap + 1) % mapNames.size();
    }

    /**
     * Decreases the index of the selected map.
     */
    public void setPreviousMap() {
        if(selectedMap == 0){
            selectedMap = mapNames.size()-1;
        }else{
            selectedMap = selectedMap -1;
        }
    }

    /**
     * Setting all the sizes of the sprites.
     */
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

    /**
     * Setting all the positions of the sprites.
     */
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

    /**
     * Setting the starting values on the two first players
     * because it is only two players shown when the screen
     * is launched.
     */
    private void setStartingValues(){
        numberOfPlayersPicker = numberOfPlayerSprites.get(0);
        player1.setNPC(false);
        player2.setNPC(true);
        player2.setDifficulty(NPCDifficulty.MEDIUM);
    }
}
