package tank_revolution.model;

import java.util.List;

/**
 * Created by antonhagermalm on 2017-03-30.
 */
public class GameModel {private List<Character> characterList;

    GameModel(List<Character> characterList){
        this.characterList = characterList;
    }

    public GameSession newGame(){
        return new GameSession(characterList);
    }


}
