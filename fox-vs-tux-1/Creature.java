import env3d.EnvObject;
import java.util.ArrayList;
  
abstract public class Creature extends EnvObject
{
    private double speed = 0.1;
    private int frame = 0;
    private int changeTime;
    /**
     * Constructor for objects of class Creature
     */
    public Creature(double x, double y, double z)
    {
        setX(x);
        setY(y);
        setZ(z);
        setScale(1);
        changeTime = (int)Math.random()*60;
    }
      
    
    public void move(ArrayList<Creature> creatures, ArrayList<Creature> dead_creatures)
    { 
        moveForward(speed);
        
        if (frame++ % 30 == 0) {
            setRotateY(Math.random()*360);
            changeTime = (int)Math.random()*60;
        }
          
        if (getX() < getScale()) setX(getScale());
        if (getX() > 50-getScale()) setX(50 - getScale());
        if (getZ() < getScale()) setZ(getScale());
        if (getZ() > 50-getScale()) setZ(50 - getScale());
   
    }      

    public void moveForward(double speed)
    {
        this.setX(this.getX()+speed*Math.sin(Math.toRadians(this.getRotateY())));
        this.setZ(this.getZ()+speed*Math.cos(Math.toRadians(this.getRotateY())));        
    }
    
    public void turnToFace(Creature gameObj) 
    {
        setRotateY(Math.toDegrees(Math.atan2(gameObj.getX()-this.getX(),gameObj.getZ()-this.getZ())));
        
        // Uncomment this next line if you want to look up and down as well
        // setRotateX(Math.toDegrees(Math.asin((this.getY()-gameObj.getY())/this.distance(gameObj))));        
    }    
}