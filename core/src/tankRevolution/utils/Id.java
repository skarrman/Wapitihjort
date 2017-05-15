package tankRevolution.utils;

/**
 * Created by antonhagermalm on 2017-04-25.
 */
public enum Id {
    EXPLOSION,
    PLAYER1,
    PLAYER2,
    PLAYER3,
    PLAYER4;

    public static Id get(int i){
        switch (i){
            case 1:
                return PLAYER1;
            case 2:
                return PLAYER2;
            case 3:
                return PLAYER3;
            case 4:
                return PLAYER4;
            default:
                return PLAYER1;
        }
    }
}
