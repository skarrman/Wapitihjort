package tankRevolution.framework.terrain;

import tankRevolution.utils.Constants;

/**
 * Created by antonhagermalm on 2017-05-11.
 * This class generates vertices for maps by creating hills and valleys from sin curves.
 */
public class TerrainPartGenerator {

    //Instead of deleting the first vertex, move it one forward to get the last one

    /**
     * generates a hill
     * @param startingX starting x for the hill, preferably the x-value where the last "shape" ended
     * @param startingY -||-
     * @param height the height of the hill
     * @param width the width of the hill
     * @return the float array for the vertices, x = %2
     */
    public static float[] generateHill(float startingX, float startingY, float height, float width) {
        float[] vertArr = new float[((int)width * 2)];

        for (int i = 0; i < vertArr.length; i = i + 2) {
            vertArr[i] = ((i / 2) + startingX + 1) * (Constants.getMapWidth() / 150);
            vertArr[i + 1] = (float) (height / 2 * Math.sin(((i / 2 + 1) - (width / 4)) * (Math.PI / (width / 2))) + height/2 + startingY);
        }

        return vertArr;
    }

    /**
     * same as above but for flat ground
     * @param startingX starting x for the ground, preferably the x-value where the last "shape" ended
     * @param startingY -||-
     * @param width the width of the hill
     * @return the float array for the vertices, x = %2
     */
    public static  float[] generateFlatGround(float startingX, float startingY, int width){
        float[] vertArr = new float[(width * 2)];

        for (int i = 0; i < vertArr.length; i = i + 2) {
            vertArr[i] = ((i / 2) + startingX + 1) * (Constants.getMapWidth() / 150);
            vertArr[i + 1] = startingY;
        }
        return vertArr;
    }

    /**
     * same as above but for a valley
     * @param startingX starting x for the ground, preferably the x-value where the last "shape" ended
     * @param startingY -||-
     * @param depth the depth of the valley
     * @param width the width of the wall
     * @return the float array for the vertices, x = %2
     */
    public static float[] generateValley(float startingX, float startingY, float depth, float width) {
        float[] vertArr = new float[(int)(width * 2)];

        depth = - depth;

        for (int i = 0; i < vertArr.length; i = i + 2) {
            vertArr[i] = ((i / 2) + startingX + 1) * (Constants.getMapWidth() / 150);
            vertArr[i + 1] = (float) (depth / 2 * Math.sin(((i / 2 + 1) - (width / 4)) * (Math.PI / (width / 2))) + depth/2 + startingY);
        }

        return vertArr;
    }
}
