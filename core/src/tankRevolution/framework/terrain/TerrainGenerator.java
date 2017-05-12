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

        verticesList.add(Constants.getMapWidth() + 1);
        verticesList.add(0f);
        verticesList.add(0f);
        verticesList.add(0f);

        verticesList = addAllToList(verticesList, TerrainPartGenerator.generateFlatGround(0, 5, 30));
        verticesList = addAllToList(verticesList, TerrainPartGenerator.generateHill(30, 5, 20, 40));
        verticesList = addAllToList(verticesList, TerrainPartGenerator.generateValley(70, 5, 3, 80));

        return listToArray(verticesList);
    }

    public static List<Float> addAllToList(List<Float> list, float[] arr){
        for (float f : arr){
            list.add(f);
        }
        return list;
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
