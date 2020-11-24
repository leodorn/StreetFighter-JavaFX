package streetfighter.managers;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public final class InputManager {
    private static Map<KeyCode, Boolean> keys = new HashMap<>();
    private static Map<KeyCode, Boolean> keysTemp = new HashMap<>();

    private InputManager() {

    }

    public static void setKey(KeyCode key, Boolean value) {
        keys.put(key, value);
    }

    public static void activateTempKey(KeyCode key) {
        if(!getKey(key))
            keysTemp.put(key, true);
    }

    public static void resetTempKeys() {
        keysTemp.keySet().removeAll(keysTemp.keySet());
    }

    public static Boolean getKey(KeyCode key) {
        if(keys.containsKey(key)) {
            return keys.get(key);
        }

        return false;
    }

    public static Boolean getTempKey(KeyCode key) {
        if(keysTemp.containsKey(key)) {
            return keysTemp.get(key);
        }

        return false;
    }


    public static class KeyPressed implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            activateTempKey(event.getCode());
            setKey(event.getCode(), true);
        }
    }

    public static class KeyReleased implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            setKey(event.getCode(), false);
        }
    }
}
