package streetfighter.other;

import streetfighter.Character;
import streetfighter.FightManager;
import streetfighter.interfaces.Collidable;

public class Hurtbox extends GameObject implements Collidable {
    Hitbox hitbox;
    Character owner;
    double damage;

    public Hurtbox(double x, double y, double width, double height, double damage, Character owner) {
        super(x, y, width, height);
        this.hitbox = new Hitbox(x, y, width, height);
        this.damage = damage;
        this.owner = owner;
    }

    @Override
    public void update() {
        this.hitbox.getRectangle().setX(x);
    }

    @Override
    public void onCollision(GameObject go) {
        if (go instanceof Character) {
            if(!owner.equals(go)) {
                FightManager.getGoGarbage().add(this);
            }
        }
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    public Character getOwner() {
        return owner;
    }

    public double getDamage() {
        return damage;
    }
}
