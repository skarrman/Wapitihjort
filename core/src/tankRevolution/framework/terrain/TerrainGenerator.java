package tankRevolution.framework.terrain;

import tankRevolution.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 */
public class TerrainGenerator {


    public static float[] getSinusArray(){
        List<Float> verticesList = new ArrayList<Float>();

        float amplitude = 10f;
        float period = 1/4f;

        verticesList.add(0f);
        verticesList.add(0f);
        verticesList.add(Constants.getMapWidth());
        verticesList.add(0f);

        for(float x = 150; x > -1; x--){
            verticesList.add(x);
            verticesList.add((float)(amplitude * Math.sin(period * x) + 10f));
        }

        float[] verticesArray = new float[verticesList.size()];
        for(int i = 0; i < verticesArray.length; i++){
            verticesArray[i] = verticesList.remove(0);
        }
        return verticesArray;
    }

}
