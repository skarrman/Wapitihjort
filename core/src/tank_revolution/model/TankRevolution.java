package tank_revolution.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 * <p>The top level of the game, model handles the list of players and starts a new game session.
 * The model will stay the same between game sessions</p>
 */
public class TankRevolution {
    /**
     * The list of characters
     */
    private final List<Character> characterList;


    /**
     * For custom setup from the custom game menu
     *
     * @param characterList The list of characters received from the meny setup
     */
    public TankRevolution(List<Character> characterList) {
        this.characterList = characterList;
    }

    /**
     * For a quick setup
     */
    public TankRevolution() {
        characterList = setupQuick();
    }

    public List<Character> setupQuick() {
        List<Character> characters = new ArrayList<Character>();
        characters.add(CharacterFactory.newPlayer("Player1"));
        characters.add(CharacterFactory.newNPC("Player2", 2));
        return characters;
    }

    private List<Character> defenciveCopiedCharacterList(){
        List<Character> safeCharacters = new ArrayList<Character>();
        for (Character character : characterList){
            safeCharacters.add(CharacterFactory.defenciveCopyCharacter(character));
        }
        return safeCharacters;
    }

    public GameSession newGame() {
        return new GameSession(defenciveCopiedCharacterList());
    }

    public List<Character> getCharacterList() {
        return characterList;
    }


}
