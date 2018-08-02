package io.github.splitfire.entities;

import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.samples.framework.SimulationBody;

import java.awt.*;

public class Tile extends SimulationBody {

    public Tile(double x, double y){
        addFixture(Geometry.createRectangle(1,1));
        //getFixtures().forEach(f->f.setSensor(true));
        setMass(MassType.NORMAL);
        setAutoSleepingEnabled(true);
        setActive(false);
        translate(x, y);
    }

    public void collided(boolean col){
        if(col) setColor(Color.GREEN);
        if(!col) setColor(Color.getHSBColor(100,100,255));

    }

}
