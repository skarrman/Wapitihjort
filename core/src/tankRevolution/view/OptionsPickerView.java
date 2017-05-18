package tankRevolution.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;
import tankRevolution.utils.Id;
import java.util.List;

/**
 * Created by simonkarrman on 2017-05-17.
 * View showing the different options available in the custom game menu.
 */
public class OptionsPickerView {

    private List<Sprite> npcOrPlayerSprites;

    private List<Sprite> npcDifficultySprites;

    private Sprite npcOrPlayerSprite;

    private Sprite npcDifficultySprite;

    private BitmapFont font;

    private GlyphLayout label;

    private String str;

    private boolean isNPC;

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

    public void draw(Batch batch) {
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

    void setNPC(boolean isNPC){
        this.isNPC = isNPC;
        if(isNPC)
            npcOrPlayerSprite = npcOrPlayerSprites.get(0);

        else
            npcOrPlayerSprite = npcOrPlayerSprites.get(1);
    }

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
