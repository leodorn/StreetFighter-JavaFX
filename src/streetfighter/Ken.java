package streetfighter;

import streetfighter.interfaces.Collidable;
import streetfighter.interfaces.Renderable;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import streetfighter.managers.InputManager;
import streetfighter.other.GameObject;
import streetfighter.other.Hitbox;
import streetfighter.other.Hurtbox;

public class Ken extends Character implements Collidable, Renderable {

    final Image iStance = new Image("streetfighter/Ken/Stance.gif", 156, 222, true, false);
    final Image iWalkForward = new Image("streetfighter/Ken/WalkForward.gif", 224, 226, true, false);
    final Image iWalkBackward = new Image("streetfighter/Ken/WalkBackward.gif", 224, 226, true, false);
    final Image iWin = new Image("streetfighter/Ken/Win.gif",224, 226, true, false);
    final Image iCrouch = new Image("streetfighter/Ken/Crounching.gif", 176, 277, true, false);
    
    final Image iKO = new Image("streetfighter/Ken/KO.gif",224, 226, true, false);

    final Image iSpecialAtk = new Image("streetfighter/Ken/SpecialAttack.gif", 300, 250, true, false);
    
    final Attack atkLightPunch = new Attack(400, 6, "PunchLight", 180, 30, 60, 20);
    final Attack atkHeavyPunch = new Attack(840, 6, "PunchHeavy", 170, 20, 80, 30);
    final Attack atkLightKick = new Attack(420, 6, "KickLight", width, height, 40, 20);
    final Attack atkHeavyKick = new Attack(590, 6, "KickHeavy", width, height, 40, 20);

    final Attack specialAtk =new Attack(500, 0, "SpecialAttack", width, height, 40, 20);
    
    Character ryu;
    ImageView renderer;
    private CharacterState state;
    final List<KeyCode> specialAttack = new ArrayList<>();
    final List<KeyCode> specialAttackFinal = new ArrayList<>();
    
//    Rectangle renderer;

