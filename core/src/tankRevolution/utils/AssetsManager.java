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
 * Singleton that handles and loads all assets when needed.
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

    private AssetsManager() {
        spriteArrays = new HashMap<Id, Array<Sprite>>();
        loadMapNames();
    }
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
       /* if (nrOfPlayers == 2) {
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
        }*/
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
                loadStartMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return pauseMenuButton;
    }

    public Sprite getLeftSwitchWeaponButton() {
        if (leftSwitchWeaponButton == null) {
            try {
                loadStartMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return leftSwitchWeaponButton;
    }

    public Sprite getRightSwitchWeaponButton() {
        if (rightSwitchWeaponButton == null) {
            try {
                loadStartMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return rightSwitchWeaponButton;
    }

    public List<Sound> getSoundEffects() {
        if (soundEffects == null) {
            try {
                loadPauseMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return soundEffects;
    }

    public List<BitmapFont> getFonts() {
        if (fonts == null) {
            try {
                loadPauseMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return fonts;
    }

    public Array<Sprite> getLeftPressedButtonSprites() {
        if (leftPressedButton == null) {
            try {
                loadPauseMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return leftPressedButton;
    }

    public Array<Sprite> getRightPressedButtonSprites() {
        if (rightPressedButton == null) {
            try {
                loadPauseMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return rightPressedButton;
    }

    public Array<Sprite> getLeftNotPressedButtonSprites() {
        if (leftNotPressedButton == null) {
            try {
                loadPauseMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return leftNotPressedButton;
    }

    public Array<Sprite> getRightNotPressedButtonSprites() {
        if (rightNotPressedButton == null) {
            try {
                loadPauseMenuTextures();
            } catch (FileNotFoundException e) {
                System.out.print("File Not Found");
            }

        }
        return rightNotPressedButton;
    }

    public List<String> getMapNames() {
        return mapNames;
    }

    public String getChosenMapName(int i) {
        return mapNames.get(i);
    }

    private void loadMapNames() {
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

    private void loadCustomGameFont() {
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
            //loadCustomGameMenuAssets();
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


    /**
     * Called the first time the pause menu is opened to load all the images there.
     */
    public void loadPauseMenuAssets() {
        try {
            loadPauseMenuTextures();
        } catch (FileNotFoundException e) {
            System.out.println("Files Not Found");
        }

    }
}
