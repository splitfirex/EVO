package io.github.splitfire.simulation;

import io.github.splitfire.entities.Camera;
import io.github.splitfire.entities.MousePointer;
import io.github.splitfire.entities.Sim;
import io.github.splitfire.entities.Tile;
import io.github.splitfire.interaction.Keyboard;
import io.github.splitfire.interaction.MouseSelection;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.*;
import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.geometry.*;
import org.dyn4j.samples.framework.Graphics2DRenderer;
import org.dyn4j.samples.framework.SimulationBody;
import org.dyn4j.samples.framework.SimulationFrame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class SimWorld {

    SimulationFrame frame;
    double scale = 1;
    Keyboard keyboard = new Keyboard();
    MouseSelection mouseSelection;

    Camera cam;
    MousePointer mousePointer;
    Sim selectedSim;

    List<Tile> tiles = new ArrayList();
    List<Sim> bodies = new ArrayList();

    private List<DetectResult> clickColission = new ArrayList<DetectResult>();

    public SimWorld(SimulationFrame frame, double scale) {

        this.scale = scale;
        this.frame = frame;
        this.frame.getWorld().setGravity(World.ZERO_GRAVITY);

        cam = new Camera(this.frame.getCanvas().getWidth(), this.frame.getCanvas().getHeight(), this.scale);
        mouseSelection = new MouseSelection();
        mousePointer = new MousePointer(this.frame.getCanvas().getWidth(), this.frame.getCanvas().getHeight(), this.scale);
        mousePointer.setCamera(cam);

        for (int i = -100; i < 100; i++) {
            for (int j = -10; j < 10; j++) {
                Tile t = new Tile(i, j);
                tiles.add(t);
                this.frame.getWorld().addBody(t);
            }
        }
        for (int i = 0; i < 1; i++) {
            Sim sim = new Sim((Math.random() * 100) - 50, (Math.random() * 100) - 50);
            bodies.add(sim);
        }

        for (Sim s : bodies) {
            s.setKeyboard(keyboard);
            this.frame.getWorld().addBody(s);
        }

        cam.setKeyboard(keyboard);
        this.frame.getCanvas().addKeyListener(keyboard);
        this.frame.getCanvas().addMouseListener(mouseSelection);
        this.frame.getCanvas().addMouseMotionListener(mouseSelection);

    }

    public void update(Graphics2D g, double elapsedTime) {
        clickColission.clear();
        if (selectedSim == null) cam.update();
        mousePointer.update(mouseSelection.getPoint());

        for (Sim s : bodies) {
            s.update();
        }

        if (mousePointer.getWorldPoint() != null) {
            if (selectedSim != null) selectedSim.setControlled(false);
            selectedSim = null;
            Convex convex = Geometry.createCircle(.2);
            Transform transform = new Transform();
            transform.translate(mousePointer.getWorldPoint().x, -mousePointer.getWorldPoint().y);

            this.frame.getWorld().detect(
                    convex,
                    transform,
                    null,            // no, don't filter anything using the Filters
                    false,            // include sensor fixtures
                    false,            // include inactive bodies
                    false,            // we don't need collision info
                    this.clickColission);
            if (this.clickColission.size() > 0) System.out.println("CCD");
            this.clickColission.forEach(detectResult -> {
                if (detectResult.getBody() instanceof Sim) {
                    selectedSim = (Sim) detectResult.getBody();
                }
            });
            if (selectedSim != null) selectedSim.setControlled(true);
        }

    }

    public void cameraRender(Graphics2D g, double elapsedTime) {
        cam.render(g, elapsedTime);
        if (mousePointer.getWorldPoint() != null) {
            AffineTransform tx = g.getTransform();
            g.translate((mousePointer.getWorldPoint().x) * this.scale, (-mousePointer.getWorldPoint().y) * this.scale);
            mousePointer.render(g, this.scale);
            g.setTransform(tx);
        }
    }
}
