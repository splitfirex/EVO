package io.github.splitfire.entities;

import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.samples.framework.SimulationBody;

import java.awt.*;

public class MousePointer extends SimulationBody {

    double width;
    double height;
    double scale;
    Camera camera;

    Vector2 worldPoint;

    public Vector2 getWorldPoint() {
        return worldPoint;
    }

    public void setWorldPoint(Vector2 worldPoint) {
        this.worldPoint = worldPoint;
    }

    public MousePointer(double width, double height, double scale) {
        addFixture(Geometry.createCircle(0.2));
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public void update(Point point) {
        if (point != null) {
            double x = (point.getX() - ((camera.getTransform().getTranslationX() - width * 0.5) * camera.getZoom()) - width * 0.5) / camera.getZoom() / scale;
            double y = -(point.getY() - ((camera.getTransform().getTranslationY() - height * 0.5) * camera.getZoom()) - height * 0.5) / camera.getZoom() / scale;
            setWorldPoint(new Vector2(x, y));
        } else {
            setWorldPoint(null);
        }
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
