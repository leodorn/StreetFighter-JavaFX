package streetfighter.other;

import streetfighter.Character;
import streetfighter.FightManager;
import streetfighter.interfaces.Collidable;


/**
    * Zone invisible créée lors d'une attaque d'un joueur
    * Permet la gestion de la prise de dégâts
 */
public class Hurtbox extends GameObject implements Collidable {

    //Zone invisible
    Hitbox hitbox;
    //De quel personnage provient l'attaque
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
