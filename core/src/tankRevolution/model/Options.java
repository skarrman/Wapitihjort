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
        characterList = setupQuick();
    }

    public List<Character> setupQuick() {
        List<Character> characters = new ArrayList<Character>();
        characters.add(CharacterFactory.newPlayer(Id.PLAYER1));
        characters.add(CharacterFactory.newNPC(Id.PLAYER2, NPCDifficulty.SUPERHARD));
        return characters;
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
