package io.github.splitfire.entities;

import io.github.splitfire.interaction.Keyboard;
import org.dyn4j.dynamics.Force;
import org.dyn4j.samples.framework.SimulationBody;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Camera extends SimulationBody {

    Keyboard keyboard;

    boolean lock = false;

    double height = 0.0;
    double width = 0.0;


    double maxspeed = 5.0;
    double defaultSpeed = 0.5;


    double Xaccel = 0.0;
    double Yaccel = 0.0;
    double Zaccel = 0.0;

    double zoom = 1.0;

    public double getZoom() {
        return zoom;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public Camera(double width, double height, double scale) {
        this.height = height;
        this.width = width;
        getTransform().setTranslation(this.width / 2, this.height / 2);
    }

    public void update() {
        if (keyboard.isRight()) Xaccel -= defaultSpeed;
        if (keyboard.isLeft()) Xaccel += defaultSpeed;
        if (keyboard.isUp()) Yaccel += defaultSpeed;
        if (keyboard.isDown()) Yaccel -=defaultSpeed;
        if (keyboard.isZoomIn()) zoom += 0.1;
        if (keyboard.isZoomOut()) zoom -= 0.1;

        if (Math.abs(Yaccel) < 0.4) Yaccel = 0;
        if (Math.abs(Xaccel) < 0.4) Xaccel = 0;
        if (Math.abs(zoom) < 0.5) zoom = 0.5;

        if (!keyboard.isVerticalPress()) Yaccel += -1 * Math.signum(Yaccel) * 0.4;
        if (!keyboard.isHorizontalPress()) Xaccel += -1 * Math.signum(Xaccel) * 0.4;

        getTransform().translate(Xaccel, Yaccel);
    }

    public void render(Graphics2D g, double elapsedTime) {
        AffineTransform tr = new AffineTransform();

        tr.scale(getZoom(), getZoom());
        tr.translate(getTransform().getTranslationX() + (((1 / zoom) - 1) * (width * .5)),
                getTransform().getTranslationY() + (((1 / zoom) - 1) * (height * .5)));

        g.setTransform(tr);
    }

}
