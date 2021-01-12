package streetfighter.interfaces;

import javafx.scene.Node;

/**
    * Interface pour définir un objet visible ayant un rendu graphique
    * @author Léo D
 */
public interface Renderable {
    Node getRenderer();
    void draw();
}
