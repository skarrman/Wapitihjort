package tankRevolution.model;

import tankRevolution.services.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 * <p>The top level of the game, model handles the list of players and starts a new game session.
 * The model will stay the same between game sessions</p>
 */
public class Options {
    /**
     * The list of characters
     */
    private final List<Character> characterList;
    private String mapName;

    /**
     * For a quick setup
     */
    public Options() {
        characterList = new ArrayList<>();
    }

    public void setupQuick() {
        characterList.add(CharacterFactory.newPlayer(Id.PLAYER1));
        characterList.add(CharacterFactory.newNPC(Id.PLAYER2, NPCDifficulty.SUPERHARD));
        mapName = "Burning Desert Wolf";
    }

    /**
     * Setting up a custom game.
     * @param numberOfPlayers The number of players in the game.
     * @param numberOfNPCs Number of NPCs in the game.
     * @param npcDifficulties The different NPC difficulties.
     * @param mapName The name of the map.
     */
    public void setUpCustom(int numberOfPlayers, int numberOfNPCs, List<NPCDifficulty> npcDifficulties, String mapName) {
        this.mapName = mapName;
        if (numberOfNPCs < numberOfPlayers) {
            characterList.add(CharacterFactory.newPlayer(Id.PLAYER1));
        } else {
            characterList.add(CharacterFactory.newNPC(Id.PLAYER1, npcDifficulties.remove(0)));
        }
        for (int i = 1; i < numberOfPlayers; i++) {
            if (numberOfNPCs > 0) {
                characterList.add(CharacterFactory.newNPC(Id.get(i + 1), npcDifficulties.remove(0)));
                numberOfNPCs--;
            } else {
                characterList.add(CharacterFactory.newPlayer(Id.get(i + 1)));
            }
        }

    }

    /**
     * @return The chosen map name.
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Makes a defensive copy of the character list.
     * @return The copied list.
     */
    private List<Character> defenciveCopiedCharacterList() {
        List<Character> safeCharacters = new ArrayList<>();
        for (Character character : characterList) {
            safeCharacters.add(CharacterFactory.defenciveCopyCharacter(character));
        }
        return safeCharacters;
    }

    /**
     * Returns a new model with the current settings.
     * @return The new game.
     */
    public TankRevolution newGame() {
        return new TankRevolution(defenciveCopiedCharacterList());
    }

}
