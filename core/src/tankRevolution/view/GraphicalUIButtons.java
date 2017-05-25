package tankRevolution.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import tankRevolution.services.AssetsManager;
import tankRevolution.services.Constants;

/**
 * Static class holding the dimension and positions of the UI buttons in the game.
 */
class GraphicalUIButtons {
    private static final Array<Sprite> leftMoveButtonPressedSprites = AssetsManager.getInstance().getLeftPressedButtonSprites();
    private static final Array<Sprite> rightMoveButtonPressedSprites = AssetsManager.getInstance().getRightPressedButtonSprites();
    private static final Array<Sprite> leftMoveButtonNotPressedSprites = AssetsManager.getInstance().getLeftNotPressedButtonSprites();
    private static final Array<Sprite> rightMoveButtonNotPressedSprites = AssetsManager.getInstance().getRightNotPressedButtonSprites();
    private static final Sprite pauseMenuButton = AssetsManager.getInstance().getPauseMenuButton();

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
