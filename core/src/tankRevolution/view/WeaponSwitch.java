package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.model.shootablePackage.AmmunitionType;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

/**
 * Created by jakobwall on 2017-05-11.
 * Class handling the graphic representation of the way to switch between weapons.
 */
public class WeaponSwitch {
    private BitmapFont font;

    private GlyphLayout label;

    private Sprite rightArrowSprite;

    private Sprite leftArrowSprite;

    WeaponSwitch(){
        font = AssetsManager.getInstance().getFonts().get(0);
        label = new GlyphLayout();
        font.setColor(0, 0, 0, 1);
        rightArrowSprite = AssetsManager.getInstance().getRightSwitchWeaponButton();
        leftArrowSprite = AssetsManager.getInstance().getLeftSwitchWeaponButton();
        setDimension();
        setPositions();
    }

    public void draw(Batch batch, AmmunitionType ammunitionType){
        leftArrowSprite.draw(batch);
        rightArrowSprite.draw(batch);
        label.setText(font, ammunitionType.getName());
        float width = label.width;
        float height = label.height;
        font.draw(batch, ammunitionType.getName(), Gdx.graphics.getWidth()/2 - width/2,
                14 * Gdx.graphics.getHeight()/16 + leftArrowSprite.getHeight()/2 + height/2);
    }

    private void setDimension(){
        float sideLength = Constants.getWeaponArrowDimension();
        rightArrowSprite.setSize(sideLength, sideLength);
        leftArrowSprite.setSize(sideLength, sideLength);
    }

    private void setPositions(){
        Vector2 rightPos = Constants.getRightWeaponPosition();
        rightArrowSprite.setPosition(rightPos.x, rightPos.y);

        Vector2 leftPos = Constants.getLeftWeaponPosition();
        leftArrowSprite.setPosition(leftPos.x, leftPos.y);
    }
}
