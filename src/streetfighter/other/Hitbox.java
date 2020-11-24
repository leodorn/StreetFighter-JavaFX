package streetfighter.other;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public class Hitbox {
    private Rectangle rectangle;

    public Hitbox(double x, double y, double width, double height) {
        rectangle = new Rectangle(x, y, width, height);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Bounds getBounds() {
        return rectangle.getLayoutBounds();
    }
}
