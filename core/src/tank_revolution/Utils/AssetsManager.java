package tank_revolution.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton that handles all assets.
 */
public class AssetsManager {

    //private Map<TextureAtlasAssets, TextureAtlas> textureAtlases;
    /**
     * A Map with all textureAtlases and with a String for key
     */
    private Map<String, TextureAtlas> textureAtlases;
    /**
     * the sprite for the standard projectile
     */
    private Sprite projectile;

    /**
     * our instance
     */
    private static AssetsManager ourInstance = new AssetsManager();

    /**
     * singleton method
     */
    public static AssetsManager getInstance() {
        return ourInstance;
    }

    /**
     * loads all assets that is need to start the game, should be called during game startup.
     * Only loads the tank textures for the players who are playing
     * @param nrOfPlayers nr of players
     */
    public void loadStartingAssets(int nrOfPlayers){
        if (nrOfPlayers == 2){
            load2PlayerAssets();
        }
        else if(nrOfPlayers == 3){
            load3PlayerAssets();
        }
        else {
            load4PlayerAssets();
        }

        loadExplosionTextureAtlas();
        loadProjectileSprite();
    }

    /**
     * @param textureName the name of the textureAtlas
     * @return the textureAtlas
     */
    public TextureAtlas getTextureAtlas(String textureName){
        return textureAtlases.get(textureName);
    }

    private AssetsManager() {
        //textureAtlases = new HashMap<TextureAtlasAssets, TextureAtlas>();
        textureAtlases = new HashMap<String, TextureAtlas>();
    }

    /**
     * The following methods is used for calling the right loadingMethods
     */
    private void load2PlayerAssets(){
        loadPlayer1TankTextureAtlas();
        loadPlayer2TankTextureAtlas();
    }
    private void load3PlayerAssets(){
        load2PlayerAssets();
        loadPlayer3TankTextureAtlas();
    }
    private void load4PlayerAssets(){
        load3PlayerAssets();
        loadPlayer4TankTextureAtlas();
    }

    /**
     * The following methods loads the assets from the android assets folder and puts into the map or the Sprite variable
     */
    private void loadExplosionTextureAtlas(){
        //textureAtlases.put(TextureAtlasAssets.EXPLOSION, new TextureAtlas(Gdx.files.internal("Explosion.txt")));
        textureAtlases.put("explosion", new TextureAtlas(Gdx.files.internal("Explosion.txt")));
    }

    private void loadPlayer1TankTextureAtlas(){
        //textureAtlases.put(TextureAtlasAssets.PLAYER1_TANK, new TextureAtlas(Gdx.files.internal("GreenTank.txt")));
        textureAtlases.put("player1", new TextureAtlas(Gdx.files.internal("GreenTank.txt")));
    }

    private void loadPlayer2TankTextureAtlas(){
        //textureAtlases.put(TextureAtlasAssets.PLAYER2_TANK, new TextureAtlas(Gdx.files.internal("WhiteTank.txt")));
        textureAtlases.put("player2", new TextureAtlas(Gdx.files.internal("WhiteTank.txt")));
    }

    private void loadPlayer3TankTextureAtlas(){

    }

    private void loadPlayer4TankTextureAtlas(){

    }

    private void loadProjectileSprite(){
        projectile = new Sprite(new Texture(Gdx.files.internal("Projectile.png")));
    }


    /*enum TextureAtlasAssets{
        EXPLOSION,
        PLAYER1_TANK,
        PLAYER2_TANK,
        PLAYER3_TANK,
        PLAYER4_TANK,

    }*/
}
