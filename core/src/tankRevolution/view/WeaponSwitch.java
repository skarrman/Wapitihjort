package tankRevolution.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import tankRevolution.model.shootablePackage.AmmunitionType;
import tankRevolution.services.AssetsManager;
import tankRevolution.utils.Constants;

/**
 * Class handling the graphic representation of the way to switch between weapons.
 */
class WeaponSwitch {

    /** The font that it used */
    private final BitmapFont font;

    /** Tool for helping layout for text */
    private final GlyphLayout label;

    /** Sprite that represents the right arrow */
    private final Sprite rightArrowSprite;

    /** Sprite that represents the left arrow */
    private final Sprite leftArrowSprite;

    WeaponSwitch(){
        font = AssetsManager.getInstance().getFonts().get(0);
        label = new GlyphLayout();
        font.setColor(0, 0, 0, 1);
        rightArrowSprite = AssetsManager.getInstance().getRightSwitchWeaponButton();
        leftArrowSprite = AssetsManager.getInstance().getLeftSwitchWeaponButton();
        setDimension();
        setPositions();
    }

    /**
     * Draws all the graphical items.
     * @param batch The batch to draw graphical items on.
     * @param ammunitionType The current ammunition that is chosen.
     */
    void draw(Batch batch, AmmunitionType ammunitionType){
        leftArrowSprite.draw(batch);
        rightArrowSprite.draw(batch);
        label.setText(font, ammunitionType.getName());
        float width = label.width;
        float height = label.height;
        font.draw(batch, ammunitionType.getName(), Gdx.graphics.getWidth()/2 - width/2,
                14 * Gdx.graphics.getHeight()/16 + leftArrowSprite.getHeight()/2 + height/2);
    }

    /**
     * Sets the dimension of the sprites.
     */
    private void setDimension(){
        float sideLength = Constants.getWeaponArrowDimension();
        rightArrowSprite.setSize(sideLength, sideLength);
        leftArrowSprite.setSize(sideLength, sideLength);
    }

    /**
     * Sets the positions of the sprites.
     */
    private void setPositions(){
        Vector2 rightPos = Constants.getRightWeaponPosition();
        rightArrowSprite.setPosition(rightPos.x, rightPos.y);

        Vector2 leftPos = Constants.getLeftWeaponPosition();
        leftArrowSprite.setPosition(leftPos.x, leftPos.y);
    }
}
