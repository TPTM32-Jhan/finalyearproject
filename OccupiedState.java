
import java.awt.Color;


public class OccupiedState implements PropertyState{

    private Agent owner;


    public OccupiedState(Agent occupant){
        this.owner = occupant;
    }

    @Override
    public void handleState(Property property){
        if(property.isOccupied()){
            System.out.println("Property is Occupied");
            // System.out.println(property.getOwner()); // Yes it works, there is an agent here
        }
        // Add logic for occupied property
        // Behaviour when they are in this state.
        /*
         * Unable to sell property
         * etc
         */

        //Add something here..

        // Transition to ForSaleState when moving out
        // property.setState(new ForSaleState());
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    // Dynamic transition, method to simulate owner moving out and property going for sale 
    public void moveOut(Property property){
        // add logic to check whether owners have decided to sell
        System.out.println(owner.getAgent() + "has moved out, property now for sale");
        property.setState(new ForSaleState());
    }

    // Prevent property sale, check if property can be sold
    public boolean canBeSold(){
        return false; // can't be sold if occupied
    }


}
