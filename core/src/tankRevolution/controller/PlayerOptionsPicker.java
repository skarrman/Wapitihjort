package tankRevolution.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import tankRevolution.model.NPCDifficulty;
import tankRevolution.utils.Constants;
import tankRevolution.utils.Id;
import tankRevolution.view.CustomGameView;

/**
 * Created by simonkarrman on 2017-05-17.
 */
public class PlayerOptionsPicker {

    private CustomGameView view;
    private Id id;

    private Button playerNpc;
    private Button playerActual;
    private Button playerEasy;
    private Button playerMedium;
    private Button playerHard;
    private Button playerExpert;

    private NPCDifficulty npcDifficulty;

    boolean playerIsNpc = false;

    public PlayerOptionsPicker(CustomGameView view, Id id){
        this.view = view;
        this.id = id;

        playerNpc = new Button();
        playerActual = new Button();
        playerEasy = new Button();
        playerMedium = new Button();
        playerHard = new Button();
        playerExpert = new Button();

        setBounds(id);
        addListeners(view, id);
    }

    public boolean isNPC(){
        return playerIsNpc;
    }

    public NPCDifficulty getNpcDifficulty(){
        return npcDifficulty;
    }

    public void setIsNPC(boolean isNPC){
        playerIsNpc = isNPC;
        view.setNPC(isNPC, id);
    }

    public void setDifficulty(NPCDifficulty npcDifficulty){
        this.npcDifficulty = npcDifficulty;
        view.setDifficulty(id, npcDifficulty);
    }

    public void addToStage(Stage stage){
        stage.addActor(playerNpc);
        stage.addActor(playerActual);
        stage.addActor(playerEasy);
        stage.addActor(playerMedium);
        stage.addActor(playerHard);
        stage.addActor(playerExpert);
    }

    private void setBounds(Id id){
        float pickerHeight = Constants.getPickerHeight();
        float npcOrPlayerX = Constants.getNpcOrPlayerX();
        float npcOrPlayerWidth = Constants.getNpcOrPlayerWidth();
        float npcDifficultyX = Constants.getNpcDifficultyX();
        float npcDifficultyWidth = Constants.getNpcDifficultyWidth();
        float y = Constants.getPickerY(getIndex(id));
        playerNpc.setBounds(npcOrPlayerX, y, npcOrPlayerWidth/2, pickerHeight);
        playerActual.setBounds(npcOrPlayerX + npcOrPlayerWidth/2, y, npcOrPlayerWidth/2, pickerHeight);
        playerEasy.setBounds(npcDifficultyX, y, npcDifficultyWidth/4, pickerHeight);
        playerMedium.setBounds(npcDifficultyX + npcDifficultyWidth/4, y, npcDifficultyWidth/4, pickerHeight);
        playerHard.setBounds(npcDifficultyX + 2 * npcDifficultyWidth/4, y, npcDifficultyWidth/4, pickerHeight);
        playerExpert.setBounds(npcDifficultyX + 3 * npcDifficultyWidth/4, y, npcDifficultyWidth/4, pickerHeight);
    }

    private int getIndex(Id id){
        switch (id){
            case PLAYER1:
                return 0;
            case PLAYER2:
                return 1;
            case PLAYER3:
                return 2;
            case PLAYER4:
                return 3;
            default:
                return -1;
        }
    }

    private void addListeners(final CustomGameView view, final Id id){
        playerNpc.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playerIsNpc = true;
                view.setNPC(true, id);
                return true;
            }
        });
        playerActual.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playerIsNpc = false;
                view.setNPC(false, id);
                return true;
            }
        });
        playerEasy.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(playerIsNpc){
                    npcDifficulty = NPCDifficulty.EASY;
                    view.setDifficulty(id, npcDifficulty);
                }
                return true;
            }
        });
        playerMedium.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(playerIsNpc){
                    npcDifficulty = NPCDifficulty.MEDIUM;
                    view.setDifficulty(id, npcDifficulty);
                }
                return true;
            }
        });
        playerHard.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(playerIsNpc){
                    npcDifficulty = NPCDifficulty.HARD;
                    view.setDifficulty(id, npcDifficulty);
                }
                return true;
            }
        });
        playerExpert.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(playerIsNpc){
                    npcDifficulty = NPCDifficulty.SUPERHARD;
                    view.setDifficulty(id, npcDifficulty);
                }
                return true;
            }
        });
    }

}
