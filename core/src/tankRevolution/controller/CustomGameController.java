package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.model.Options;
import tankRevolution.services.AssetsManager;
import tankRevolution.services.Constants;
import tankRevolution.services.Id;
import tankRevolution.view.CustomGameView;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomGameController is the controller class that is responsible for handling
 * user actions in the custom game set up screen.
 */
class CustomGameController {

    /** Button that represents the right arrow in the map selector */
    private Button rightMapArrow;

    /** Button that represents the left arrow in the map selector */
    private Button leftMapArrow;

    /** Button that sets the number of players to two */
    private Button numberOfPlayersTwo;

    /** Button that sets the number of players to three */
    private Button numberOfPlayersThree;

    /** Button that sets the number of players to four */
    private Button numberOfPlayersFour;

    /** The options picker that handles options for player one */
    private PlayerOptionsPicker player1;

    /** The options picker that handles options for player two */
    private PlayerOptionsPicker player2;

    /** The options picker that handles options for player three */
    private PlayerOptionsPicker player3;

    /** The options picker that handles options for player four */
    private PlayerOptionsPicker player4;

    /** Button that starts the game with the selected options */
    private Button startGameButton;

    /** The stage where all the buttons is in */
    private Stage stage;

    /** The index of the selected map in the map names array */
    private int selectedMap = 0;

    /** Array of names of all the available maps */
    private List<String> mapNames;

    /** The amount of players. Start with value of two because it the least amout of players posible to start a game */
    private int numberOfPlayers = 2;


    /**
     * The only constructor in this class. It initialize everything and calling
     * methods to place all the buttons in the right place.
     *
     * @param gameHolder The current game holder. Is necessary to be able to switch view.
     * @param view The current view the represent the custom game screen.
     */
    CustomGameController(GameHolder gameHolder, CustomGameView view){
        rightMapArrow = new Button();
        leftMapArrow = new Button();

        numberOfPlayersTwo = new Button();
        numberOfPlayersThree = new Button();
        numberOfPlayersFour = new Button();

        player1 = new PlayerOptionsPicker(view, Id.PLAYER1);
        player2 = new PlayerOptionsPicker(view, Id.PLAYER2);
        player3 = new PlayerOptionsPicker(view, Id.PLAYER3);
        player4 = new PlayerOptionsPicker(view, Id.PLAYER4);


        startGameButton = new Button();

        stage = new Stage();

        mapNames = AssetsManager.getInstance().getMapNames();

        setUpButtonBounds();
        setUpButtonListener(gameHolder, view);
        addButtonsToStage();
        setStartValues();
    }

    /**
     * @return The stage that all the buttons are in.
     */
    Stage getStage() {
        return stage;
    }

    /**
     * Setting upp all the button's bounds (position, width and height).
     */
    private void setUpButtonBounds(){
        float pickerHeight = Constants.getPickerHeight();
        float numberOfPlayerWidth = Constants.getNumberOfPlayerWidth();

        float arrowSize = Constants.getWeaponArrowDimension();
        Vector2 rightArrowPos = Constants.getRightMapArrowPos();
        rightMapArrow.setBounds(rightArrowPos.x, rightArrowPos.y, arrowSize, arrowSize);
        Vector2 leftArrowPos = Constants.getLeftMapArrowPos();
        leftMapArrow.setBounds(leftArrowPos.x, leftArrowPos.y, arrowSize, arrowSize);

        Vector2 numberOfPLayerPos = Constants.getNumberOfPlayersPos();
        numberOfPlayersTwo.setBounds(numberOfPLayerPos.x, numberOfPLayerPos.y, numberOfPlayerWidth/3, pickerHeight);
        numberOfPlayersThree.setBounds(numberOfPLayerPos.x + numberOfPlayerWidth/3, numberOfPLayerPos.y, numberOfPlayerWidth/3, pickerHeight);
        numberOfPlayersFour.setBounds(numberOfPLayerPos.x + 2 * numberOfPlayerWidth/3, numberOfPLayerPos.y, numberOfPlayerWidth/3, pickerHeight);

        Vector2 startCustomGamePos = Constants.getStartCustomGamePos();
        float startCustomGameWidth = Constants.getStartCustomGameWidth();
        float startCustomGameHeight = Constants.getStartCustomGameHeight();

        startGameButton.setBounds(startCustomGamePos.x, startCustomGamePos.y, startCustomGameWidth, startCustomGameHeight);
    }

    /**
     * Setting up all the listeners for the buttons.
     * @param gameHolder the current game holder
     * @param view the current Custom game view.
     */
    private void setUpButtonListener(final GameHolder gameHolder, final CustomGameView view){
        leftMapArrow.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(selectedMap == 0){
                    selectedMap = mapNames.size()-1;
                }else {
                    selectedMap--;
                }
                view.setPreviousMap();
                return true;
            }
        });
        rightMapArrow.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedMap = (selectedMap + 1) % mapNames.size();
                view.setNextMap();
                return true;
            }
        });
        numberOfPlayersTwo.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numberOfPlayers = 2;
                view.setNumberOfPlayers(2);
                return true;
            }
        });
        numberOfPlayersThree.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numberOfPlayers = 3;
                view.setNumberOfPlayers(3);
                player3.setIsNPC(true);
                player3.setDifficulty(NPCDifficulty.MEDIUM);
                return true;
            }
        });
        numberOfPlayersFour.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numberOfPlayers = 4;
                view.setNumberOfPlayers(4);
                player3.setIsNPC(true);
                player3.setDifficulty(NPCDifficulty.MEDIUM);
                player4.setIsNPC(true);
                player4.setDifficulty(NPCDifficulty.MEDIUM);
                return true;
            }
        });

        startGameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Options options = new Options();
                List<NPCDifficulty> npcDifficulties = new ArrayList<NPCDifficulty>();
                int numberOfNpc = 0;
                if(player1.isNPC()){
                    numberOfNpc++;
                    npcDifficulties.add(player1.getNpcDifficulty());
                }
                if(player2.isNPC()){
                    numberOfNpc++;
                    npcDifficulties.add(player2.getNpcDifficulty());
                }
                if(numberOfPlayers > 2){
                    if(player3.isNPC()){
                        numberOfNpc++;
                        npcDifficulties.add(player3.getNpcDifficulty());
                    }
                    if(numberOfPlayers == 4){
                        if(player4.isNPC()){
                            numberOfNpc++;
                            npcDifficulties.add(player4.getNpcDifficulty());
                        }
                    }
                }
                options.setUpCustom(numberOfPlayers, numberOfNpc, npcDifficulties, mapNames.get(selectedMap));
                gameHolder.startNewGame(options);
                return true;
            }
        });
    }

    /**
     * Adding all the elements to the stage.
     */
    private void addButtonsToStage(){
        stage.addActor(leftMapArrow);
        stage.addActor(rightMapArrow);
        stage.addActor(numberOfPlayersTwo);
        stage.addActor(numberOfPlayersThree);
        stage.addActor(numberOfPlayersFour);
        stage.addActor(startGameButton);
        player1.addToStage(stage);
        player2.addToStage(stage);
        player3.addToStage(stage);
        player4.addToStage(stage);
    }

    /**
     * Setting up the start values of the switchers so that the
     * good defaults are the same as quick games options.
     */
    private void setStartValues(){
        player1.setIsNPC(false);
        player2.setIsNPC(true);
        player2.setDifficulty(NPCDifficulty.MEDIUM);
    }
}
