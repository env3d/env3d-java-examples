import java.util.ArrayList;

public class Fox extends Creature
{
    public Fox(double x, double y, double z)
    {
        super(x, y, z);
          
        // Must use the mutator as the fields have private access
        // in the parent class
        setTexture("models/fox/fox.png");
        setModel("models/fox/fox.obj");
    }    
    
    public void move(ArrayList<Creature> creatures, ArrayList<Creature> dead_creatures) {
        for (Creature c : creatures) {
            if (c.distance(this) < c.getScale()+this.getScale() && c instanceof Tux) {
                dead_creatures.add(c);
            }
        }
        
        // Find a tux that is close to me
        for (Creature c : creatures) {
            if (c instanceof Tux) {
                if (c.distance(this) < 5) {
                    this.turnToFace(c);
                }
            }
        }
        
        super.move(creatures, dead_creatures);
    }
}