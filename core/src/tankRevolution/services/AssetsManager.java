package tankRevolution.services;

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
 * Singleton that handles and loads all assets when needed.
 */
public class AssetsManager {

    /**
     * A Map with all textureAtlases and with a String for key
     */
    private final Map<Id, Array<Sprite>> spriteArrays;

    /**
     * the sprite for the standard projectile
     */
    private Sprite projectile;

    /**
     * The images shown in the main menu.
     */
    private List<Texture> startMenuTextures;

    /**
     * The images shown in the pause menu.
     */
    private List<Texture> pauseMenuTextures;

    /**
     * The images shown in the settings menu.
     */
    private Texture settingsTexture;

    // Buttons in the UI
    private Sprite pauseMenuButton;
    private Sprite leftSwitchWeaponButton;
    private Sprite rightSwitchWeaponButton;

    /**
     * List of sound effects the game uses.
     */
    private List<Sound> soundEffects;

    /**
     * List of fonts for menus and in-game text.
     */
    private List<BitmapFont> fonts;

    /**
     * All the different appearances of the left move button when pressed
     */
    private Array<Sprite> leftPressedButton;

    /**
     * All the different appearances of the left move button when not pressed
     */
    private Array<Sprite> leftNotPressedButton;

    /**
     * All the different appearances of the right move button when pressed
     */
    private Array<Sprite> rightPressedButton;

    /**
     * All the different appearances of the right move button when not pressed
     */
    private Array<Sprite> rightNotPressedButton;

    /**
     * The chosen map name.
     */
    private String mapString;

    /**
     * All of the available map names.
     */
    private List<String> mapNames;

    private List<Sprite> npcOrPlayerSprites;

    private List<Sprite> npcDifficultySprites;

    private List<Sprite> numberOfPlayerSprites;

    private Sprite startCustomGameButton;

    private AssetsManager(){
        spriteArrays = new HashMap<Id, Array<Sprite>>();
    }

    /**
     * our instance
     */
    private final static AssetsManager ourInstance = new AssetsManager();

    /**
     * singleton method
     */
    public static AssetsManager getInstance() {
        return ourInstance;
    }

    /**
     * @param textureName the name of the textureAtlas
     * @return the textureAtlas
     */
    public Array<Sprite> getSpriteArray(Id textureName) {
        return spriteArrays.get(textureName);
    }

