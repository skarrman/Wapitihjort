package tankRevolution.framework.terrain;

import tankRevolution.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 */
public class TerrainGenerator {


    public float[] getSinusArray(){
        List<Float> verticesList = new ArrayList<Float>();

        float amplitude = 30f;
        float period = 0.2f;

        for(float x = 0; x < Constants.getMapWidth(); x++){
            verticesList.add(x);
            verticesList.add((float)(amplitude * Math.sin(period * x)));
        }

        float[] verticesArray = new float[verticesList.size()];
        for(int i = 0; i < verticesArray.length; i++){
            verticesArray[i] = verticesList.remove(0);
        }
        return verticesArray;
    }

}
