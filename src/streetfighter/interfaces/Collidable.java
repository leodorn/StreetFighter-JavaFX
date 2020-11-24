package streetfighter.interfaces;

import streetfighter.other.GameObject;
import streetfighter.other.Hitbox;

public interface Collidable {
    void onCollision(GameObject go);
    Hitbox getHitbox();
}
