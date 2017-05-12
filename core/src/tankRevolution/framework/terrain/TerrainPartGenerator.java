package tankRevolution.framework.terrain;

/**
 * Created by antonhagermalm on 2017-05-11.
 */
public class TerrainPartGenerator {

    //Instead of deleting the first vertex, move it one forward to get the last one

    public static float[] generateHill(float startingX, float startingY, float height, float width) {
        float[] vertArr = new float[((int)width * 2)];

        for (int i = 0; i < vertArr.length; i = i + 2) {
            //vertArr[i] = (i / 2) + startingX + 1;
            vertArr[i] = (i / 2) + startingX + 1;
            vertArr[i + 1] = (float) (height / 2 * Math.sin(((i / 2 + 1) - (width / 4)) * (Math.PI / (width / 2))) + height/2 + startingY);
            //vertArr[i + 1] = (float) (height / 2 * Math.sin(((i / 2) - (width / 4)) * (Math.PI / (width/ 2))) + height/2);
        }

        return vertArr;
    }

    public static  float[] generateFlatGround(float startingX, float startingY, int width){
        float[] vertArr = new float[(width * 2) + 2];

        for (int i = 0; i < vertArr.length; i = i + 2) {
            vertArr[i] = (i / 2) + startingX;
            vertArr[i + 1] = startingY;
        }
        return vertArr;
    }

    public static float[] generateValley(float startingX, float startingY, float depth, float width) {
        float[] vertArr = new float[(int)(width * 2)];

        depth = - depth;

        for (int i = 0; i < vertArr.length; i = i + 2) {
            vertArr[i] = (i / 2) + startingX + 1;
            vertArr[i + 1] = (float) (depth / 2 * Math.sin(((i / 2 + 1) - (width / 4)) * (Math.PI / (width / 2))) + depth/2 + startingY);
        }

        return vertArr;
    }
}
