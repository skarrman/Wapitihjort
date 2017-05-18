package tankRevolution.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.services.AssetsManager;
import tankRevolution.utils.Constants;
import tankRevolution.utils.Id;
import java.util.List;

/**
 * View showing the different options available in the custom game menu.
 */
class OptionsPickerView {

    /** The list of sprites to represent if the player is a NPC or player */
    private final List<Sprite> npcOrPlayerSprites;

    /** The list of sprites to represent the different choices in NPC difficulty */
    private final List<Sprite> npcDifficultySprites;

    /** The sprite that represents the current choice if the player is NPC or not */
    private Sprite npcOrPlayerSprite;

    /** The sprite that represents the current selected NPC difficulty */
    private Sprite npcDifficultySprite;

    /** The font that is used by the text */
    private final BitmapFont font;

    /** Tool to help with layout */
    private final GlyphLayout label;

    /** The string that tells which player the different options affect. */
    private String str;

    /** Tells if the player is selected to be a NPC at the moment */
    private boolean isNPC;

    /** The y-coordinate of there the items will be */
    private float y;

    OptionsPickerView(Id id){

        npcOrPlayerSprites = AssetsManager.getInstance().getNpcOrPlayerSprites();
        npcDifficultySprites = AssetsManager.getInstance().getNpcDifficultySprites();

        npcOrPlayerSprite = npcOrPlayerSprites.get(0);
        npcDifficultySprite = npcDifficultySprites.get(0);

        setValues(id);
        setSizes();
        setPositions();

        AssetsManager assetsManager = AssetsManager.getInstance();
        label = new GlyphLayout();
        font = assetsManager.getFonts().get(2);
        font.setColor(0, 0, 0, 1);

        isNPC = false;
    }

    /**
     * Draws the text, npc/player picker and NPC difficulty picker.
     * @param batch The batch to draw the items on.
     */
    void draw(Batch batch) {
        label.setText(font, str);
        float height = label.height;
        font.draw(batch, str, Constants.getNumberOfPlayersPos().x,
                y + Constants.getPickerHeight() + height / 2);
        npcOrPlayerSprite.setY(y);
        npcOrPlayerSprite.draw(batch);
        if (isNPC) {
            npcDifficultySprite.setY(y);
            npcDifficultySprite.draw(batch);
        }
    }

    /**
     * Sets the value of NPC and changes the sprite to the correct one
     * according to the new value of isNPC.
     * @param isNPC The value if the this player is set to be a NPC or not.
     */
    void setNPC(boolean isNPC){
        this.isNPC = isNPC;
        if(isNPC)
            npcOrPlayerSprite = npcOrPlayerSprites.get(0);

        else
            npcOrPlayerSprite = npcOrPlayerSprites.get(1);
    }

    /**
     * Changes the difficulty picker sprite to be correct to the new setting.
     * @param difficulty The new NPC difficulty value.
     */
    void setDifficulty(NPCDifficulty difficulty){
        switch (difficulty){
            case EASY:
                npcDifficultySprite = npcDifficultySprites.get(0);
                break;
            case MEDIUM:
                npcDifficultySprite = npcDifficultySprites.get(1);
                break;
            case HARD:
                npcDifficultySprite = npcDifficultySprites.get(2);
                break;
            case SUPERHARD:
                npcDifficultySprite = npcDifficultySprites.get(3);
                break;
        }
    }

    /**
     * Sets the value of the string and y-coordinate with help from the id.
     * @param id The id of the player that this picker affects.
     */
    private void setValues(Id id){
        switch (id){
            case PLAYER1:
                str = "Player 1";
                y = Constants.getPickerY(0);
                break;
            case PLAYER2:
                str = "Player 2";
                y = Constants.getPickerY(1);
                break;
            case PLAYER3:
                str = "Player 3";
                y = Constants.getPickerY(2);
                break;
            case PLAYER4:
                str = "Player 4";
                y = Constants.getPickerY(3);
                break;
            default:
                str = "Error";
                y = Constants.getPickerY(0);
                break;
        }
    }

    /**
     * Sets the sizes of the sprites.
     */
    private void setSizes(){
        float pickerHeight = Constants.getPickerHeight();
        float npcOrPlayerWidth = Constants.getNpcOrPlayerWidth();
        for(Sprite npcORPl : npcOrPlayerSprites){
            npcORPl.setSize(npcOrPlayerWidth, pickerHeight);
        }

        float npcDifficultyWidth = Constants.getNpcDifficultyWidth();
        for(Sprite npcDifficulty : npcDifficultySprites){
            npcDifficulty.setSize(npcDifficultyWidth, pickerHeight);
        }
    }

    /**
     * Sets the positions of the sprites.
     */
    private void setPositions(){
        float npcOrPlayerX = Constants.getNpcOrPlayerX();
        for(Sprite npcOrPlayer : npcOrPlayerSprites){
            npcOrPlayer.setX(npcOrPlayerX);
        }

        float npcDifficultyX = Constants.getNpcDifficultyX();

        for(Sprite npcDifficulty : npcDifficultySprites){
            npcDifficulty.setX(npcDifficultyX);
        }
    }
}
