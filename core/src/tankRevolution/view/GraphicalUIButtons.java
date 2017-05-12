package tankRevolution.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

/**
 * Created by JakobErlandsson on 2017-05-12.
 */
public class GraphicalUIButtons {
    private static Array<Sprite> leftMoveButtonPressedSprites = AssetsManager.getInstance().getLeftPressedButtonSprites();
    private static Array<Sprite> rightMoveButtonPressedSprites = AssetsManager.getInstance().getRightPressedButtonSprites();
    private static Array<Sprite> leftMoveButtonNotPressedSprites = AssetsManager.getInstance().getLeftNotPressedButtonSprites();
    private static Array<Sprite> rightMoveButtonNotPressedSprites = AssetsManager.getInstance().getRightNotPressedButtonSprites();
    private static Sprite pauseMenuButton = AssetsManager.getInstance().getPauseMenuButton();

    public static void draw(int i, Batch batch, boolean pressed){
        if(pressed) {
            leftMoveButtonPressedSprites.get(i).setBounds(Constants.getLeftMoveButtonPosition().x, Constants.getLeftMoveButtonPosition().y,
                    Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
            leftMoveButtonPressedSprites.get(i).draw(batch);
            rightMoveButtonPressedSprites.get(i).setBounds(Constants.getRightMoveButtonPosition().x, Constants.getRightMoveButtonPosition().y,
                    Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
            rightMoveButtonPressedSprites.get(i).draw(batch);
        }else{
            leftMoveButtonNotPressedSprites.get(i).setBounds(Constants.getLeftMoveButtonPosition().x, Constants.getLeftMoveButtonPosition().y,
                    Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
            leftMoveButtonNotPressedSprites.get(i).draw(batch);
            rightMoveButtonNotPressedSprites.get(i).setBounds(Constants.getRightMoveButtonPosition().x, Constants.getRightMoveButtonPosition().y,
                    Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
            rightMoveButtonNotPressedSprites.get(i).draw(batch);
        }
        pauseMenuButton.setBounds(Constants.getSettingsButtonPosition().x, Constants.getSettingsButtonPosition().y,
                Constants.getSettingsButtonDimension(), Constants.getSettingsButtonDimension());
        pauseMenuButton.draw(batch);
    }
}
