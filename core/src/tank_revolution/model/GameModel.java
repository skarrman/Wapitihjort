package tank_revolution.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 * <p>The top level of the game, model handles the list of players and starts a new game session.
 * The model will stay the same between game sessions</p>
 */
public class GameModel {
    /**
     * The list of characters
     */
    private final List<Character> characterList;


    /**
     * For custom setup from the custom game menu
     *
     * @param characterList The list of characters received from the meny setup
     */
    public GameModel(List<Character> characterList) {
        this.characterList = characterList;
    }

    /**
     * For a quick setup
     */
    public GameModel() {
        characterList = setupQuick();
    }

    public List<Character> setupQuick() {
        List<Character> characters = new ArrayList<Character>();
        characters.add(new Player("Player1"));
        characters.add(new Player("Player2"));
        return characters;
    }

    

    public GameSession newGame() {
        return new GameSession(characterList);
    }

    public List<Character> getCharacterList() {
        return characterList;
    }


}
