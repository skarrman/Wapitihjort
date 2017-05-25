package tankRevolution.model.shootablePackage;


import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created by antonhagermalm on 2017-03-30.
 * The interface responsible for all shootable objects
 */
public interface Shootable {

    int getBlastRadius();

    int getDamage();

    float getMissileRadius();

    float getMissileDensity();

}
