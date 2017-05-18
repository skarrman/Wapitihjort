package tankRevolution.services;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import tankRevolution.utils.Id;

import java.util.List;

/**
 * Created by jakobwall on 2017-05-18.
 * Created as a facade for the Asset Manager.
 */
public interface IAssetsManager {

    Array<Sprite> getSpriteArray(Id textureName);

    Sprite getProjectileSprite();

    List<Texture> getStartMenuTextures();

    List<Texture> getPauseMenuTextures();

    Sprite getPauseMenuButton();

    Sprite getLeftSwitchWeaponButton();

    Sprite getRightSwitchWeaponButton();

    List<Sound> getSoundEffects();

    List<BitmapFont> getFonts();

    Array<Sprite> getLeftPressedButtonSprites();

    Array<Sprite> getRightPressedButtonSprites();

    Array<Sprite> getLeftNotPressedButtonSprites();

    Array<Sprite> getRightNotPressedButtonSprites();

    List<String> getMapNames();

    void loadMapNames();

    String getMapString();

    List<Sprite> getNpcOrPlayerSprites();

    List<Sprite> getNpcDifficultySprites();

    List<Sprite> getNumberOfPlayerSprites();

    void loadStartMenuAssets();

    Sprite getCustomGameButtonSprite();

    void loadCustomGameAssets();

    void loadNewGameAssets(int nrOfPlayers, String mapString);

}
