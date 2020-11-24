package streetfighter;

import javafx.scene.image.Image;

public class Attack {
    private int duration;
    private double damage;
    private double xOff, yOff, width, height;
    private String name;

    public Attack(int duration, double damage, String name, double xOff, double yOff, double width, double height) {
        this.duration = duration;
        this.damage = damage;
        this.name = name;
        this.xOff = xOff;
        this.yOff = yOff;
        this.width = width;
        this.height = height;
    }

    public int getDuration() {
        return duration;
    }

    public double getDamage() {
        return damage;
    }

    public Image getSprite(String characterName) {
        Image spr = new Image(characterName + "/" + name + ".gif");
        return new Image(characterName + "/" + name + ".gif", spr.getWidth()*2, spr.getHeight()*2, true, false);
    }

    public double getXOff() {
        return xOff;
    }

    public double getYOff() {
        return yOff;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