    public Sprite getProjectileSprite() {
        if (projectile == null) {
            try {
                loadProjectileSprite();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return projectile;
    }

    public List<Texture> getStartMenuTextures() {
        if (startMenuTextures == null) {
            try {
                loadStartMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return startMenuTextures;
    }

    public List<Texture> getPauseMenuTextures() {
        if (pauseMenuTextures == null) {
            try {
                loadPauseMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return pauseMenuTextures;
    }

    public Sprite getPauseMenuButton() {
        if (pauseMenuButton == null) {
            try {
                loadPauseButtonTexture();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return pauseMenuButton;
    }

    public Sprite getLeftSwitchWeaponButton() {
        if (leftSwitchWeaponButton == null) {
            loadLeftSwitchWeaponButton();
        }
        return leftSwitchWeaponButton;
    }

    public Sprite getRightSwitchWeaponButton() {
        if (rightSwitchWeaponButton == null) {
            loadRightSwitchWeaponButton();
        }
        return rightSwitchWeaponButton;
    }

    public List<Sound> getSoundEffects() {
        if (soundEffects == null) {
            try {
                loadSoundEffects();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return soundEffects;
    }

    public List<BitmapFont> getFonts() {
        if (fonts == null) {
            try {
                loadFonts();
                loadCustomGameFont();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return fonts;
    }

    public Array<Sprite> getLeftPressedButtonSprites() {
        if (leftPressedButton == null) {
            try {
                loadMoveButtonTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return leftPressedButton;
    }

    public Array<Sprite> getRightPressedButtonSprites() {
        if (rightPressedButton == null) {
            try {
                loadMoveButtonTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return rightPressedButton;
    }

    public Array<Sprite> getLeftNotPressedButtonSprites() {
        if (leftNotPressedButton == null) {
            try {
                loadMoveButtonTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return leftNotPressedButton;
    }

    public Array<Sprite> getRightNotPressedButtonSprites() {
        if (rightNotPressedButton == null) {
            try {
                loadMoveButtonTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return rightNotPressedButton;
    }

    public List<String> getMapNames() {
        return mapNames;
    }

    public void loadMapNames() {
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
        greenTank.add(new Sprite(new Texture(Gdx.files.internal("TankBody/greentank.png"))));
        spriteArrays.put(Id.PLAYER1, greenTank);
    }

    private void loadPlayer2TankTextureAtlas() throws FileNotFoundException {
        Array<Sprite> whiteTank = new Array<Sprite>();
        whiteTank.add(new Sprite(new Texture(Gdx.files.internal("TankBody/whitetank.png"))));
        spriteArrays.put(Id.PLAYER2, whiteTank);
    }

    private void loadPlayer3TankTextureAtlas() throws FileNotFoundException {
        Array<Sprite> yellowTank = new Array<Sprite>();
        yellowTank.add(new Sprite(new Texture(Gdx.files.internal("TankBody/yellowtank.png"))));
        spriteArrays.put(Id.PLAYER3, yellowTank);
    }

    private void loadPlayer4TankTextureAtlas() throws FileNotFoundException {
        Array<Sprite> blueTank = new Array<Sprite>();
        blueTank.add(new Sprite(new Texture(Gdx.files.internal("TankBody/bluetank.png"))));
        spriteArrays.put(Id.PLAYER4, blueTank);
    }

    private void loadRightSwitchWeaponButton() {
        rightSwitchWeaponButton = new Sprite(new Texture(Gdx.files.internal("Arrows/RightArrow.png")));
    }

    private void loadLeftSwitchWeaponButton() {
        leftSwitchWeaponButton = new Sprite(new Texture(Gdx.files.internal("Arrows/LeftArrow.png")));
    }

    public void loadMap(String mapName) {
        FileHandle fileHandle = Gdx.files.internal("maps/" + mapName);
        mapString = fileHandle.readString();
    }

    public String getMapString() {
        return mapString;
    }

    public List<Sprite> getNpcOrPlayerSprites(){
        return npcOrPlayerSprites;
    }

    public List<Sprite> getNpcDifficultySprites(){
        return npcDifficultySprites;
    }

    public List<Sprite> getNumberOfPlayerSprites(){
        return numberOfPlayerSprites;
    }

    public Sprite getCustomGameButtonSprite(){
        return startCustomGameButton;
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

    private void loadProjectileSprite() throws FileNotFoundException {
        projectile = new Sprite(new Texture(Gdx.files.internal("Projectile.png")));
    }

    private Texture getSettingsTexture() {
        if (settingsTexture == null) {
            settingsTexture = new Texture(Gdx.files.internal("MainMenu/gear.png"));
        }
        return settingsTexture;
    }

    private void loadStartMenuTextures() throws FileNotFoundException {
        startMenuTextures = new ArrayList<Texture>();
        startMenuTextures.add(new Texture(Gdx.files.internal("MainMenu/QuickGame.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("MainMenu/WorldCS.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("MainMenu/CustomGame.png")));
        startMenuTextures.add(new Texture(Gdx.files.internal("MainMenu/HighScoreCS.png")));
        startMenuTextures.add(getSettingsTexture());
        startMenuTextures.add(new Texture(Gdx.files.internal("MainMenu/StartMenuBackgroundCopy.png")));

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

    private void loadCustomGameFont() throws FileNotFoundException{
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Noteworthy-Light.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getWidth() / 22;
        fonts.add(generator.generateFont(parameter));
        generator.dispose();
    }

    /**
     * Called right before a gamesession is launched to load all images in the UI.
     *
     * @param mapString name of the map being played
     */
    private void loadInGameAssets(String mapString) {
        try {
            loadLeftSwitchWeaponButton();
            loadRightSwitchWeaponButton();
            loadMoveButtonTextures();
            loadExplosionTextureAtlas();
            loadFonts();
            loadPauseButtonTexture();
            loadProjectileSprite();
            loadSoundEffects();
            loadMap(mapString);
        } catch (FileNotFoundException e) {
            System.out.println("Files Not Found");
        }
    }

    /**
     * Called when the app is first launched to load all images in the main menu.
     */
    public void loadStartMenuAssets() {
        try {
            loadStartMenuTextures();
        } catch (FileNotFoundException e) {
            System.out.println("Files Not Found");
        }
    }

    /**
     * Called when the user goes into the custom game menu to load all the images in it.
     */
    public void loadCustomGameAssets() {
        try {
            loadMapNames();
            loadFonts();
            loadCustomGameFont();
            loadCustomGameMenuAssets();
        } catch (FileNotFoundException e) {
            System.out.println("Files Not Found");
        }
    }

    /**
     * Loads the assets needed to start a game with a set number of players and a
     * specified map.
     *
     * @param nrOfPlayers The number of tanks in the game session.
     * @param mapString   Name of the chosen map.
     */
    public void loadNewGameAssets(int nrOfPlayers, String mapString) {
        if (nrOfPlayers == 2) {
            load2PlayerAssets();
        } else if (nrOfPlayers == 3) {
            load3PlayerAssets();
        } else if (nrOfPlayers == 4) {
            load4PlayerAssets();
        }
        loadInGameAssets(mapString);
    }

    private void loadCustomGameMenuAssets() throws FileNotFoundException{
        if(npcOrPlayerSprites == null){
            npcOrPlayerSprites = new ArrayList<Sprite>();
            npcOrPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NPCpicker1.png"))));
            npcOrPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NPCpicker2.png"))));
        }
        if(npcDifficultySprites == null){
            npcDifficultySprites = new ArrayList<Sprite>();
            npcDifficultySprites.add(new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker1.png"))));
            npcDifficultySprites.add(new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker2.png"))));
            npcDifficultySprites.add(new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker3.png"))));
            npcDifficultySprites.add(new Sprite(new Texture(Gdx.files.internal("NPCDifficultyPicker4.png"))));
        }
        if(numberOfPlayerSprites == null){
            numberOfPlayerSprites = new ArrayList<Sprite>();
            numberOfPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NumberOfPlayersPicker1.png"))));
            numberOfPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NumberOfPlayersPicker2.png"))));
            numberOfPlayerSprites.add(new Sprite(new Texture(Gdx.files.internal("NumberOfPlayersPicker3.png"))));
        }
        if(startCustomGameButton == null){
            startCustomGameButton = new Sprite(new Texture(Gdx.files.internal("StartGameButton.png")));
        }

    }

    public void dispose(){
        for (Array<Sprite> sprites : spriteArrays.values()) {
            for (Sprite s : sprites) {
                s.getTexture().dispose();
            }
        }

        if(startMenuTextures != null) {
            for (Texture t : startMenuTextures) {
                t.dispose();
            }
            settingsTexture.dispose();
        }

        if(pauseMenuTextures != null) {
            for (Texture t : pauseMenuTextures) {
                t.dispose();
            }
        }

        if(pauseMenuButton != null)
            pauseMenuButton.getTexture().dispose();

        if(leftSwitchWeaponButton != null)
            leftSwitchWeaponButton.getTexture();

        if(rightSwitchWeaponButton != null)
            rightSwitchWeaponButton.getTexture();


        if(soundEffects != null) {
            for (Sound s : soundEffects) {
                s.dispose();
            }
        }

        if(fonts != null) {
            for (BitmapFont f : fonts) {
                f.dispose();
            }
        }

        if(leftPressedButton != null) {
            for (Sprite s : leftPressedButton) {
                s.getTexture().dispose();
            }
        }
        if(leftNotPressedButton != null) {
            for (Sprite s : leftNotPressedButton) {
                s.getTexture().dispose();
            }
        }

        if(rightPressedButton != null) {
            for (Sprite s : rightPressedButton) {
                s.getTexture().dispose();
            }
        }

        if(rightNotPressedButton != null) {
            for (Sprite s : rightNotPressedButton) {
                s.getTexture().dispose();
            }
        }

        if(npcOrPlayerSprites != null) {
            for (Sprite s : npcOrPlayerSprites) {
                s.getTexture().dispose();
            }
        }

        if(npcDifficultySprites != null) {
            for (Sprite s : npcDifficultySprites) {
                s.getTexture().dispose();
            }
        }

        if(numberOfPlayerSprites != null) {
            for (Sprite s : numberOfPlayerSprites) {
                s.getTexture().dispose();
            }
        }

        if(startCustomGameButton != null)
            startCustomGameButton.getTexture().dispose();
    }
}
