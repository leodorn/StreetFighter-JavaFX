package streetfighter;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leodo_000
 */
public class CountDown {
    
    int interval;
    static Timer timer;
    SimpleIntegerProperty integer;
    
    public CountDown(Label label,int interval,Character player1,Character player2)
    {
        this.interval = interval;
        integer = new SimpleIntegerProperty(this.interval);
        label.textProperty().bind(integer.asString());
}
   
       
        
        
        
    public void startTimer()
    {
        /* -------------------- Timer ------------------- */
        
       timer = new Timer();
       interval = 100;
       integer.set(interval);
       FightManager.instance.listTimer.add(timer);
       timer.scheduleAtFixedRate(new TimerTask(){

           @Override
           public void run()
           {
                Platform.runLater(() -> {
                    interval--;
                    integer.set(interval);
                    if(interval <= 1) {
                        FightManager.instance.finishRound();
                        timer.cancel();
                    }
                });
           }
       }, 0, 1000);
    }
    
    public void stopTimer()
    {
        timer.cancel();
    }

}
