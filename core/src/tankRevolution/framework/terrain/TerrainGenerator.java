package tankRevolution.framework.terrain;

import tankRevolution.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 */
public class TerrainGenerator {


    public static float[] getTerrainVertexArray(){
        List<Float> verticesList = new ArrayList<Float>();
        float period = 10;

        verticesList.add(0f);
        verticesList.add(0f);
        verticesList.add(Constants.getMapWidth());
        verticesList.add(0f);

        for(float x = 150; x > -1; x--){
            if (x < 36 || (x > 72 && x < 108)){
                verticesList.add(x);
                verticesList.add(a2Sin10a0Add10(x));
            } else {
                verticesList.add(x);
                verticesList.add(a8Sin10an5Add10(x));
            }
        }

        return listToArray(verticesList);
    }

    private static float a8Sin10an5Add10(float x){
        //x is in deg
        //this makes the curve go between 10 and 26 with 36 as period
        //the map will start at y = sin

        float amplitude = 8f;
        float period = 10;
        float verticalShift = 10f;
        float parseShift = 0;


        return (float)(amplitude * Math.sin(Math.toRadians((x + parseShift) * period)) + verticalShift);
    }

    private static float a2Sin10a0Add10(float x){
        //x is in deg
        //this makes the curve go between 10 and 26 with 36 as period
        //the map will start at y = sin

        float amplitude = 2f;
        float period = 10f;
        float verticalShift = 10f;
        float parseShift = 0f;


        return (float)(amplitude * Math.sin(Math.toRadians((x + parseShift) * period)) + verticalShift);
    }


    private static float[] listToArray(List<Float> verticesList){
        float[] verticesArray = new float[verticesList.size()];
        for(int i = 0; i < verticesArray.length; i++){
            verticesArray[i] = verticesList.remove(0);
        }
        return verticesArray;
    }

    public enum Maps{
    }
}
