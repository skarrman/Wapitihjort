package tank_revolution_tests.model.environment;


import com.quailshillstudio.polygonClippingUtils.GroundFixture;
import org.junit.Test;
import tankRevolution.framework.terrain.Bezier;
import tankRevolution.utils.Constants;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-10.
 * This is a class where i basically dumped some code i no longer need but can be usfull in the future
 */
public class CodeDump {
    Point2D[] points = new Point2D[]{new Point2D.Float(0, 0) ,new Point2D.Float(5, 10), new Point2D.Float(10, 0)};
    //Point2D[] points = new Point2D[]{new Point2D.Float(0, 0), new Point2D.Float(-2, 7), new Point2D.Float(2, 12), new Point2D.Float(10, 10)};

    @Test public void getPoints(){
        Bezier bezier = new Bezier(points);
        points = bezier.getPoints();
        for(Point2D point2D : points){
            System.out.println(point2D.getX() + " " + point2D.getY());
        }

        /*Point2D[] points = new Point2D[]{new Point2D.Float(Constants.getMapWidth(), 3), new Point2D.Float(20, 8), new Point2D.Float(0, 3)};
        points = getTotalArr(points);

        List<Float> floats = new ArrayList<Float>();
        floats.add(0f);
        floats.add(0f);
        floats.add(Constants.getMapWidth());
        floats.add(0f);
        floats.add(Constants.getMapWidth());
        floats.add(3f);

        for(Point2D point2D : points){
            floats.add((float) point2D.getX());
            floats.add((float) point2D.getY());
        }

        float[] fl = new float[floats.size()];
        for(int i = 0; i < fl.length; i++){
            fl[i] = floats.remove(0);
        }

        for(int i = 0; i < fl.length; i++) {
            System.out.println(fl[i]);
        }*/
    }

    private Point2D[] getTotalArr(Point2D[] inPoints){
        Point2D startPoint = inPoints[0];
        Point2D endPoint = inPoints[inPoints.length-1];

        Bezier bezier = new Bezier(inPoints);
        Point2D[] bezierPoints = bezier.getPoints();
        Point2D[] newPoints = new Point2D[bezier.getPointCount() + 2];

        newPoints[0]= startPoint;
        newPoints[newPoints.length-1] = endPoint;

        for(int i = 0; i < bezierPoints.length; i++){
            newPoints[i+1] = bezierPoints[i];
        }

        return newPoints;
    }
/** some generation with quadcurve2d */
    private float[] generateTerrain() {
        //return new float[]{0, 0, Constants.getMapWidth(), 0, Constants.getMapWidth(), 3f, 0, 3f};
/*
        QuadCurve2D.Float roundedCurve = new QuadCurve2D.Float(10, 0, 20, 5, 30, 0);
        float[] floats = new float[]{10, 0, 20, 5, 30, 0, 40, 10};
        //PathIterator pathIterator = roundedCurve.getPathIterator(new AffineTransform());
        double[] doubles = new double[]{10, 1, 20, 5, 30, 1};
        System.out.println("pathIterator.currentSegment(floats) = "+ QuadCurve2D.solveQuadratic(doubles));
        for (double d : doubles){
            System.out.print(" " + d);
        }
*/
        /*Point2D[] points = new Point2D[]{new Point2D.Float(Constants.getMapWidth(), 3), new Point2D.Float(20, 8), new Point2D.Float(0, 3)};
        points = getTotalArr(points);

        List<Float> floats = new ArrayList<Float>();
        floats.add(0f);
        floats.add(0f);
        floats.add(Constants.getMapWidth());
        floats.add(0f);
        floats.add(Constants.getMapWidth());
        floats.add(3f);

        for(Point2D point2D : points){
            floats.add((float) point2D.getX());
            floats.add((float) point2D.getY());
        }

        float[] fl = new float[floats.size()];
        for(int i = 0; i < fl.length; i++){
            fl[i] = floats.remove(0);
        }


        return fl;*/
        //return new float[]{0, 0, Constants.getMapWidth(), 0, Constants.getMapWidth(), 3, 20, 8, 0, 3};
        return null;
    }

    /**
     * defensive copying of a list of groundfixtures
     * TESTED
     */
    /*public List<float[]> defenciveCopyVerticesList(List<GroundFixture> polyVerts){
        List<float[]> verticesListArray = new ArrayList<float[]>();

        for (GroundFixture polyVert : polyVerts) {

            for(int i = 0; i < polyVert.getVerts().size(); i++){
                float[] verticesArray = new float[polyVert.getVerts().get(i).length];

                for(int j = 0; j < polyVert.getVerts().get(i).length; j++){
                    verticesArray[j] = polyVert.getVerts().get(i)[j];
                }
                verticesListArray.add(verticesArray);
            }
        }
        return verticesListArray;

        /*for(int i = 0; i < verticesListArray.size(); i++){
            for(int j = 0; j < verticesListArray.get(i).length; j++){
                System.out.println(verticesListArray.get(i)[j]);
            }
        }
    }*/
}
