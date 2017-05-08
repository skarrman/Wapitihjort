package tankRevolution.framework.terrain;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.quailshillstudio.polygonClippingUtils.CollisionGeometry;
import com.quailshillstudio.polygonClippingUtils.GroundFixture;
import com.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;
import com.quailshillstudio.polygonClippingUtils.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-03.
 */
public class TerrainHandler implements ITerrainHandler {

    private boolean mustCreate;
    private List<GroundFixture> polyVerts;
    private World world;
    private float circRadius = 4.0F;
    //This is sets the roundness of the explosion radius
    private int segments = 32;
    private Body terrain;

    public TerrainHandler(World world){
        polyVerts = new ArrayList();
        this.world = world;
        create();
    }

    public void create() {
        List<float[]> verts = new ArrayList();
        float[] points = new float[]{0, 0, 50f, 0, 50f, 3f, 0, 3f};
        verts.add(points);
        GroundFixture grFix = new GroundFixture(verts);
        this.polyVerts.add(grFix);
        this.mustCreate = true;
        createGround();
    }

    /**
     *
     * @param rs dont really know what this stands for but it's the complete ground (i think)
     */
    @Override
    public void switchGround(List<PolygonBox2DShape> rs) {
        mustCreate = true;
        List<float[]> verts = new ArrayList();

        for (PolygonBox2DShape r : rs) {
            verts.add(((PolygonBox2DShape) r).verticesToLoop());
        }

        GroundFixture grFix = new GroundFixture(verts);
        this.polyVerts.add(grFix);
    }

    //TODO change this method name to something better
    public void update(){
        for(int i = 0; i < this.world.getBodyCount(); ++i) {
            Array<Body> bodies = new Array();
            this.world.getBodies(bodies);
            UserData data = (UserData)(bodies.get(i)).getUserData();
            if(data != null && data.getType() == 0 && (data.mustDestroy || this.mustCreate) && !data.destroyed) {
                this.world.destroyBody(bodies.get(i));
                bodies.removeIndex(i);
            }
        }
        if(this.mustCreate) {
            this.createGround();
        }
    }

    protected void createGround() {
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0.0F, 0.0F);

        for(int i = 0; i < this.polyVerts.size(); ++i) {
            Body nground = world.createBody(groundDef);
            this.terrain = nground;
            UserData usrData = new UserData(0);
            nground.setUserData(usrData);
            List<Fixture> fixtures = new ArrayList();

            for(int y = 0; y < this.polyVerts.get(i).getVerts().size(); ++y) {
                if(((this.polyVerts.get(i)).getVerts().get(y)).length >= 6) {
                    ChainShape shape = new ChainShape();
                    shape.createLoop(this.polyVerts.get(i).getVerts().get(y));
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.density = 1.0F;
                    fixtureDef.friction = 0.8F;
                    fixtures.add(nground.createFixture(fixtureDef));
                }
            }

            this.polyVerts.get(i).setFixtures(fixtures);
        }

        this.mustCreate = false;
        this.polyVerts.clear();
    }

    /**
     * This method basicly does the clipping of polygons. After the algorithm is done, the switchGround is called.
     * circVerts is a vertices of a circle polygon, segments is the number of edges in the circle
     * @param clipper The polygon that will clip the ground
     */
    public void clippingGround(Body clipper, int blastRadius) {
        List<PolygonBox2DShape> totalRS = new ArrayList();
        float[] circVerts = CollisionGeometry.approxCircle(clipper.getPosition().x, clipper.getPosition().y, blastRadius, this.segments);
        ChainShape shape = new ChainShape();
        shape.createLoop(circVerts);
        PolygonBox2DShape circlePoly = new PolygonBox2DShape(shape);
        Array<Fixture> fixtureList = terrain.getFixtureList();
        int fixCount = fixtureList.size;

        for(int i = 0; i < fixCount; ++i) {
            PolygonBox2DShape polyClip = null;
            if(fixtureList.get(i).getShape() instanceof PolygonShape) {
                polyClip = new PolygonBox2DShape(fixtureList.get(i).getShape());
            } else if(fixtureList.get(i).getShape() instanceof ChainShape) {
                polyClip = new PolygonBox2DShape(fixtureList.get(i).getShape());
            }

            List<PolygonBox2DShape> rs = polyClip.differenceCS(circlePoly);

            for(int y = 0; y < rs.size(); ++y) {
                rs.get(y).circleContact(clipper.getPosition(), this.circRadius);
                totalRS.add(rs.get(y));
            }
        }

        switchGround(totalRS);
        ((UserData)terrain.getUserData()).mustDestroy = true;
        this.terrain = null;
    }
}
