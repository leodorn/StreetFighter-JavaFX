package streetfighter.managers;

import streetfighter.interfaces.Collidable;
import streetfighter.other.GameObject;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    public CollisionManager() {
    }

    public static void checkForCollisions(List<GameObject> gameObjects) {
        for(int i = 0; i < gameObjects.size(); i++) {
            GameObject go1 = gameObjects.get(i);
            for(int j = i; j < gameObjects.size(); j++) {
                GameObject go2 = gameObjects.get(j);

                if(go1 instanceof Collidable && go2 instanceof Collidable && !go1.equals(go2)) {
                    if(((Collidable) go1).getHitbox().getRectangle().intersects(((Collidable) go2).getHitbox().getBounds())) {
                        ((Collidable) go1).onCollision(go2);
                        ((Collidable) go2).onCollision(go1);
                    }
                }
            }
        }
    }
}
