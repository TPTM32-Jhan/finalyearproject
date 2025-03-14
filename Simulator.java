import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator{

    private static final int DEFAULT_WIDTH = 120;
    private static final int DEFAULT_DEPTH = 80;
    private static final double FACILITY_CREATION_PROBABILITY = 0.02;
    private static final double PROPERTY_CREATION_PROBABILITY = 0.1;
    private static final double PEOPLE_CREATION_PROBABILITY = 0.15;


    // List of Entities in the field
    private List<Entity> entities;
    // List of Agents in the field
    private List<Agent> agents;
    // current state of the field
    private Field field;
    // Current step of the simulation
    private int step;
    // Graphical view of the simulation
    private List<SimulatorView> views;    

    public Simulator(){
        this(DEFAULT_DEPTH,DEFAULT_WIDTH);
    }

    public Simulator(int depth, int width){

        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        entities = new ArrayList<>();
        field = new Field(depth, width);
        agents = new ArrayList<>();

        views = new ArrayList<>();

        SimulatorView view = new GridView(depth, width);
        view.setColor(Facility.class, Color.BLUE);
        view.setColor(Property.class, Color.YELLOW);
        
        views.add(view);

        reset();
    }

    public void reset(){
        step = 0;
        entities.clear();
        agents.clear();
        for (SimulatorView view : views) {
            view.reset();
        }
        populate();
        updateViews();

    }

    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= PROPERTY_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Property property = new Property(field,location);
                    entities.add(property);
                    // field.place(property, location);
                }
                if(rand.nextDouble() <= FACILITY_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Entity facility = new Facility(field, location);
                    entities.add(facility);
                    // field.place(facility, location);
                }
                if(rand.nextDouble() <= PEOPLE_CREATION_PROBABILITY){
                    Location location = new Location(row, col);
                    Agent people = new People(field,location);
                    agents.add(people);
                    // field.place(people, location);
                }
                
            }
                // else leave the location empty.
        }
    }

    private void updateViews(){
        for(SimulatorView view: views){
            view.showStatus(step, field);
        }
    }
}
