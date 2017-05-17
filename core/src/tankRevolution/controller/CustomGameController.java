package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.model.Options;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;
import tankRevolution.utils.Id;
import tankRevolution.view.CustomGameView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simonkarrman on 2017-05-16.
 */
public class CustomGameController {
    private Button rightMapArrow;
    private Button leftMapArrow;

    private Button numberOfPlayersTwo;
    private Button numberOfPlayersThree;
    private Button numberOfPlayersFour;

    private PlayerOptionsPicker player1;

    private PlayerOptionsPicker player2;

    private PlayerOptionsPicker player3;

    private PlayerOptionsPicker player4;

    private Button startGameButton;

    private Stage stage;

    private int selectedMap = 0;

    List<String> mapNames;

    private int numberOfPlayers = 2;



    public CustomGameController(GameHolder gameHolder, CustomGameView view){
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

    public Stage getStage() {
        return stage;
    }

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

    private void setUpButtonListener(final GameHolder gameHolder, final CustomGameView view){
        leftMapArrow.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedMap = (selectedMap - 1) % mapNames.size();
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
                gameHolder.startNewGame(options.newGame(), mapNames.get(selectedMap));
                return true;
            }
        });
    }

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

    private void setStartValues(){
        player1.setIsNPC(false);
        player2.setIsNPC(true);
        player2.setDifficulty(NPCDifficulty.MEDIUM);
    }
}
