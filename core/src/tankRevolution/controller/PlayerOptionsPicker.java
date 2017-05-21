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
 * Handles the options of a player in the custom game view.
 */
class PlayerOptionsPicker {

    /** The current custom game view */
    private final CustomGameView view;

    /** The ID of the player that this object handles */
    private final Id id;

    /** The button to chose that the player will be a npc */
    private final Button playerNpc;

    /** The button to chose that the player will not be a npc */
    private final Button playerActual;

    /** The button to chose easy as the NPC difficulty */
    private final Button playerEasy;

    /** The button to chose medium as the NPC difficulty */
    private final Button playerMedium;

    /** The button to chose hard as the NPC difficulty */
    private final Button playerHard;

    /** The button to chose expert as the NPC difficulty */
    private final Button playerExpert;

    /** The current NPC difficulty that is chosen */
    private NPCDifficulty npcDifficulty;

    /** The status of if the player is a NPC or not */
    private boolean playerIsNpc = false;

    /**
     * Initializing
     * @param view The current custom game view. Necessary to be able to change the view
     *             so it is correct accordingly to the user input.
     * @param id The ID of the player that this picker is handling.
     */
    PlayerOptionsPicker(CustomGameView view, Id id){
        this.view = view;
        this.id = id;

        playerNpc = new Button();
        playerActual = new Button();
        playerEasy = new Button();
        playerMedium = new Button();
        playerHard = new Button();
        playerExpert = new Button();

        setBounds(id);
        addListeners(id);
    }

    /**
     * @return <code>true</code> if the player is a NPC otherwise <code>false</code>.
     */
    boolean isNPC(){
        return playerIsNpc;
    }

    /**
     * @return Returns the current NPC difficulty.
     */
    NPCDifficulty getNpcDifficulty(){
        return npcDifficulty;
    }

    /**
     * Sets the isNPC instance.
     * @param isNPC The new value of isNPC.
     */
    void setIsNPC(boolean isNPC){
        playerIsNpc = isNPC;
        view.setNPC(isNPC, id);
    }

    /**
     * Sets the NPC difficulty.
     * @param npcDifficulty The new NPC difficulty.
     */
    void setDifficulty(NPCDifficulty npcDifficulty){
        this.npcDifficulty = npcDifficulty;
        view.setDifficulty(id, npcDifficulty);
    }

    /**
     * Adds the buttons to the stage that the custom game controller instances.
     * @param stage The stage to add the buttons in.
     */
    void addToStage(Stage stage){
        stage.addActor(playerNpc);
        stage.addActor(playerActual);
        stage.addActor(playerEasy);
        stage.addActor(playerMedium);
        stage.addActor(playerHard);
        stage.addActor(playerExpert);
    }

    /**
     * Sets the bounds (Position and dimensions) of the buttons.
     * @param id To determine the y-coordinate of the buttons.
     */
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

    /**
     * Determin the index of the row that is equivalent with the ID.
     * @param id The id of the player.
     * @return The row index.
     */
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

    /**
     * Adds the listeners to the buttons.
     * @param id The ID of the player.
     */
    private void addListeners(final Id id){
        playerNpc.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playerIsNpc = true;
                npcDifficulty = NPCDifficulty.MEDIUM;
                view.setNPC(true, id);
                view.setDifficulty(id, npcDifficulty);
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
