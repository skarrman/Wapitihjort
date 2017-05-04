package tank_revolution.terrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.quailshillstudio.polygonClippingUtils.GroundFixture;
import com.quailshillstudio.polygonClippingUtils.UserData;
import com.quailshillstudio.polygonClippingUtils.WorldCollisions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-03.
 */
public class TerrainAdapter implements ITerrain{

    private boolean mustCreate;
    private List<GroundFixture> polyVerts;
    private World world;

    public TerrainAdapter(World world){
        polyVerts = new ArrayList();
        this.world = world;
        create();
    }

    public void create() {
        List<float[]> verts = new ArrayList();
        float[] points = new float[]{-5f, -5f, 55f, -5f, 55f, 3f, -5f, 3f};
        verts.add(points);
        GroundFixture grFix = new GroundFixture(verts);
        this.polyVerts.add(grFix);
        this.mustCreate = true;
        createGround();
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    //TODO change this method name to something better
    public void hasSomeThingBeenHit(){
        for(int i = 0; i < this.world.getBodyCount(); ++i) {
            Array<Body> bodies = new Array();
            this.world.getBodies(bodies);
            UserData data = (UserData)((Body)bodies.get(i)).getUserData();
            if(data != null && data.getType() == 0 && (data.mustDestroy || this.mustCreate) && !data.destroyed) {
                this.world.destroyBody((Body)bodies.get(i));
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
            UserData usrData = new UserData(0);
            nground.setUserData(usrData);
            List<Fixture> fixtures = new ArrayList();

            for(int y = 0; y < ((GroundFixture)this.polyVerts.get(i)).getVerts().size(); ++y) {
                if(((float[])((GroundFixture)this.polyVerts.get(i)).getVerts().get(y)).length >= 6) {
                    ChainShape shape = new ChainShape();
                    shape.createLoop((float[])((GroundFixture)this.polyVerts.get(i)).getVerts().get(y));
                    FixtureDef fixtureDef = new FixtureDef();
                    fixtureDef.shape = shape;
                    fixtureDef.density = 1.0F;
                    fixtureDef.friction = 0.8F;
                    fixtures.add(nground.createFixture(fixtureDef));
                }
            }

            ((GroundFixture)this.polyVerts.get(i)).setFixtures(fixtures);
        }

        this.mustCreate = false;
        this.polyVerts.clear();
    }
}
