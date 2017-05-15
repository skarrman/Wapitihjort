package tankRevolution.model;

import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Id;

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


    /**
     * For custom setup from the custom game menu
     *
     * @param characterList The list of characters received from the meny setup
     */
    public Options(List<Character> characterList) {
        this.characterList = characterList;
    }

    /**
     * For a quick setup
     */
    public Options() {
        characterList = new ArrayList<Character>();

    }

    public void setupQuick() {
        characterList.add(CharacterFactory.newNPC(Id.PLAYER1, NPCDifficulty.EASY));
        characterList.add(CharacterFactory.newNPC(Id.PLAYER2, NPCDifficulty.SUPERHARD));
    }

    public List<Character> setUpCustom(int numberOfPlayers, int numberOfNPCs,List<NPCDifficulty> npcDifficulties, String map){
        characterList.add(CharacterFactory.newPlayer(Id.PLAYER1));
        for(int i = 1; i < numberOfPlayers; i++){
            if(numberOfNPCs > 0){
                characterList.add(CharacterFactory.newNPC(Id.get(i+1), npcDifficulties.remove(0)));
            }else{
                characterList.add(CharacterFactory.newPlayer(Id.get(i+1)));
            }
        }
        return characterList;

    }

    private List<Character> defenciveCopiedCharacterList(){
        List<Character> safeCharacters = new ArrayList<Character>();
        for (Character character : characterList){
            safeCharacters.add(CharacterFactory.defenciveCopyCharacter(character));
        }
        return safeCharacters;
    }

    public TankRevolution newGame() {
        return new TankRevolution(defenciveCopiedCharacterList());
    }

    public List<Character> getCharacterList() {
        return characterList;
    }


}
