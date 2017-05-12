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

    public static void draw(int i, Batch batch){
        leftMoveButtonSprites.get(i).setBounds(Constants.getLeftMoveButtonPosition().x, Constants.getLeftMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        leftMoveButtonSprites.get(i).draw(batch);
        rightMoveButtonSprites.get(i).setBounds(Constants.getRightMoveButtonPosition().x, Constants.getRightMoveButtonPosition().y,
                Constants.getMoveButtonWidth(), Constants.getMoveButtonHeight());
        rightMoveButtonSprites.get(i).draw(batch);
    }
}
