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
    private static Array<Sprite> leftMoveButtonSprites = AssetsManager.getInstance().getLeftButtonSprites();
    private static Array<Sprite> rightMoveButtonSprites = AssetsManager.getInstance().getRightButtonSprites();
    private static Sprite pauseMenuButton = AssetsManager.getInstance().getPauseMenuButton();

    public static void draw(int i, Batch batch){
        leftMoveButtonSprites.get(i).setBounds(Constants.getLeftMoveButtonPosition().x, Constants.getLeftMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        leftMoveButtonSprites.get(i).draw(batch);
        rightMoveButtonSprites.get(i).setBounds(Constants.getRightMoveButtonPosition().x, Constants.getRightMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        rightMoveButtonSprites.get(i).draw(batch);
        pauseMenuButton.setBounds(Constants.getSettingsButtonPosition().x, Constants.getSettingsButtonPosition().y,
                Constants.getSettingsButtonDimension(), Constants.getSettingsButtonDimension());
        pauseMenuButton.draw(batch);
    }
}
