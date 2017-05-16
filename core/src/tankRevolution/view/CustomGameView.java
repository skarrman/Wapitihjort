package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;
import tankRevolution.utils.Id;
import tankRevolution.utils.Vector;

import java.util.ArrayList;
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

    private List<Sprite> numberOfPlayerSprites;

    private List<Sprite> npcOrPlayerSprites;

    private List<Sprite> npcDifficultySprites;

    private Sprite rightMapArrow;

    private Sprite leftMapArrow;

    private Sprite numberOfPlayersPicker;

    private Sprite player1NpcOrPlayerSprite;

    private Sprite player1DifficultySprite;

    private boolean player1IsNPC = false;

    private Sprite player2NpcOrPlayerSprite;

    private Sprite player2DifficultySprite;

    private boolean player2IsNPC = true;

    private Sprite player3NpcOrPlayerSprite;

    private Sprite player3DifficultySprite;

    private boolean player3IsNPC = false;

    private Sprite player4NpcOrPlayerSprite;

    private Sprite player4DifficultySprite;

    private boolean player4IsNPC = false;


    private Sprite startGameButton;

    private List<String> mapNames;

    private int selectedMap = 0;

    private int numberOfPlayers = 2;

    public CustomGameView() {
        batch = new SpriteBatch();
        str = new StringBuilder();
        AssetsManager assetsManager = AssetsManager.getInstance();
        bigFont = assetsManager.getFonts().get(1);
        label = new GlyphLayout();
        bigFont.setColor(0, 0, 0, 1);
        smallFont = assetsManager.getFonts().get(2);
        smallFont.setColor(0, 0, 0, 1);
        mapNames = assetsManager.getMapNames();

        leftMapArrow = new Sprite(new Texture(Gdx.files.internal("LeftSwitchWeaponArrow.png")));
        rightMapArrow = new Sprite(new Texture(Gdx.files.internal("RightSwitchWeaponArrow.png")));
        startGameButton = new Sprite(new Texture(Gdx.files.internal("StartGameButton.png")));

        numberOfPlayerSprites = new ArrayList<Sprite>();
        numberOfPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NumberOfPlayersPicker1.png"))));
        numberOfPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NumberOfPlayersPicker2.png"))));
        numberOfPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NumberOfPlayersPicker3.png"))));

        npcOrPlayerSprites = new ArrayList<Sprite>();
        npcOrPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NPCPicker1.png"))));
        npcOrPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NPCPicker2.png"))));

        npcDifficultySprites = new ArrayList<Sprite>();
        npcDifficultySprites.add(new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker1.png"))));
        npcDifficultySprites.add(new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker2.png"))));
        npcDifficultySprites.add(new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker3.png"))));
        npcDifficultySprites.add(new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker4.png"))));

        setSizes();
        setPositions();
        setStartingValues();
    }

    @Override
    public void update() {
        batch.begin();
        Gdx.gl.glClearColor(1f, 1, 1f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        label.setText(smallFont, mapNames.get(selectedMap));
        float width = label.width;
        float height = label.height;
        smallFont.draw(batch, mapNames.get(selectedMap), Gdx.graphics.getWidth() / 2 - width / 2,
                14 * Gdx.graphics.getHeight() / 16 + leftMapArrow.getHeight() / 2 + height / 2);

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

    private void drawPlayerOptions() {
        float player1Y = Constants.getPickerY(0);
        str.delete(0, str.length());
        str.append("Player ").append(1);
        label.setText(smallFont, str);
        float height = label.height;
        smallFont.draw(batch, str, Constants.getNumberOfPlayersPos().x,
                player1Y + Constants.getPickerHeight() + height / 2);
        player1NpcOrPlayerSprite.setY(player1Y);
        player1NpcOrPlayerSprite.draw(batch);
        if(player1IsNPC) {
            player1DifficultySprite.setY(player1Y);
            player1DifficultySprite.draw(batch);
        }

        float player2Y = Constants.getPickerY(1);
        str.delete(0, str.length());
        str.append("Player ").append(2);
        label.setText(smallFont, str);
        height = label.height;
        smallFont.draw(batch, str, Constants.getNumberOfPlayersPos().x,
                player2Y + Constants.getPickerHeight() + height / 2);
        player2NpcOrPlayerSprite.setY(player2Y);
        player2NpcOrPlayerSprite.draw(batch);
        if(player2IsNPC) {
            player2DifficultySprite.setY(player2Y);
            player2DifficultySprite.draw(batch);
        }
        if (numberOfPlayers > 2) {
            float player3Y = Constants.getPickerY(2);
            str.delete(0, str.length());
            str.append("Player ").append(3);
            label.setText(smallFont, str);
            height = label.height;
            smallFont.draw(batch, str, Constants.getNumberOfPlayersPos().x,
                    player3Y + Constants.getPickerHeight() + height / 2);
            player3NpcOrPlayerSprite.setY(player3Y);
            player3NpcOrPlayerSprite.draw(batch);
            if(player3IsNPC) {
                player3DifficultySprite.setY(player3Y);
                player3DifficultySprite.draw(batch);
            }

            if (numberOfPlayers == 4) {
                float player4Y = Constants.getPickerY(3);
                str.delete(0, str.length());
                str.append("Player ").append(4);
                label.setText(smallFont, str);
                height = label.height;
                smallFont.draw(batch, str, Constants.getNumberOfPlayersPos().x,
                        player4Y + Constants.getPickerHeight() + height / 2);
                player4NpcOrPlayerSprite.setY(player4Y);
                player4NpcOrPlayerSprite.draw(batch);
                if(player4IsNPC) {
                    player4DifficultySprite.setY(player4Y);
                    player4DifficultySprite.draw(batch);
                }
            }
        }
    }

    public void setNPC(boolean isNPC, Id player){
        switch (player){
            case PLAYER1:
                player1IsNPC = isNPC;
                if(isNPC){
                    player1NpcOrPlayerSprite = npcOrPlayerSprites.get(0);
                    setDifficulty(Id.PLAYER1, NPCDifficulty.EASY);
                }else{
                    player1NpcOrPlayerSprite = npcOrPlayerSprites.get(1);
                }
                break;
            case PLAYER2:
                player2IsNPC = isNPC;
                if(isNPC){
                    player2NpcOrPlayerSprite = npcOrPlayerSprites.get(0);
                    setDifficulty(Id.PLAYER2, NPCDifficulty.EASY);
                }else{
                    player2NpcOrPlayerSprite = npcOrPlayerSprites.get(1);
                }
                break;
            case PLAYER3:
                player3IsNPC = isNPC;
                if(isNPC){
                    player3NpcOrPlayerSprite = npcOrPlayerSprites.get(0);
                    setDifficulty(Id.PLAYER3, NPCDifficulty.EASY);
                }else{
                    player3NpcOrPlayerSprite = npcOrPlayerSprites.get(1);
                }
                break;
            case PLAYER4:
                player4IsNPC = isNPC;
                if(isNPC){
                    player4NpcOrPlayerSprite = npcOrPlayerSprites.get(0);
                    setDifficulty(Id.PLAYER4, NPCDifficulty.EASY);
                }else{
                    player4NpcOrPlayerSprite = npcOrPlayerSprites.get(1);
                }
                break;
        }
    }

    public void setDifficulty(Id player, NPCDifficulty difficulty){
        switch (player){
            case PLAYER1:
                switch (difficulty){
                    case EASY:
                        player1DifficultySprite = npcDifficultySprites.get(0);
                        break;
                    case MEDIUM:
                        player1DifficultySprite = npcDifficultySprites.get(1);
                        break;
                    case HARD:
                        player1DifficultySprite = npcDifficultySprites.get(2);
                        break;
                    case SUPERHARD:
                        player1DifficultySprite = npcDifficultySprites.get(3);
                        break;
                }
                break;
            case PLAYER2:
                switch (difficulty){
                    case EASY:
                        player2DifficultySprite = npcDifficultySprites.get(0);
                        break;
                    case MEDIUM:
                        player2DifficultySprite = npcDifficultySprites.get(1);
                        break;
                    case HARD:
                        player2DifficultySprite = npcDifficultySprites.get(2);
                        break;
                    case SUPERHARD:
                        player2DifficultySprite = npcDifficultySprites.get(3);
                        break;
                }
                break;
            case PLAYER3:
                switch (difficulty){
                    case EASY:
                        player3DifficultySprite = npcDifficultySprites.get(0);
                        break;
                    case MEDIUM:
                        player3DifficultySprite = npcDifficultySprites.get(1);
                        break;
                    case HARD:
                        player3DifficultySprite = npcDifficultySprites.get(2);
                        break;
                    case SUPERHARD:
                        player3DifficultySprite = npcDifficultySprites.get(3);
                        break;
                }
                break;
            case PLAYER4:
                switch (difficulty){
                    case EASY:
                        player4DifficultySprite = npcDifficultySprites.get(0);
                        break;
                    case MEDIUM:
                        player4DifficultySprite = npcDifficultySprites.get(1);
                        break;
                    case HARD:
                        player4DifficultySprite = npcDifficultySprites.get(2);
                        break;
                    case SUPERHARD:
                        player4DifficultySprite = npcDifficultySprites.get(3);
                        break;
                }
                break;

        }
    }

    public void setNumberOfPlayers(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        switch (numberOfPlayers){
            case 2:
                numberOfPlayersPicker = numberOfPlayerSprites.get(0);
                break;
            case 3:
                numberOfPlayersPicker = numberOfPlayerSprites.get(1);
                player3NpcOrPlayerSprite = npcOrPlayerSprites.get(0);
                player3DifficultySprite = npcDifficultySprites.get(2);
                player3IsNPC = true;
                break;
            case 4:
                numberOfPlayersPicker = numberOfPlayerSprites.get(2);
                player3NpcOrPlayerSprite = npcOrPlayerSprites.get(0);
                player3DifficultySprite = npcDifficultySprites.get(2);
                player3IsNPC = true;
                player4NpcOrPlayerSprite = npcOrPlayerSprites.get(0);
                player4DifficultySprite = npcDifficultySprites.get(2);
                player4IsNPC = true;
                break;
        }
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


        float npcOrPlayerWidth = Constants.getNpcOrPlayerWidth();
        for(Sprite npcORPl : npcOrPlayerSprites){
            npcORPl.setSize(npcOrPlayerWidth, pickerHeight);
        }

        float npcDifficultyWidth = Constants.getNpcDifficultyWidth();
        for(Sprite npcDifficulty : npcDifficultySprites){
            npcDifficulty.setSize(npcDifficultyWidth, pickerHeight);
        }

        float startCustomGameWidth = Constants.getStartCustomGameWidth();
        float startCustomGameHeight = Constants.getStartCustomGameHeight();
        startGameButton.setSize(startCustomGameWidth, startCustomGameHeight);

    }

    private void setPositions() {
        Vector2 leftArrowPos = Constants.getLeftWeaponPosition();
        leftMapArrow.setPosition(leftArrowPos.x, leftArrowPos.y);

        Vector2 rightArrowPos = Constants.getRightWeaponPosition();
        rightMapArrow.setPosition(rightArrowPos.x, rightArrowPos.y);

        Vector2 numberOfPlayerPos = Constants.getNumberOfPlayersPos();
        for(Sprite numOfPlayer : numberOfPlayerSprites){
            numOfPlayer.setPosition(numberOfPlayerPos.x, numberOfPlayerPos.y);
        }

        float npcOrPlayerX = Constants.getNpcOrPlayerX();
        for(Sprite npcOrPlayer : npcOrPlayerSprites){
            npcOrPlayer.setX(npcOrPlayerX);
        }

        float npcDifficultyX = Constants.getNpcDifficultyX();

        for(Sprite npcDifficulty : npcDifficultySprites){
            npcDifficulty.setX(npcDifficultyX);
        }

        Vector2 startButtonPos = Constants.getStartCustomGamePos();
        startGameButton.setPosition(startButtonPos.x, startButtonPos.y);

    }

    private void setStartingValues(){
        player1NpcOrPlayerSprite = npcOrPlayerSprites.get(1);

        player2NpcOrPlayerSprite = npcOrPlayerSprites.get(0);

        player2DifficultySprite = npcDifficultySprites.get(1);

        numberOfPlayersPicker = numberOfPlayerSprites.get(0);

    }
}
