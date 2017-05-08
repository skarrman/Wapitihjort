package tankRevolution.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    private Map<Id, TextureAtlas> textureAtlases;
    /**
     * the sprite for the standard projectile
     */
    private Sprite projectile;

    private ArrayList<Texture> startMenuTextures;

    private ArrayList<Texture> pauseMenuTextures;

    private Texture settingsTexture;

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

        try {
            loadExplosionTextureAtlas();
            loadProjectileSprite();
            loadStartMenuTextures();
            loadPauseMenuTextures();
        }
        catch (FileNotFoundException e){
            System.out.println("The explosion texture or the projectile texture was not found");
        }
    }

    /**
     * @param textureName the name of the textureAtlas
     * @return the textureAtlas
     */
    public TextureAtlas getTextureAtlas(Id textureName){
        return textureAtlases.get(textureName);
    }

    public Sprite getProjectileSprite(){
        return projectile;
    }

    public ArrayList<Texture> getStartMenuTextures(){
        return startMenuTextures;
    }

    public ArrayList<Texture> getPauseMenuTextures(){
        return pauseMenuTextures;
    }

    private AssetsManager() {
        //textureAtlases = new HashMap<TextureAtlasAssets, TextureAtlas>();
        textureAtlases = new HashMap<Id, TextureAtlas>();
    }

    /**
     * The following methods is used for calling the right loadingMethods
     */
    private void load2PlayerAssets(){
        try {
            loadPlayer1TankTextureAtlas();
            loadPlayer2TankTextureAtlas();
        }
        catch (FileNotFoundException e){
            System.out.println("Player 1 or player 2 texture was not found");
        }
    }
    private void load3PlayerAssets() {
        load2PlayerAssets();
        try {
            loadPlayer3TankTextureAtlas();
        }
        catch (FileNotFoundException e){
            System.out.println("Player 3 texture was not found");
        }
    }
    private void load4PlayerAssets(){
        load3PlayerAssets();
        try{
            loadPlayer4TankTextureAtlas();
        }
        catch (FileNotFoundException e){
            System.out.println("Player 4 texture was not found");
        }
    }

    /**
     * The following methods loads the assets from the android assets folder and puts into the map or the Sprite variable
     */
    private void loadExplosionTextureAtlas() throws FileNotFoundException{
        //textureAtlases.put(TextureAtlasAssets.EXPLOSION, new TextureAtlas(Gdx.files.internal("Explosion.txt")));
        textureAtlases.put(Id.EXPLOSION, new TextureAtlas(Gdx.files.internal("Explosion.txt")));
    }

    private void loadPlayer1TankTextureAtlas() throws FileNotFoundException{
        //textureAtlases.put(TextureAtlasAssets.PLAYER1_TANK, new TextureAtlas(Gdx.files.internal("GreenTank.txt")));
        textureAtlases.put(Id.PLAYER1, new TextureAtlas(Gdx.files.internal("GreenTank.txt")));
    }

    private void loadPlayer2TankTextureAtlas() throws FileNotFoundException{
        //textureAtlases.put(TextureAtlasAssets.PLAYER2_TANK, new TextureAtlas(Gdx.files.internal("WhiteTank.txt")));
        textureAtlases.put(Id.PLAYER2, new TextureAtlas(Gdx.files.internal("WhiteTank.txt")));
    }

    private void loadPlayer3TankTextureAtlas() throws FileNotFoundException{
        textureAtlases.put(Id.PLAYER3, new TextureAtlas(Gdx.files.internal("PLACEHOLDER")));
    }

    private void loadPlayer4TankTextureAtlas() throws FileNotFoundException{
        textureAtlases.put(Id.PLAYER4, new TextureAtlas(Gdx.files.internal("PLACEHOLDER")));
    }

    private void loadProjectileSprite(){
        projectile = new Sprite(new Texture(Gdx.files.internal("Projectile.png")));
    }

    private Texture getSettingsTexture(){
        if(settingsTexture == null){
            settingsTexture = new Texture(Gdx.files.internal("Kugghjul.png"));
        }
        return settingsTexture;
    }

    private void loadStartMenuTextures() throws FileNotFoundException{
        startMenuTextures = new ArrayList<Texture>();
        startMenuTextures.add(new Texture(Gdx.files.internal("NewGameButtonIcon.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("Disabled.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("Disabled.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("Disabled.png")));
        startMenuTextures.add(getSettingsTexture());
    }

    private void loadPauseMenuTextures() throws FileNotFoundException{
        pauseMenuTextures = new ArrayList<Texture>();
        pauseMenuTextures.add(new Texture(Gdx.files.internal("ResumeButton.png")));
        pauseMenuTextures.add(new Texture(Gdx.files.internal("RestartButton.png")));
        pauseMenuTextures.add(new Texture(Gdx.files.internal("MainMenuButton.png")));
        pauseMenuTextures.add(getSettingsTexture());
    }

    /*enum TextureAtlasAssets{
        EXPLOSION,
        PLAYER1_TANK,
        PLAYER2_TANK,
        PLAYER3_TANK,
        PLAYER4_TANK,

    }*/
}
