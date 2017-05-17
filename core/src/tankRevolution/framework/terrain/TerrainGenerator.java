package tankRevolution.framework.terrain;

import tankRevolution.utils.AssetsManager;
import tankRevolution.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 * This class creates a map from a textfile in assets/maps/..
 *
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
    public static float[] getTerrainVertexArray(String mapName) {
        List<Float> vertexList = getInitValues();
        List<List<Integer>> mapMatrix;

        /*vertexList = addAllToList(vertexList, TerrainPartGenerator.generateFlatGround(0, 5, 30));
        vertexList = addAllToList(vertexList, TerrainPartGenerator.generateHill(30, 5, 20, 40));
        vertexList = addAllToList(vertexList, TerrainPartGenerator.generateValley(70, 5, 3, 80));
*/
        mapMatrix = getMatrix(mapName);
        vertexList = createVertexList(mapMatrix, vertexList);

        return listToArray(vertexList);
    }

    private static List<Float> getInitValues() {
        List<Float> verticesList = new ArrayList<Float>();

        verticesList.add(Constants.getMapWidth() + 1);
        verticesList.add(0f);
        verticesList.add(0f);
        verticesList.add(0f);

        return verticesList;
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

        //Debug
/*        for (int i = 0; i < mapStringList.size(); i++) {
            System.out.print(i + " = ");
            System.out.println(mapStringList.get(i));
        }
*/
        return mapStringList;
    }

    private static List<List<Integer>> createMapMatrix(List<String> mapStringList) {
        List<List<Integer>> mapMatrix = new ArrayList<List<Integer>>();
        for (int i = 0; i < mapStringList.size(); i++) {
            mapMatrix.add(new ArrayList<Integer>());
            int firstIndexOfInteger = 0;
            for (int j = 0; j < mapStringList.get(i).length(); j++) {
                if (mapStringList.get(i).charAt(j) == ',') {
                    mapMatrix.get(i).add(Integer.parseInt(mapStringList.get(i).substring(firstIndexOfInteger, j)));

                    firstIndexOfInteger = j + 1;
                }
            }
            mapMatrix.get(i).add(Integer.parseInt(mapStringList.get(i).substring(firstIndexOfInteger)));
        }
        return mapMatrix;
    }

    private static List<List<Integer>> getMatrix(String mapName) {
        List<String> mapStringList;
        List<List<Integer>> mapMatrix;

        mapStringList = createMapStringList(mapName);
        mapMatrix = createMapMatrix(mapStringList);

        //Debug
        /*for(List<Integer> integers : mapMatrix){
            System.out.println("");
            for (Integer integer : integers){
                System.out.print(integer + ", ");
            }
        }*/

        return mapMatrix;
    }

    private static List<Float> createVertexList(List<List<Integer>> matrix, List<Float> vertexList) {

        for (int i = 0; i < matrix.size(); i++) {

            int type = matrix.get(i).get(0);

            switch (type) {
                case hill:
                    addAllToList(vertexList, TerrainPartGenerator.generateHill(
                            matrix.get(i).get(1),
                            matrix.get(i).get(2),
                            matrix.get(i).get(3),
                            matrix.get(i).get(4)));
                    break;
                case valley:
                    addAllToList(vertexList, TerrainPartGenerator.generateValley(
                            matrix.get(i).get(1),
                            matrix.get(i).get(2),
                            matrix.get(i).get(3),
                            matrix.get(i).get(4)));
                    break;
                case flat:
                    addAllToList(vertexList, TerrainPartGenerator.generateFlatGround(
                            matrix.get(i).get(1),
                            matrix.get(i).get(2),
                            matrix.get(i).get(3)));
                    break;
            }
        }
        return vertexList;
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
