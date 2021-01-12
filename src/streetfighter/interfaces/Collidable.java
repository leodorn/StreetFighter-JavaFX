package streetfighter.interfaces;

import streetfighter.other.GameObject;
import streetfighter.other.Hitbox;

/**
    * Interface pour définir un objet possédant une physique
    * @author Léo D
 */
public interface Collidable {
    void onCollision(GameObject go);
    Hitbox getHitbox();
}
