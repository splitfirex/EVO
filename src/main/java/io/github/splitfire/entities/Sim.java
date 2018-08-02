package io.github.splitfire.entities;

import io.github.splitfire.interaction.Keyboard;
import org.dyn4j.dynamics.Force;
import org.dyn4j.geometry.*;
import org.dyn4j.samples.framework.SimulationBody;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Sim extends SimulationBody {

    Keyboard keyboard;

    boolean controlled = false;
    double angle = 0.0;
    double force = 10;

    public Sim(double x, double y) {

        addFixture(Geometry.createCircle(0.5));
        setMass(MassType.NORMAL);
        angle = Math.random()*360;
        translate(x, y);
    }

    public boolean isControlled() {
        return controlled;
    }

    public void setControlled(boolean controlled) {
        if (controlled) setColor(color.RED);
        if (!controlled) setColor(color.GREEN);
        this.controlled = controlled;
    }

    public void update() {
        if (controlled) {
            if (keyboard.isLeft()) angle -= 0.025;
            if (keyboard.isRight()) angle += 0.025;

            if (keyboard.isSpace()) {
                applyForce(new Force(Math.cos(Math.PI * angle) * force, Math.sin(Math.PI * angle) * force));
            }
        }
        applyForce(new Force(getLinearVelocity().x/2 * -1, getLinearVelocity().y/2 * -1));
        getTransform().setRotation(Math.PI*angle);
    }


    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

}