    public Ken(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, speed);
        renderer = new ImageView(iStance);
        renderer.setX(x);
        renderer.setY(y);
        resetXPosition = x;
        resetYPosition = y;
        specialAttackFinal.add(KeyCode.NUMPAD7);
        specialAttackFinal.add(KeyCode.NUMPAD9);
        specialAttackFinal.add(KeyCode.NUMPAD3);
    }

    @Override
    public void update() {
        super.update();
        if (canMove) {
            hitbox.getRectangle().setY(y);

            if (state != CharacterState.ATTACKING) {
                if (InputManager.getKey(KeyCode.NUMPAD6) && state != CharacterState.CROUCH) {
                    state = CharacterState.MOVING_RIGHT;
                    this.x += speed;
                } else if (InputManager.getKey(KeyCode.NUMPAD4) && state != CharacterState.CROUCH) {
                    state = CharacterState.MOVING_LEFT;
                    this.x += -speed;
                } else if (InputManager.getKey(KeyCode.NUMPAD5)) {
                    state = CharacterState.CROUCH;
                    hitbox.getRectangle().setY(y + 76);
                } else {
                    state = CharacterState.STANCE;
                }
                
                if(state != CharacterState.CROUCH)
                {
                    if (InputManager.getTempKey(KeyCode.NUMPAD7)) {
                        attack(atkLightPunch);
                        setSpecialAttack(KeyCode.NUMPAD7);
                        for (KeyCode KC : specialAttack)
                            System.out.println(KC);
                        System.out.println();
                    }
                    if (InputManager.getTempKey(KeyCode.NUMPAD1)) {
                        attack(atkHeavyPunch);
                        setSpecialAttack(KeyCode.NUMPAD1);
                        for (KeyCode KC : specialAttack)
                            System.out.println(KC);
                        System.out.println();
                    }
                    if (InputManager.getTempKey(KeyCode.NUMPAD9)) {
                        attack(atkLightKick);
                        setSpecialAttack(KeyCode.NUMPAD9);
                        for (KeyCode KC : specialAttack)
                            System.out.println(KC);
                        System.out.println();
                    }
                    if (InputManager.getTempKey(KeyCode.NUMPAD3)) {
                        attack(atkHeavyKick);
                        setSpecialAttack(KeyCode.NUMPAD3);
                        for (KeyCode KC : specialAttack)
                            System.out.println(KC);
                        System.out.println();
                    }
                    if (specialAttack.equals(specialAttackFinal)) {
                        System.out.println("hadoken");
                        specialAttack();
                        specialAttack.clear();
                    }
                }
                
            }
            if(ryu.getX() > x) {
            facing = FacingDirection.RIGHT;
        } else {
            facing = FacingDirection.LEFT;
        }
        }
    }
    
     public void attack(Attack attack) {
        Hurtbox hurtbox;

        state = CharacterState.ATTACKING;
        renderer.setImage(attack.getSprite("streetfighter/Ken"));
        if(facing == FacingDirection.RIGHT) {
            hurtbox = new Hurtbox(x + attack.getXOff(), y + attack.getYOff(), attack.getWidth(), attack.getHeight(), attack.getDamage(), (Character) this);
        } else {
            hurtbox = new Hurtbox(x - attack.getXOff() +100, y + attack.getYOff(), attack.getWidth(), attack.getHeight(), attack.getDamage(), (Character) this);
            renderer.setX(x - renderer.getImage().getWidth() + iStance.getWidth());
        }
        FightManager.getGoWaitList().add(hurtbox);
        Timer timer = new Timer();
        FightManager.instance.listTimer.add(timer);
        TimerTask decay = new TimerTask() {
            @Override
            public void run() {
                renderer.setImage(iStance);
                state = CharacterState.STANCE;
                FightManager.getGoGarbage().add(hurtbox);
            }
        };
        timer.schedule(decay, attack.getDuration());
    }

    public void specialAttack() {
        Hadoken hadoken;
        state = CharacterState.ATTACKING;
        renderer.setImage(iSpecialAtk);

        if(facing == FacingDirection.RIGHT) {
            hadoken = new Hadoken(x + 180,  y + 31,120, 60, 15, this, 8, 2000);
        } else {
            hadoken = new Hadoken(x - 180,  y + 31,120, 60, 15, this, -8, 2000);
            hadoken.setScaleXRenderer(-1);
        }
        FightManager.getGoWaitList().add(hadoken);
        Timer timer = new Timer();
        FightManager.instance.listTimer.add(timer);
        TimerTask decay = new TimerTask() {
            @Override
            public void run() {
                renderer.setImage(iStance);
                state = CharacterState.STANCE;
                FightManager.getGoGarbage().add(hadoken);
            }
        };
        timer.schedule(decay, hadoken.duration);
    }

    public void setSpecialAttack(KeyCode KC){
        if (specialAttack.size() >= 3) {
            specialAttack.remove(0);
        }
        specialAttack.add(KC);
    }
    
     @Override
    public void draw() {
       if(canMove && state != CharacterState.ATTACKING)
        {
           //renderer.resizeRelocate(x, y, width, height);
            renderer.setX(x);
            if(facing == FacingDirection.RIGHT) {
                renderer.setScaleX(1);
            } else {
                renderer.setScaleX(-1);
            }

            renderer.setY(y);

            switch (state) {
                case STANCE:
                    renderer.setImage(iStance);
                    renderer.setX(x);
                    break;
                case MOVING_LEFT:
                    if(facing == FacingDirection.RIGHT) {
                        renderer.setImage(iWalkBackward);
                        renderer.setX(x - renderer.getImage().getWidth() / 2 + iStance.getWidth() / 2);
                    } else {
                        renderer.setImage(iWalkForward);
                        renderer.setX(x);
                    }
                    break;
                case MOVING_RIGHT:
                    if(facing == FacingDirection.RIGHT) {
                        renderer.setImage(iWalkForward);
                        renderer.setX(x - renderer.getImage().getWidth() / 2 + iStance.getWidth() / 2);
                    } else {
                        renderer.setImage(iWalkBackward);
                        renderer.setX(x);
                    }
                    break;
                case CROUCH:
                    renderer.setImage(iCrouch);
                    renderer.setY(y + 76);
            }
        }
    }

    

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public Node getRenderer() {
        return renderer;
    }

    @Override
    public void resetPosition()
    {
        System.out.println(resetXPosition);
        renderer.setX(resetXPosition);
        renderer.setY(resetYPosition);
        renderer.setImage(iStance);
        state = CharacterState.STANCE;
    }
    
    @Override
    public void setOtherPlayer(Character ryu)
    {
        this.ryu = ryu;
    }

    @Override
    public void win() {
        roundWon++;
        renderer.setImage(iWin); 
    }
    @Override
    public void ko()
    {
        renderer.setImage(iKO);
    }
    
    
    
    

   
    

            
}
