package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;
import tankRevolution.model.Character;
import tankRevolution.utils.AssetsManager;

import java.util.List;

/**
 * Created by simonkarrman on 2017-05-09.
 * Class responsible for drawing information on the screen about the tanks' health.
 */
public class LabelDrawer {
    private BitmapFont font;

    private GlyphLayout label;

    LabelDrawer(){
        font = AssetsManager.getInstance().getFonts().get(0);
        label = new GlyphLayout();
        font.setColor(0, 0, 0, 1);
    }

    public void draw(List<Character> characterList, Batch batch){
        float rowHeight = Gdx.graphics.getHeight()/16;
        float rowWidth = Gdx.graphics.getWidth()/8;
        for(Character c : characterList){
            StringBuilder str = new StringBuilder();
            Vector2 pos;
            switch (c.getId()) {
                case PLAYER1:
                    str.append("Player 1:\n");
                    pos = new Vector2(2 * rowWidth, 15 * rowHeight);
                    break;
                case PLAYER2:
                    str.append("Player 2:\n");
                    pos = new Vector2(6 * rowWidth, 15 * rowHeight);
                    break;
                case PLAYER3:
                    str.append("Player 3:\n");
                    pos = new Vector2(2 * rowWidth, 12 * rowHeight);
                    break;
                case PLAYER4:
                    str.append("Player 4:\n");
                    pos = new Vector2(6 * rowWidth, 12 * rowHeight);
                    break;
                default:
                    str.append("Error ");
                    pos = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
                    break;
            }
            str.append("Health :").append((int)c.getTank().getHealth());
            label.setText(font, str);
            float width = label.width;

            font.draw(batch, str, pos.x - width/2, pos.y);

        }
    }
}
