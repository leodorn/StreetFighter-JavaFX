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
import streetfighter.other.Hitbox;
import streetfighter.other.Hurtbox;

public class Ryu extends Character implements Collidable, Renderable {

    final Image iStance = new Image("streetfighter/Ryu/Stance.gif", 156, 222, true, false);
    final Image iWalkForward = new Image("streetfighter/Ryu/WalkForward.gif", 224, 226, true, false);
    final Image iWalkBackward = new Image("streetfighter/Ryu/WalkBackward.gif", 224, 226, true, false);
    final Image iCrouch = new Image("streetfighter/Ryu/Crouch.gif", 176, 277, true, false);
    
    final Image iWin  = new Image("streetfighter/Ryu/Win.gif",224, 226, true, false);
    final Image iKO = new Image("streetfighter/Ryu/KO.gif",224, 226, true, false);

    final Image iSpecialAtk = new Image("streetfighter/Ryu/SpecialAttack.gif", 300, 250, true, false);

    Timer crouchTimer = new Timer();

    final Attack atkLightPunch = new Attack(400, 4.8, "PunchLight", width + 24, 30, 60, 20);
    final Attack atkHeavyPunch = new Attack(840, 8.4, "PunchHeavy", width + 14, 20, 80, 30);
    final Attack atkLightKick = new Attack(420, 4.2, "KickLight", width + 50, height - 80, 40, 50);
    final Attack atkHeavyKick = new Attack(590, 5.9, "KickHeavy", width, height, 40, 20);

    Character ken;
    ImageView renderer;
    final List<KeyCode> specialAttackFinal = new ArrayList<>();
    List<KeyCode> specialAttack = new ArrayList<>();

    public Ryu(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, speed);
        renderer = new ImageView(iStance);
        renderer.setX(x);
        renderer.setY(y);
        resetXPosition = x;
        resetYPosition = y;
        specialAttackFinal.add(KeyCode.A);
        specialAttackFinal.add(KeyCode.E);
        specialAttackFinal.add(KeyCode.C);
        state = CharacterState.STANCE;
    }

    public void attack(Attack attack) {
        Hurtbox hurtbox;

        state = CharacterState.ATTACKING;
        renderer.setImage(attack.getSprite("streetfighter/Ryu"));
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

    @Override
    public void update() {
        super.update();
        if (canMove) {
            hitbox.getRectangle().setY(y);
            if (state != CharacterState.ATTACKING) {
                if (InputManager.getKey(KeyCode.D) && state != CharacterState.CROUCH) {
                    state = CharacterState.MOVING_RIGHT;
                    this.x += speed;
                } else if (InputManager.getKey(KeyCode.Q) && state != CharacterState.CROUCH) {
                    state = CharacterState.MOVING_LEFT;
                    this.x += -speed;
                } else if (InputManager.getKey(KeyCode.S)) {
                    state = CharacterState.CROUCH;
                    hitbox.getRectangle().setY(y + 76);
                } else {
                    state = CharacterState.STANCE;
                }
                
                if(state != CharacterState.CROUCH)
                {
                    if (InputManager.getTempKey(KeyCode.A)) {
                        attack(atkLightPunch);
                        setSpecialAttack(KeyCode.A);
                    }
                    if (InputManager.getTempKey(KeyCode.W)) {
                        attack(atkHeavyPunch);
                        setSpecialAttack(KeyCode.W);
                    }
                    if (InputManager.getTempKey(KeyCode.E)) {
                        attack(atkLightKick);
                        setSpecialAttack(KeyCode.E);
                    }
                    if (InputManager.getTempKey(KeyCode.C)) {
                        attack(atkHeavyKick);
                        setSpecialAttack(KeyCode.C);
                    }
                    if (specialAttack.equals(specialAttackFinal)) {
                        specialAttack();
                        specialAttack.clear();
                    }
                }

               
            }
        }
        if(ken.getX() > x) {
            facing = FacingDirection.RIGHT;
        } else {
            facing = FacingDirection.LEFT;
        }

    }

    public void setSpecialAttack(KeyCode KC){
        if (specialAttack.size() >= 3) {
            specialAttack.remove(0);
        }
        specialAttack.add(KC);
    }

    
     @Override
    public void resetPosition()
    {
        x = resetXPosition;
        y = resetYPosition;
        renderer.setImage(iStance);
        state = CharacterState.STANCE;
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
     public void setOtherPlayer(Character ken)
    {
        this.ken = ken;
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
