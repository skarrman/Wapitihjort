package tank_revolution.framework;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.quailshillstudio.articlepolygonclipping.PolygonClipping;
import com.quailshillstudio.polygonClippingUtils.CollisionGeometry;
import com.quailshillstudio.polygonClippingUtils.PolygonBox2DShape;
import com.quailshillstudio.polygonClippingUtils.UserData;
import com.quailshillstudio.polygonClippingUtils.WorldCollisions;
import tank_revolution.framework.terrain.ITerrainHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonhagermalm on 2017-05-04.
 */
public class EnvironmentCollisions {
    /*public float circRadius = 4.0F;
    public int segments;
    //private PolygonClipping game;
    private boolean clipped;
    private ITerrainHandler terrainHandler;

    public EnvironmentCollisions(ITerrainHandler terrainHandler) {
        this.segments = (int)(this.circRadius * 2.0F + this.circRadius / 2.0F);
        this.terrainHandler = terrainHandler;
    }

    public void beginContact(Contact contact) {
    }

    public void endContact(Contact contact) {
    }

    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    public void postSolve(Contact contact, ContactImpulse impulse) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        UserData dataA = (UserData)a.getUserData();
        UserData dataB = (UserData)b.getUserData();
        if(dataA instanceof UserData && dataA.getType() == 0 && dataB instanceof UserData && dataB.getType() == 1) {
            this.clippingGround(a, b, dataA);
        } else if(dataB instanceof UserData && dataB.getType() == 0 && dataA instanceof UserData && dataA.getType() == 1) {
            this.clippingGround(b, a, dataB);
        }

        this.clipped = false;
    }

    private float[] getVerts(Shape shape) {
        float[] verts = new float[0];
        int i;
        int var5;
        Vector2 vect;
        if(shape instanceof PolygonShape) {
            PolygonShape polyShape = (PolygonShape)shape;
            verts = new float[polyShape.getVertexCount() * 2];
            i = 0;

            for(var5 = 0; i < polyShape.getVertexCount(); ++i) {
                vect = new Vector2();
                polyShape.getVertex(i, vect);
                verts[var5++] = vect.x;
                verts[var5++] = vect.y;
            }
        }

        if(shape instanceof ChainShape) {
            ChainShape cshape = (ChainShape)shape;
            float[] vertices = null;
            if(cshape.isLooped()) {
                vertices = new float[cshape.getVertexCount() * 2 - 2];
                i = 0;

                for(var5 = 0; i < cshape.getVertexCount() - 1; ++i) {
                    vect = new Vector2();
                    cshape.getVertex(i, vect);
                    vertices[var5++] = vect.x;
                    vertices[var5++] = vect.y;
                }
            } else {
                vertices = new float[cshape.getVertexCount() * 2];
                i = 0;

                for(var5 = 0; i < cshape.getVertexCount(); ++i) {
                    vect = new Vector2();
                    cshape.getVertex(i, vect);
                    vertices[var5++] = vect.x;
                    vertices[var5++] = vect.y;
                }
            }
        }

        return verts;
    }

    /**
     * handles the clipping of the terrain
     * @param a the static body (ground) that was hit by the projectile
     * @param b the projectile
     * @param dataA the UserData of a
     */
    /*private void clippingGround(Body a, Body b, UserData dataA) {
        List<PolygonBox2DShape> totalRS = new ArrayList();
        float[] circVerts = CollisionGeometry.approxCircle(b.getPosition().x, b.getPosition().y, this.circRadius, this.segments);
        ChainShape shape = new ChainShape();
        shape.createLoop(circVerts);
        PolygonBox2DShape circlePoly = new PolygonBox2DShape(shape);
        Array<Fixture> fixtureList = a.getFixtureList();
        int fixCount = fixtureList.size;

        for(int i = 0; i < fixCount; ++i) {
            PolygonBox2DShape polyClip = null;
            if(((Fixture)fixtureList.get(i)).getShape() instanceof PolygonShape) {
                polyClip = new PolygonBox2DShape((PolygonShape)((Fixture)fixtureList.get(i)).getShape());
            } else if(((Fixture)fixtureList.get(i)).getShape() instanceof ChainShape) {
                polyClip = new PolygonBox2DShape((ChainShape)((Fixture)fixtureList.get(i)).getShape());
            }

            List<PolygonBox2DShape> rs = polyClip.differenceCS(circlePoly);

            for(int y = 0; y < rs.size(); ++y) {
                ((PolygonBox2DShape)rs.get(y)).circleContact(b.getPosition(), this.circRadius);
                totalRS.add(rs.get(y));
            }
        }

        this.terrainHandler.switchGround(totalRS);
        ((UserData)a.getUserData()).mustDestroy = true;
    }*/
}

