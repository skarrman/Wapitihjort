package tankRevolution.framework.terrain;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.quailshillstudio.polygonClippingUtils.CollisionGeometry;
import com.quailshillstudio.polygonClippingUtils.GroundFixture;
import com.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;
import com.quailshillstudio.polygonClippingUtils.UserData;
import tankRevolution.utils.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-03.
 * The terrain consists of a list of GroundFixtures that consists of a list of arrays of floats.
 */
public class TerrainHandler implements ITerrainHandler {

    private boolean mustCreate;
    private List<GroundFixture> polyVerts;

    private World world;
    /**
     * The body of the entire terrain
     */
    private Body terrain;

    public TerrainHandler(World world) {
        polyVerts = new ArrayList();
        this.world = world;
        create();
    }


    /**
     * creates the initial body by creating an array of floats. This array of floats is then added to a list given to a
     * GroundFixture that stores this list of arrays. polyVerts is a list of GroundFixtures (only one element if not the ground is split in two)
     */
    private void create() {
        List<float[]> verts = new ArrayList();
        float[] points = generateTerrain();
        verts.add(points);
        GroundFixture grFix = new GroundFixture(verts);
        this.polyVerts.add(grFix);
        this.mustCreate = true;
        createGround();
    }

    /**
     * may fucked this up, if there is any problems, it may be here
     * @return a list of lists with all the vertices to the shapes in the ground
     */
    public List<List<Float>> getVertices() {
        List<List<Float>> verticesListList = new ArrayList<List<Float>>();
        for (GroundFixture groundFixture : polyVerts) {
            List<Float> verticesList = new ArrayList<Float>();
            for (float[] verticesArray : groundFixture.getVerts()) {
                for (Float vertex : verticesArray){
                    verticesList.add(vertex);
                }
            }
            verticesListList.add(verticesList);
        }
        return verticesListList;
    }

    private float[] generateTerrain() {
        //return new float[]{0, 0, Constants.getMapWidth(), 0, Constants.getMapWidth(), 3f, 0, 3f};
        return new float[]{0, 0, Constants.getMapWidth(), 0, Constants.getMapWidth(), 3, 20, 8, 0, 3};
    }

    /**
     * @param rs is the entire ground in Polygons (only one if not the ground is split in two)
     */
    private void switchGround(List<PolygonBox2DShape> rs) {
        this.polyVerts.clear();
        mustCreate = true;
        List<float[]> verts = new ArrayList();
        //gets all vertices from the polygons
        for (PolygonBox2DShape r : rs) {
            verts.add(r.verticesToLoop());
        }


        //makes a big GroundFixture from the vertices of the big terrainPolygon
        GroundFixture grFix = new GroundFixture(verts);
        //adds the nee graoundFixture to the cleared list
        this.polyVerts.add(grFix);
    }

    /**
     * needs to be called in each frame update, checks if any bodies needs to be removed or the terrain needs to be rebuilt
     */
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

        this.mustCreate = false;
    }

    /**
     * This method basicly does the clipping of polygons. After the algorithm is done, the switchGround is called.
     * circVerts is a vertices of a circle polygon, segments is the number of edges in the circle
     *
     * @param projectileBody The polygon that will clip the ground
     */
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
