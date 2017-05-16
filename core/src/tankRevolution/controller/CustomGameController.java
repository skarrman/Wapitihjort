package tankRevolution.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.GameHolder;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.model.Options;
import tankRevolution.utils.Constants;
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

    private Button player1Npc;
    private Button player1Actual;
    private Button player1Easy;
    private Button player1Medium;
    private Button player1Hard;
    private Button player1Expert;

    private Button player2Npc;
    private Button player2Actual;
    private Button player2Easy;
    private Button player2Medium;
    private Button player2Hard;
    private Button player2Expert;

    private Button player3Npc;
    private Button player3Actual;
    private Button player3Easy;
    private Button player3Medium;
    private Button player3Hard;
    private Button player3Expert;

    private Button player4Npc;
    private Button player4Actual;
    private Button player4Easy;
    private Button player4Medium;
    private Button player4Hard;
    private Button player4Expert;

    private Button startGameButton;

    private Stage stage;

    private int selectedMap = 0;

    private int numnberOfPlayers = 2;

    private boolean player1IsNpc = false;

    private NPCDifficulty player1Difficulty;

    private boolean player2IsNpc = true;

    private NPCDifficulty player2Difficulty = NPCDifficulty.MEDIUM;

    private boolean player3IsNpc = false;

    private NPCDifficulty player3Difficulty;

    private boolean player4IsNpc = false;

    private NPCDifficulty player4Difficulty;


    public CustomGameController(GameHolder gameHolder, CustomGameView view){
        rightMapArrow = new Button();
        leftMapArrow = new Button();

        numberOfPlayersTwo = new Button();
        numberOfPlayersThree = new Button();
        numberOfPlayersFour = new Button();

        player1Npc = new Button();
        player1Actual = new Button();
        player1Easy = new Button();
        player1Medium = new Button();
        player1Hard = new Button();
        player1Expert = new Button();

        player2Npc = new Button();
        player2Actual = new Button();
        player2Easy = new Button();
        player2Medium = new Button();
        player2Hard = new Button();
        player2Expert = new Button();

        player3Npc = new Button();
        player3Actual = new Button();
        player3Easy = new Button();
        player3Medium = new Button();
        player3Hard = new Button();
        player3Expert = new Button();


        player4Npc = new Button();
        player4Actual = new Button();
        player4Easy = new Button();
        player4Medium = new Button();
        player4Hard = new Button();
        player4Expert = new Button();


        startGameButton = new Button();

        stage = new Stage();

        setUpButtonBounds();
        setUpButtonListener(gameHolder, view);
        addButtonsToStage();
    }

    public Stage getStage() {
        return stage;
    }

    private void setUpButtonBounds(){
        float pickerHeight = Constants.getPickerHeight();
        float numberOfPlayerWidth = Constants.getNumberOfPlayerWidth();
        float npcOrPlayerWidth = Constants.getNpcOrPlayerWidth();
        float npcDifficultyWidth = Constants.getNpcDifficultyWidth();

        float npcOrPlayerX = Constants.getNpcOrPlayerX();
        float npcDifficultyX = Constants.getNpcDifficultyX();

        float arrowSize = Constants.getWeaponArrowDimension();
        Vector2 rightArrowPos = Constants.getRightWeaponPosition();
        rightMapArrow.setBounds(rightArrowPos.x, rightArrowPos.y, arrowSize, arrowSize);
        Vector2 leftArrowPos = Constants.getLeftWeaponPosition();
        leftMapArrow.setBounds(leftArrowPos.x, leftArrowPos.y, arrowSize, arrowSize);

        Vector2 numberOfPLayerPos = Constants.getNumberOfPlayersPos();
        numberOfPlayersTwo.setBounds(numberOfPLayerPos.x, numberOfPLayerPos.y, numberOfPlayerWidth/3, pickerHeight);
        numberOfPlayersThree.setBounds(numberOfPLayerPos.x + numberOfPlayerWidth/3, numberOfPLayerPos.y, numberOfPlayerWidth/3, pickerHeight);
        numberOfPlayersFour.setBounds(numberOfPLayerPos.x + 2 * numberOfPlayerWidth/3, numberOfPLayerPos.y, numberOfPlayerWidth/3, pickerHeight);

        float player1Y = Constants.getPickerY(0);
        player1Npc.setBounds(npcOrPlayerX, player1Y, npcOrPlayerWidth/2, pickerHeight);
        player1Actual.setBounds(npcOrPlayerX + npcOrPlayerWidth/2, player1Y, npcOrPlayerWidth/2, pickerHeight);
        player1Easy.setBounds(npcDifficultyX, player1Y, npcDifficultyWidth/4, pickerHeight);
        player1Medium.setBounds(npcDifficultyX + npcDifficultyWidth/4, player1Y, npcDifficultyWidth/4, pickerHeight);
        player1Hard.setBounds(npcDifficultyX + 2 * npcDifficultyWidth/4, player1Y, npcDifficultyWidth/4, pickerHeight);
        player1Expert.setBounds(npcDifficultyX + 3 * npcDifficultyWidth/4, player1Y, npcDifficultyWidth/4, pickerHeight);

        float player2Y = Constants.getPickerY(1);
        player2Npc.setBounds(npcOrPlayerX, player2Y, npcOrPlayerWidth/2, pickerHeight);
        player2Actual.setBounds(npcOrPlayerX + npcOrPlayerWidth/2, player2Y, npcOrPlayerWidth/2, pickerHeight);
        player2Easy.setBounds(npcDifficultyX, player2Y, npcDifficultyWidth/4, pickerHeight);
        player2Medium.setBounds(npcDifficultyX + npcDifficultyWidth/4, player2Y, npcDifficultyWidth/4, pickerHeight);
        player2Hard.setBounds(npcDifficultyX + 2 * npcDifficultyWidth/4, player2Y, npcDifficultyWidth/4, pickerHeight);
        player2Expert.setBounds(npcDifficultyX + 3 * npcDifficultyWidth/4, player2Y, npcDifficultyWidth/4, pickerHeight);

        float player3Y = Constants.getPickerY(2);
        player3Npc.setBounds(npcOrPlayerX, player3Y, npcOrPlayerWidth/2, pickerHeight);
        player3Actual.setBounds(npcOrPlayerX + npcOrPlayerWidth/2, player3Y, npcOrPlayerWidth/2, pickerHeight);
        player3Easy.setBounds(npcDifficultyX, player3Y, npcDifficultyWidth/4, pickerHeight);
        player3Medium.setBounds(npcDifficultyX + npcDifficultyWidth/4, player3Y, npcDifficultyWidth/4, pickerHeight);
        player3Hard.setBounds(npcDifficultyX + 2 * npcDifficultyWidth/4, player3Y, npcDifficultyWidth/4, pickerHeight);
        player3Expert.setBounds(npcDifficultyX + 3 * npcDifficultyWidth/4, player3Y, npcDifficultyWidth/4, pickerHeight);

        float player4Y = Constants.getPickerY(3);
        player4Npc.setBounds(npcOrPlayerX, player4Y, npcOrPlayerWidth/2, pickerHeight);
        player4Actual.setBounds(npcOrPlayerX + npcOrPlayerWidth/2, player4Y, npcOrPlayerWidth/2, pickerHeight);
        player4Easy.setBounds(npcDifficultyX, player4Y, npcDifficultyWidth/4, pickerHeight);
        player4Medium.setBounds(npcDifficultyX + npcDifficultyWidth/4, player4Y, npcDifficultyWidth/4, pickerHeight);
        player4Hard.setBounds(npcDifficultyX + 2 * npcDifficultyWidth/4, player4Y, npcDifficultyWidth/4, pickerHeight);
        player4Expert.setBounds(npcDifficultyX + 3 * npcDifficultyWidth/4, player4Y, npcDifficultyWidth/4, pickerHeight);

        Vector2 startCustomGamePos = Constants.getStartCustomGamePos();
        float startCustomGameWidth = Constants.getStartCustomGameWidth();
        float startCustomGameHeight = Constants.getStartCustomGameHeight();

        startGameButton.setBounds(startCustomGamePos.x, startCustomGamePos.y, startCustomGameWidth, startCustomGameHeight);
    }

    private void setUpButtonListener(final GameHolder gameHolder, final CustomGameView view){
        leftMapArrow.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedMap--;
                System.out.println("leftArrow");
                return true;
            }
        });
        rightMapArrow.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedMap++;
                System.out.println("rightArrow");
                return true;
            }
        });
        numberOfPlayersTwo.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numnberOfPlayers = 2;
                System.out.println("two");
                return true;
            }
        });
        numberOfPlayersThree.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numnberOfPlayers = 3;
                System.out.println("three");
                return true;
            }
        });
        numberOfPlayersFour.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                numnberOfPlayers = 4;
                System.out.println("four");
                return true;
            }
        });
        player1Npc.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player1IsNpc = true;
                System.out.println("p1NPC");
                return true;
            }
        });
        player1Actual.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player1IsNpc = false;
                System.out.println("p1Actual");
                return true;
            }
        });
        player1Easy.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player1IsNpc){
                    player1Difficulty = NPCDifficulty.EASY;
                }
                System.out.println("p1Easy");
                return true;
            }
        });
        player1Medium.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player1IsNpc){
                    player1Difficulty = NPCDifficulty.MEDIUM;
                }
                System.out.println("p1Medium");
                return true;
            }
        });
        player1Hard.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player1IsNpc){
                    player1Difficulty = NPCDifficulty.HARD;
                }
                System.out.println("p1Hard");
                return true;
            }
        });
        player1Expert.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player1IsNpc){
                    player1Difficulty = NPCDifficulty.SUPERHARD;
                }
                System.out.println("p1Expert");
                return true;
            }
        });
        player2Npc.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player2IsNpc = true;
                System.out.println("p2NPC");
                return true;
            }
        });
        player2Actual.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player2IsNpc = false;
                System.out.println("p2Actual");
                return true;
            }
        });
        player2Easy.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player2IsNpc){
                    player1Difficulty = NPCDifficulty.EASY;
                }
                System.out.println("p2Easy");
                return true;
            }
        });
        player2Medium.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player2IsNpc){
                    player1Difficulty = NPCDifficulty.MEDIUM;
                }
                System.out.println("p2Medium");
                return true;
            }
        });
        player2Hard.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player2IsNpc){
                    player1Difficulty = NPCDifficulty.HARD;
                }
                System.out.println("p2Hard");
                return true;
            }
        });
        player2Expert.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player2IsNpc){
                    player1Difficulty = NPCDifficulty.SUPERHARD;
                }
                System.out.println("p2Expert");
                return true;
            }
        });
        player3Npc.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player3IsNpc = true;
                System.out.println("p3NPC");
                return true;
            }
        });
        player3Actual.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player3IsNpc = false;
                System.out.println("p3Actual");
                return true;
            }
        });
        player3Easy.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player3IsNpc){
                    player1Difficulty = NPCDifficulty.EASY;
                }
                System.out.println("p3Easy");
                return true;
            }
        });
        player3Medium.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player3IsNpc){
                    player1Difficulty = NPCDifficulty.MEDIUM;
                }
                System.out.println("p3Medium");
                return true;
            }
        });
        player3Hard.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player3IsNpc){
                    player1Difficulty = NPCDifficulty.HARD;
                }
                System.out.println("p3Hard");
                return true;
            }
        });
        player3Expert.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player3IsNpc){
                    player1Difficulty = NPCDifficulty.SUPERHARD;
                }
                System.out.println("p3Expert");
                return true;
            }
        });
        player4Npc.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player4IsNpc = true;
                System.out.println("p4NPC");
                return true;
            }
        });
        player4Actual.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player4IsNpc = false;
                System.out.println("p4Actual");
                return true;
            }
        });
        player4Easy.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player4IsNpc){
                    player1Difficulty = NPCDifficulty.EASY;
                }
                System.out.println("p4Easy");
                return true;
            }
        });
        player4Medium.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player4IsNpc){
                    player1Difficulty = NPCDifficulty.MEDIUM;
                }
                System.out.println("p4Medium");
                return true;
            }
        });
        player4Hard.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player4IsNpc){
                    player1Difficulty = NPCDifficulty.HARD;
                }
                System.out.println("p4Hard");
                return true;
            }
        });
        player4Expert.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player4IsNpc){
                    player1Difficulty = NPCDifficulty.SUPERHARD;
                }
                System.out.println("p4Expert");
                return true;
            }
        });

        startGameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Options options = new Options();
                List<NPCDifficulty> npcDifficulties = new ArrayList<NPCDifficulty>();
                int numberOfNpc = 0;
                if(player1IsNpc){
                    numberOfNpc++;
                    npcDifficulties.add(player1Difficulty);
                }
                if(player2IsNpc){
                    numberOfNpc++;
                    npcDifficulties.add(player2Difficulty);
                }
                if(numnberOfPlayers > 2){
                    if(player3IsNpc){
                        numberOfNpc++;
                        npcDifficulties.add(player3Difficulty);
                    }
                    if(numnberOfPlayers == 4){
                        if(player4IsNpc){
                            numberOfNpc++;
                            npcDifficulties.add(player4Difficulty);
                        }
                    }
                }
                options.setUpCustom(numnberOfPlayers, numberOfNpc, npcDifficulties, "testMap");
                gameHolder.startNewGame(options.newGame());
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
       stage.addActor(player1Npc);
       stage.addActor(player1Actual);
       stage.addActor(player1Easy);
       stage.addActor(player1Medium);
       stage.addActor(player1Hard);
       stage.addActor(player1Expert);
       stage.addActor(player2Npc);
       stage.addActor(player2Actual);
       stage.addActor(player2Easy);
       stage.addActor(player2Medium);
       stage.addActor(player2Hard);
       stage.addActor(player2Expert);
       stage.addActor(player3Npc);
       stage.addActor(player3Actual);
       stage.addActor(player3Easy);
       stage.addActor(player3Medium);
       stage.addActor(player3Hard);
       stage.addActor(player3Expert);
       stage.addActor(player4Npc);
       stage.addActor(player4Actual);
       stage.addActor(player4Easy);
       stage.addActor(player4Medium);
       stage.addActor(player4Hard);
       stage.addActor(player4Expert);
       stage.addActor(startGameButton);
    }
}
