package tank_revolution_tests.Utils;

import org.junit.Test;
import tank_revolution.Utils.AssetsManager;
import tank_revolution.Utils.Id;

/**
 * Created by antonhagermalm on 2017-04-25.
 */
public class AssetsManagerExceptionsTest {

    @Test public void loadStaringAssetsTest(){
        AssetsManager.getInstance().loadStartingAssets(2);
    }

    @Test public void loadPlayerTexture(){
        AssetsManager.getInstance().getTextureAtlas(Id.PLAYER2);
    }
}
