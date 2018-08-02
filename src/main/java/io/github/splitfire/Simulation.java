package io.github.splitfire;

import io.github.splitfire.simulation.SimWorld;
import org.dyn4j.dynamics.DetectResult;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.samples.framework.SimulationBody;
import org.dyn4j.samples.framework.SimulationFrame;

import java.awt.*;

public class Simulation extends SimulationFrame {

    SimWorld mundo;

    public Simulation() {
        super("EVO", 24);
    }

    protected void initializeWorld() {
        mundo = new SimWorld(this, this.scale);
    }


    @Override
    protected void update(Graphics2D g, double elapsedTime) {
        super.update(g, elapsedTime);
        mundo.update(g,elapsedTime);
    }

    @Override
    protected void render(Graphics2D g, double elapsedTime) {
        mundo.cameraRender(g, elapsedTime);
        super.render(g, elapsedTime);
    }


    public static void main(String[] args) {
        Simulation sim = new Simulation();
        sim.run();
    }

}
