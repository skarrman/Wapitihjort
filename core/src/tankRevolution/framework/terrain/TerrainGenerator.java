package tankRevolution.framework.terrain;

import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 * This class creates a map from a source, it's not decided yet if it's from a file or classes
 */
public class TerrainGenerator {

    private static final int hill = 0;
    private static final int valley = 1;
    private static final int flat = 2;

    /**
     * This method will be called to get the map in the floatArray
     *
     * @return a float array containing all vertices to the map. %2==0 = x %2==1 = y
     */
    public static float[] getTerrainVertexArray() {
        List<Float> verticesList = new ArrayList<Float>();
        float period = 10;

        verticesList.add(Constants.getMapWidth() + 1);
        verticesList.add(0f);
        verticesList.add(0f);
        verticesList.add(0f);

        verticesList = addAllToList(verticesList, TerrainPartGenerator.generateFlatGround(0, 5, 30));
        verticesList = addAllToList(verticesList, TerrainPartGenerator.generateHill(30, 5, 20, 40));
        verticesList = addAllToList(verticesList, TerrainPartGenerator.generateValley(70, 5, 3, 80));

        AssetsManager.getInstance().loadMap("testMap");
        String mapString = AssetsManager.getInstance().getMapString();

        getMatrix("testMap");
        return listToArray(verticesList);
    }

    private static List<String> createMapStringList(String mapName) {
        AssetsManager.getInstance().loadMap(mapName);
        String mapString = AssetsManager.getInstance().getMapString();
        List<String> mapStringList = new ArrayList<String>();
        int firstIndexOfRow = 0;

        for (int i = 0; i < mapString.length(); i++) {
            if (mapString.charAt(i) == '\n') {
                //System.out.println(i + " = i");
                mapStringList.add(mapString.substring(firstIndexOfRow, i));
                firstIndexOfRow = i + 1;
            }
        }
        mapStringList.add(mapString.substring(firstIndexOfRow));

/*        for (int i = 0; i < mapStringList.size(); i++) {
            System.out.print(i + " = ");
            System.out.println(mapStringList.get(i));
        }
*/
        return mapStringList;
    }

    private static List<List<Integer>> createMapMatrix(List<String> mapStringList) {
        List<List<Integer>> mapMatrix = new ArrayList<List<Integer>>(2);
        for (int i = 0; i < mapStringList.size(); i++) {
            mapMatrix.add(i, new ArrayList<Integer>());
            int firstIndexOfInteger = 0;
            for (int j = 0; j < mapStringList.get(i).length(); j++) {
                if (mapStringList.get(i).charAt(j) == ',') {
                    mapMatrix.get(i).add(Integer.getInteger(mapStringList.get(i).substring(firstIndexOfInteger, j)));
                    firstIndexOfInteger = j + 1;
                }
            }
        }
        return mapMatrix;
    }

    private static List<List<Integer>> getMatrix(String mapName) {
        List<String> mapStringList;
        List<List<Integer>> mapMatrix;

        mapStringList = createMapStringList(mapName);
        mapMatrix = createMapMatrix(mapStringList);

        for(List<Integer> integers : mapMatrix){
            System.out.println("");
            for (Integer integer : integers){
                System.out.print(integer + ", ");
            }
        }

        return mapMatrix;
    }

    /**
     * Adds the array to the list
     *
     * @param list
     * @param arr
     * @return
     */
    public static List<Float> addAllToList(List<Float> list, float[] arr) {
        for (float f : arr) {
            list.add(f);
        }
        return list;
    }


    private static float[] listToArray(List<Float> verticesList) {
        float[] verticesArray = new float[verticesList.size()];
        for (int i = 0; i < verticesArray.length; i++) {
            verticesArray[i] = verticesList.remove(0);
        }
        return verticesArray;
    }

    public enum Maps {
    }
}
