package streetfighter;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetfighter.interfaces.Renderable;
import streetfighter.other.Hurtbox;

public class Hadoken extends Hurtbox implements Renderable {
    int duration;
    private double speed;
    private Image hadoken = new Image("streetfighter/Ryu/Hadoken.gif");
    private ImageView renderer;

    public Hadoken(double x, double y, double width, double height, double damage, Character owner, double speed, int duration) {
        super(x, y, width, height, damage, owner);
        this.speed = speed;
        renderer = new ImageView(hadoken);
        renderer.setX(x);
        renderer.setY(y);
        this.duration=duration;
    }

    public void setScaleXRenderer(int scale){
        renderer.setScaleX(scale);
    }

    @Override
    public void update() {
        super.update();
        this.x += speed;
    }

    @Override
    public Node getRenderer() {
        return renderer;
    }

    @Override
    public void draw() {
        renderer.setX(x);
    }
}
