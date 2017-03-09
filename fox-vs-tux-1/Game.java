import env3d.Env;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

/**
 * A predator and prey simulation. Fox is the predator and Tux is the prey.
 */
public class Game {

    private Env env;
    private boolean finished;

    private double yaw = 0;
    private double pitch = -35;
    private double dist = 65;
    
    private ArrayList<Creature> creatures;
    private ArrayList<Creature> dead_creatures;

    /**
     * Constructor for the Game class. It sets up the foxes and tuxes.
     */
    public Game() {        
        // Create the new environment.  Must be done in the same
        // method as the game loop
        env = new Env();
        
        // Sets up the camera
        env.setCameraXYZ(25, 50, 55);
        env.setCameraPitch(pitch);

        // Turn off the default controls
        env.setDefaultControl(false);

        // Make the room 50 x 50.
        env.setRoom(new Room());
        creatures = new ArrayList<Creature>();
    }

    private void setup() {
        finished = false;
        
        // we use a separate ArrayList to keep track of each animal. 
        // our room is 50 x 50.
        for (Creature c: creatures) {
            env.removeObject(c);
        }
        creatures.clear();
        for (int i = 0; i < 55; i++) {
            if (i < 5) {
                creatures.add(new Fox((int) (Math.random() * 48) + 1, 1, (int) (Math.random() * 48) + 1));
            } else {
                creatures.add(new Tux((int) (Math.random() * 48) + 1, 1, (int) (Math.random() * 48) + 1));
            }
        }

        // Add all the animals into to the environment for display
        for (Creature c : creatures) {
            env.addObject(c);
        }

        // A list to keep track of dead tuxes.
        dead_creatures = new ArrayList<Creature>();

    }
    

    private void loop() {
        // UI Control
        if (env.getKey() == 1) {
            finished = true;
        }

        if (env.getKeyDown() == Keyboard.KEY_LEFT) {
            yaw += 1;
        }        
        if (env.getKeyDown() == Keyboard.KEY_RIGHT) {
            yaw -= 1;
        }
        if (env.getKeyDown() == Keyboard.KEY_UP) {
            pitch += 1;
        }
        if (env.getKeyDown() == Keyboard.KEY_DOWN) {
            pitch -= 1;
        }
        if (env.getKeyDown() == Keyboard.KEY_Z) {
            dist += 1;
        }
        if (env.getKeyDown() == Keyboard.KEY_A) {
            dist -= 1;
        }
        
        // Change the camera based on user controlled pitch and yaw.
        // We use a spherical coordinate system around a center point,
        // the center point for this simulation is at 25, 0, 25
        double camx = 25 + dist * Math.sin(Math.toRadians(yaw))* Math.cos(Math.toRadians(-pitch));
        double camy = 0 + dist * Math.sin(Math.toRadians(-pitch));
        double camz = 25 + dist * Math.cos(Math.toRadians(yaw))* Math.cos(Math.toRadians(-pitch));                
        env.setCameraXYZ(camx, camy, camz);        
        env.setCameraYaw(yaw);
        env.setCameraPitch(pitch);        
        
        // Move each fox and tux.
        for (Creature c : creatures) {
            c.move(creatures, dead_creatures);
        }

        // Clean up of the dead tuxes.
        for (Creature c : dead_creatures) {
            env.removeObject(c);
            creatures.remove(c);
        }
        // we clear the ArrayList for the next loop.  We could create a new one 
        // every loop but that would be very inefficient.
        dead_creatures.clear();
        boolean hasTux = false;
        for (Creature c : creatures) {
            if (c instanceof Tux) {
                hasTux = true;
                break;
            }
        }
        if (!hasTux) {
            setup();
        }
    }

    /**
     * Play the game
     */
    public void play() {
        setup();
        
        // The main game loop
        while (!finished) {
            loop();
            // Update display
            env.advanceOneFrame();
        }

        // Just a little clean up
        env.exit();
    }

    /**
     * Main method to launch the program.
     */
    public static void main(String args[]) {
        (new Game()).play();
    }
}
