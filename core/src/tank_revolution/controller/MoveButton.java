package tank_revolution.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;


/**
 * Created by JakobErlandsson on 2017-04-05.
 */
public class MoveButton extends ImageButton {

    public MoveButton(Texture image) {
        super(new TextureRegionDrawable(new TextureRegion(image)));
    }
}


