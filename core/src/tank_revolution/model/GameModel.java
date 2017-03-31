package tank_revolution.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class GameModel {
    private final List<Character> characterList;


    //For custom setup from the main menu
    public GameModel(List<Character> characterList){
        this.characterList = characterList;
    }

    //For a quickGame
    public GameModel(){
        characterList = setupQuick();
    }

    public List<Character> setupQuick(){
        List<Character> characters = new ArrayList<Character>();
        characters.add(new Player("Player1"));
        characters.add(new Player("Player2"));
        return characters;
    }


    public GameSession newGame(){
        return new GameSession(characterList);
    }

    public List<Character> getCharacterList() {
        return characterList;
    }


}
