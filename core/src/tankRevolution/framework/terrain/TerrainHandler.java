package tankRevolution.framework.terrain;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.quailshillstudio.polygonClippingUtils.CollisionGeometry;
import com.quailshillstudio.polygonClippingUtils.GroundFixture;
import com.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;
import com.quailshillstudio.polygonClippingUtils.UserData;
import tankRevolution.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-03.
 * The terrain consists of a list of GroundFixtures that consists of a list of arrays of floats.
 */
public class TerrainHandler implements ITerrainHandler {

    private boolean mustCreate;
    private List<GroundFixture> polyVerts;
    private List<float[]> verticesListArray;

    private World world;
    /**
     * The body of the entire terrain
     */
    private Body terrain;

    public TerrainHandler(World world, String mapName) {
        polyVerts = new ArrayList();
        this.world = world;
        verticesListArray = new ArrayList<float[]>();
        create(mapName);
    }


    /**
     * creates the initial body by creating an array of floats. This array of floats is then added to a list given to a
     * GroundFixture that stores this list of arrays. polyVerts is a list of GroundFixtures (only one element if not the ground is split in two)
     */
    private void create(String mapName) {
        List<float[]> verts = new ArrayList();
        float[] points = TerrainGenerator.getTerrainVertexArray(mapName);
        verts.add(points);
        verticesListArray = defenciveCopyVerticesList(verts);
        GroundFixture grFix = new GroundFixture(verts);
        this.polyVerts.add(grFix);
        this.mustCreate = true;
        createGround();
    }

    /**
     * @return a list of arrays with all the vertices to the shapes in the ground
     */
    public List<float[]> getVertices() {
        //DebugMessage
        /*System.out.println(" ");
        System.out.println(" ");
        System.out.println("--------------------------------------------");
        System.out.println(" ");
        System.out.println(" ");

        for (int i = 0; i < verticesListArray.size(); i++) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("- - - - - - - - - - - - - - - -");
            System.out.println(" ");
            System.out.println(" ");

            for (int j = 0; j < verticesListArray.get(i).length; j++) {
                System.out.println(verticesListArray.get(i)[j]);
            }
        }*/

        return verticesListArray;
    }

    /**
     * @param rs is the entire ground in Polygons (only one if not the ground is split in two)
     */
    private void switchGround(List<PolygonBox2DShape> rs) {
        mustCreate = true;
        List<float[]> verts = new ArrayList();
        //gets all vertices from the polygons
        for (PolygonBox2DShape r : rs) {
            verts.add(r.verticesToLoop());
        }

        verticesListArray = defenciveCopyVerticesList(verts);

        //makes a big GroundFixture from the vertices of the big terrainPolygon
        GroundFixture grFix = new GroundFixture(verts);
        //adds the nee graoundFixture to the cleared list
        this.polyVerts.add(grFix);
    }

    @Override
    public void update() {
        for (int i = 0; i < this.world.getBodyCount(); ++i) {
            Array<Body> bodies = new Array();
            this.world.getBodies(bodies);
            UserData data = (UserData) (bodies.get(i)).getUserData();
            if (data != null && data.getType() == 0 && (data.mustDestroy || this.mustCreate) && !data.destroyed) {
                this.world.destroyBody(bodies.get(i));
                bodies.removeIndex(i);
            }
        }
        if (this.mustCreate) {
            this.createGround();
        }
    }

    /**
     * creates the ground by adding several fixtures to the body
     */
    protected void createGround() {
        //verticesListArray = defenciveCopyVerticesList(polyVerts);
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0.0F, 0.0F);

        for (GroundFixture polyVert : this.polyVerts) {

            Body nground = world.createBody(groundDef);
            this.terrain = nground;
            UserData usrData = new UserData(0);
            nground.setUserData(usrData);
            List<Fixture> fixtures = new ArrayList();

            for (int y = 0; y < polyVert.getVerts().size(); ++y) {
                if (polyVert.getVerts().get(y).length >= 6) {
                    ChainShape shape = new ChainShape();
                    shape.createLoop(polyVert.getVerts().get(y));
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.density = 1.0F;
                    fixtureDef.friction = 0.8F;
                    fixtures.add(nground.createFixture(fixtureDef));
                }
            }

            polyVert.setFixtures(fixtures);
        }

        /*List<float[]> verticesListArray = new ArrayList<float[]>();
        for (GroundFixture groundFixture : polyVerts) {
            //List<Float> verticesList = new ArrayList<Float>();
            verticesListArray.addAll(groundFixture.getVerts());
        }*/
        //verticesListArray = defenciveCopyVerticesList(polyVerts);
        /*for(int i = 0; i < verticesListArray.size(); i++){
            for(int j = 0; j < verticesListArray.get(i).length; j++){
                System.out.println(verticesListArray.get(i)[j]);
            }
        }*/

        //oldPolyVerts = polyVerts;
        polyVerts.clear();
        this.mustCreate = false;

    }

    /**
     * @param inVerts The list of float-arrays to be copied.
     * @return a defensive copy of the list of points that makes up the ground.
     */
    public List<float[]> defenciveCopyVerticesList(List<float[]> inVerts) {
        List<float[]> outVerts = new ArrayList<float[]>();

        for (int i = 0; i < inVerts.size(); i++) {
            float[] arrVerts = new float[inVerts.get(i).length];

            for (int j = 0; j < inVerts.get(i).length; j++) {
                arrVerts[j] = inVerts.get(i)[j];
            }
            outVerts.add(arrVerts);
        }
        return outVerts;

        /*for(int i = 0; i < verticesListArray.size(); i++){
            for(int j = 0; j < verticesListArray.get(i).length; j++){
                System.out.println(verticesListArray.get(i)[j]);
            }
        }*/
    }

    @Override
    public void explode(Body projectileBody, int blastRadius) {
        List<PolygonBox2DShape> totalRS = new ArrayList();
        //Approximates the vertices of a circle
        float[] circVerts = CollisionGeometry.approxCircle(projectileBody.getPosition().x, projectileBody.getPosition().y, blastRadius, Constants.getExplosionSegments());
        //Creates a shape from the vertices
        ChainShape shape = new ChainShape();
        shape.createLoop(circVerts);
        //Wraps the shape into a PolygonBox2DShape
        PolygonBox2DShape circlePoly = new PolygonBox2DShape(shape);
        Array<Fixture> fixtureList = terrain.getFixtureList();
        int fixCount = fixtureList.size;

        //Starts iterating through all fixtures in the terrain
        for (int i = 0; i < fixCount; ++i) {
            PolygonBox2DShape polyClip = null;
            if (fixtureList.get(i).getShape() instanceof PolygonShape || fixtureList.get(i).getShape() instanceof ChainShape)
                polyClip = new PolygonBox2DShape(fixtureList.get(i).getShape());


            //Returns the list of clipped polygons i.e. the ground (if the ground is split in two, this list will be the size two),
            // the javaDoc for the algorithm can be read here
            //https://github.com/helderco/univ-polyclip/blob/java/src/compgraphics/Polygon.java

            List<PolygonBox2DShape> rs = polyClip.differenceCS(circlePoly);

            //Adding all the polygons in the list to the new total, totalRS is init here above

            for (PolygonBox2DShape r : rs) {
                r.circleContact(projectileBody.getPosition(), blastRadius);
                totalRS.add(r);
            }
        }
        //Switching to this new clipped ground consisting of the polygon, several if the ground is split
        switchGround(totalRS);
        ((UserData) terrain.getUserData()).mustDestroy = true;
        this.terrain = null;
    }
}
