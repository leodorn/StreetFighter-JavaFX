package streetfighter;

import javafx.scene.shape.Rectangle;
import streetfighter.other.GameObject;
import streetfighter.other.Hitbox;
import streetfighter.other.Hurtbox;

public abstract class Character extends GameObject {
    protected CharacterState state;
    protected FacingDirection facing;
    protected double speed;
    protected Hitbox hitbox;
    private double healthPoint = 100;
    protected boolean canMove = true;
    protected int roundWon = 0;
    protected double resetXPosition;
    protected double resetYPosition;

    public Character(double x, double y, double width, double height, double speed) {
        super(x, y, width, height);
        this.hitbox = new Hitbox(x, y, width, height);

        this.speed = speed;
    }

    public double getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(double healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void takeDamage(double damage){
        setHealthPoint(getHealthPoint()-damage);
        if(healthPoint<=0)
        {
            FightManager.instance.finishRound();
        }
    }

    public void heal(int heal){
        setHealthPoint(getHealthPoint() + heal);
        if(healthPoint>100)
            setHealthPoint(100);
    }
    
    
    public void onCollision(GameObject go) {
        if(go instanceof Hurtbox) {
            if(!((Hurtbox) go).getOwner().equals(this)) {
                this.takeDamage(((Hurtbox) go).getDamage());
            }
        }
    }
    
   
    
   
    public abstract void ko();
    public abstract void win();
    public abstract void setOtherPlayer(Character otherPlayer);
    public abstract void resetPosition();       

    @Override
    public void update() {
        this.hitbox.getRectangle().setX(x);
    }
    
    
}
