package tankRevolution.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

import java.io.FileNotFoundException;
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

    private Sprite pauseMenuButton;

    private Sprite leftSwitchWeaponButton;

    private Sprite rightSwitchWeaponButton;

    private List<Sound> soundEffects;

    private List<BitmapFont> fonts;

    private Array<Sprite> leftPressedButton;

    private Array<Sprite> rightPressedButton;

    private Array<Sprite> leftNotPressedButton;

    private Array<Sprite> rightNotPressedButton;

    private String mapString;

    private List<String> mapNames;

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
     *
     * @param nrOfPlayers nr of players
     */
    public void loadStartingAssets(int nrOfPlayers) {
        if (nrOfPlayers == 2) {
            load2PlayerAssets();
        } else if (nrOfPlayers == 3) {
            load3PlayerAssets();
        } else {
            load4PlayerAssets();
        }

        try {
            loadExplosionTextureAtlas();
            loadProjectileSprite();
            loadStartMenuTextures();
            loadPauseMenuTextures();
            loadPauseButtonTexture();
            loadSoundEffects();
            loadFonts();
            loadCustomGameFont();
            loadRightSwitchWeaponButton();
            loadLeftSwitchWeaponButton();
            loadMoveButtonTextures();
        } catch (FileNotFoundException e) {
            System.out.println("One or more textures not found");
        }
    }

    /**
     * @param textureName the name of the textureAtlas
     * @return the textureAtlas
     */
    public Array<Sprite> getSpriteArray(Id textureName) {
        return spriteArrays.get(textureName);
    }

    public Sprite getProjectileSprite() {
        return projectile;
    }

    public List<Texture> getStartMenuTextures() {
        return startMenuTextures;
    }

    public List<Texture> getPauseMenuTextures() {
        return pauseMenuTextures;
    }

    public Sprite getPauseMenuButton() {
        return pauseMenuButton;
    }

    public Sprite getLeftSwitchWeaponButton() {
        return leftSwitchWeaponButton;
    }

    public Sprite getRightSwitchWeaponButton() {
        return rightSwitchWeaponButton;
    }

    private AssetsManager() {
        spriteArrays = new HashMap<Id, Array<Sprite>>();
        loadMapNames();
    }

    public List<Sound> getSoundEffects() {
        return soundEffects;
    }

    public List<BitmapFont> getFonts() {
        return fonts;
    }

    public Array<Sprite> getLeftPressedButtonSprites() {
        return leftPressedButton;
    }

    public Array<Sprite> getRightPressedButtonSprites() {
        return rightPressedButton;
    }

    public Array<Sprite> getLeftNotPressedButtonSprites() {
        return leftNotPressedButton;
    }

    public Array<Sprite> getRightNotPressedButtonSprites() {
        return rightNotPressedButton;
    }

    public List<String> getMapNames(){
        return mapNames;
    }

    private void loadMapNames(){
        FileHandle fileHandle = Gdx.files.internal("maps/" + "mapNames");
        String maps = fileHandle.readString();
        mapNames = new ArrayList<String>();

        int firstIndexOfRow = 0;

        for (int i = 0; i < maps.length(); i++) {
            if (maps.charAt(i) == '\n') {
                //System.out.println(i + " = i");
                mapNames.add(maps.substring(firstIndexOfRow, i));
                firstIndexOfRow = i + 1;
            }
        }
        mapNames.add(maps.substring(firstIndexOfRow));
    }

    /**
     * The following methods is used for calling the right loadingMethods
     */
    private void load2PlayerAssets() {
        try {
            loadPlayer1TankTextureAtlas();
            loadPlayer2TankTextureAtlas();
        } catch (FileNotFoundException e) {
            System.out.println("Player 1 or player 2 texture was not found");
        }
    }

    private void load3PlayerAssets() {
        load2PlayerAssets();
        try {
            loadPlayer3TankTextureAtlas();
        } catch (FileNotFoundException e) {
            System.out.println("Player 3 texture was not found");
        }
    }

    private void load4PlayerAssets() {
        load3PlayerAssets();
        try {
            loadPlayer4TankTextureAtlas();
        } catch (FileNotFoundException e) {
            System.out.println("Player 4 texture was not found");
        }
    }

    /**
     * The following methods loads the assets from the android assets folder and puts into the map or the Sprite variable
     */
    private void loadExplosionTextureAtlas() throws FileNotFoundException {
        Array<Sprite> explosions = new Array<Sprite>();
        for (int i = 1; i <= 3; i++) {
            explosions.add(new Sprite(new Texture(Gdx.files.internal("Explosion/Explosion" + i + ".png"))));
        }
        spriteArrays.put(Id.EXPLOSION, explosions);
    }

    private void loadPlayer1TankTextureAtlas() throws FileNotFoundException {
        Array<Sprite> greenTank = new Array<Sprite>();
        for (int i = 0; i <= 180; i += 10) {
            greenTank.add(new Sprite(new Texture(Gdx.files.internal("GreenTank/GreenTank" + i + ".png"))));
        }
        spriteArrays.put(Id.PLAYER1, greenTank);
    }

    private void loadPlayer2TankTextureAtlas() throws FileNotFoundException {
        Array<Sprite> whiteTank = new Array<Sprite>();
        for (int i = 0; i <= 180; i += 10) {
            whiteTank.add(new Sprite(new Texture(Gdx.files.internal("WhiteTank/WhiteTank" + i + ".png"))));
        }
        spriteArrays.put(Id.PLAYER2, whiteTank);
    }

    private void loadPlayer3TankTextureAtlas() throws FileNotFoundException {
        Array<Sprite> yellowTank = new Array<Sprite>();
        for (int i = 0; i <= 180; i += 10) {
            yellowTank.add(new Sprite(new Texture(Gdx.files.internal("YellowTank/YellowTank" + i + ".png"))));
        }
        spriteArrays.put(Id.PLAYER3, yellowTank);
    }

    private void loadPlayer4TankTextureAtlas() throws FileNotFoundException {
        Array<Sprite> blueTank = new Array<Sprite>();
        for (int i = 0; i <= 180; i += 10) {
            blueTank.add(new Sprite(new Texture(Gdx.files.internal("BlueTank/BlueTank" + i + ".png"))));
        }
        spriteArrays.put(Id.PLAYER4, blueTank);
    }

    public void loadRightSwitchWeaponButton() {
        rightSwitchWeaponButton = new Sprite(new Texture(Gdx.files.internal("RightSwitchWeaponArrow.png")));
    }

    public void loadLeftSwitchWeaponButton() {
        leftSwitchWeaponButton = new Sprite(new Texture(Gdx.files.internal("LeftSwitchWeaponArrow.png")));
    }

    public void loadMap(String mapName) {
        FileHandle fileHandle = Gdx.files.internal("maps/" + mapName);
        mapString = fileHandle.readString();
    }

    public String getMapString() {
        return mapString;
    }

    private void loadMoveButtonTextures() throws FileNotFoundException {
        rightPressedButton = new Array<Sprite>();
        leftPressedButton = new Array<Sprite>();
        rightNotPressedButton = new Array<Sprite>();
        leftNotPressedButton = new Array<Sprite>();
        for (int i = 0; i <= 100; i += 10) {
            rightPressedButton.add(new Sprite(new Texture(Gdx.files.internal("RightMoveButtons/right_" + i + "_pressed.png"))));
            leftPressedButton.add(new Sprite(new Texture(Gdx.files.internal("LeftMoveButtons/left_" + i + "_pressed.png"))));
            rightNotPressedButton.add(new Sprite(new Texture(Gdx.files.internal("RightMoveButtons/right_" + i + "_notPressed.png"))));
            leftNotPressedButton.add(new Sprite(new Texture(Gdx.files.internal("LeftMoveButtons/left_" + i + "_notPressed.png"))));
        }

    }

    private void loadProjectileSprite() {
        projectile = new Sprite(new Texture(Gdx.files.internal("Projectile.png")));
    }

    private Texture getSettingsTexture() {
        if (settingsTexture == null) {
            settingsTexture = new Texture(Gdx.files.internal("Kugghjul.png"));
        }
        return settingsTexture;
    }

    private void loadStartMenuTextures() throws FileNotFoundException {
        startMenuTextures = new ArrayList<Texture>();
        startMenuTextures.add(new Texture(Gdx.files.internal("NewGameButtonIcon.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("Disabled.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("Disabled.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("Disabled.png")));
        startMenuTextures.add(getSettingsTexture());
    }

    private void loadPauseMenuTextures() throws FileNotFoundException {
        pauseMenuTextures = new ArrayList<Texture>();
        pauseMenuTextures.add(new Texture(Gdx.files.internal("ResumeButton.png")));
        pauseMenuTextures.add(new Texture(Gdx.files.internal("RestartButton.png")));
        pauseMenuTextures.add(new Texture(Gdx.files.internal("MainMenuButton.png")));
        pauseMenuTextures.add(getSettingsTexture());
    }

    private void loadPauseButtonTexture() throws FileNotFoundException {
        pauseMenuButton = new Sprite(new Texture(Gdx.files.internal("PauseMenuButton.png")));
    }

    private void loadSoundEffects() throws FileNotFoundException {
        soundEffects = new ArrayList<Sound>();
        soundEffects.add(Gdx.audio.newSound(Gdx.files.internal("Sounds/Cannon-sound-effect.mp3")));
        soundEffects.add(Gdx.audio.newSound(Gdx.files.internal("Sounds/Boom-sound.mp3")));
    }

    private void loadFonts() throws FileNotFoundException {
        fonts = new ArrayList<BitmapFont>();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Noteworthy-Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getWidth() / 64;
        fonts.add(generator.generateFont(parameter));
        generator.dispose();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Noteworthy-Bold.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getWidth() / 16;
        fonts.add(generator.generateFont(parameter));
        generator.dispose();
    }

    private void loadCustomGameFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Noteworthy-Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getWidth() / 22;
        fonts.add(generator.generateFont(parameter));
        generator.dispose();
    }
}
