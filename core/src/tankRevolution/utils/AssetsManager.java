package tankRevolution.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.io.FileNotFoundException;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton that handles all assets.
 */
public class AssetsManager {

    //private Map<TextureAtlasAssets, TextureAtlas> textureAtlases;
    /**
     * A Map with all textureAtlases and with a String for key
     */
    private Map<Id, Array<Sprite>> spriteArrays;
    /**
     * the sprite for the standard projectile
     */
    private Sprite projectile;

    private List<Texture> startMenuTextures;

    private List<Texture> pauseMenuTextures;

    private Texture settingsTexture;

    private List<Texture> UITextures;

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
            loadUIButtonTextures();
        }
        catch (FileNotFoundException e){
            System.out.println("One or more textures not found");
        }
    }

    /**
     * @param textureName the name of the textureAtlas
     * @return the textureAtlas
     */
    public Array<Sprite> getSpriteArray(Id textureName){
        return spriteArrays.get(textureName);
    }

    public Sprite getProjectileSprite(){
        return projectile;
    }

    public List<Texture> getStartMenuTextures(){
        return startMenuTextures;
    }

    public List<Texture> getPauseMenuTextures() {
        return pauseMenuTextures;
    }

    public List<Texture> getUITextures() {
        return UITextures;
    }

    private AssetsManager() {
        spriteArrays = new HashMap<Id, Array<Sprite>>();
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
        Array<Sprite> explosions = new Array<Sprite>();
        for(int i = 1; i <= 3; i++){
            explosions.add(new Sprite(new Texture(Gdx.files.internal("Explosion/Explosion"+i+".png"))));
        }
        spriteArrays.put(Id.EXPLOSION, explosions);
    }

    private void loadPlayer1TankTextureAtlas() throws FileNotFoundException{
        Array<Sprite> greenTank = new Array<Sprite>();
        for(int i = 0; i <= 180; i+=10){
            greenTank.add(new Sprite(new Texture(Gdx.files.internal("GreenTank/GreenTank"+i+".png"))));
        }
        spriteArrays.put(Id.PLAYER1, greenTank);
    }

    private void loadPlayer2TankTextureAtlas() throws FileNotFoundException{
        Array<Sprite> whiteTank = new Array<Sprite>();
        for(int i = 0; i <= 180; i+=10){
            whiteTank.add(new Sprite(new Texture(Gdx.files.internal("WhiteTank/WhiteTank"+i+".png"))));
        }
        spriteArrays.put(Id.PLAYER2, whiteTank);
    }

    private void loadPlayer3TankTextureAtlas() throws FileNotFoundException{
        Array<Sprite> yellowTank = new Array<Sprite>();
        for(int i = 0; i <= 180; i+=10){
            yellowTank.add(new Sprite(new Texture(Gdx.files.internal("YellowTank/YellowTank"+i+".png"))));
        }
        spriteArrays.put(Id.PLAYER3, yellowTank);
    }

    private void loadPlayer4TankTextureAtlas() throws FileNotFoundException{
        Array<Sprite> blueTank = new Array<Sprite>();
        for(int i = 0; i <= 180; i+=10){
            blueTank.add(new Sprite(new Texture(Gdx.files.internal("BlueTank/BlueTank"+i+".png"))));
        }
        spriteArrays.put(Id.PLAYER4, blueTank);
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

    private void loadUIButtonTextures() throws FileNotFoundException{
        UITextures = new ArrayList<Texture>();
        UITextures.add(new Texture(Gdx.files.internal("LeftMoveButton.png")));
        UITextures.add(new Texture(Gdx.files.internal("RightMoveButton.png")));
        UITextures.add(new Texture(Gdx.files.internal("PauseMenuButton.png")));
    }

}
