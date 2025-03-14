
import java.awt.Color;


public class OccupiedState implements PropertyState{

    @Override
    public void handleState(Property property){
        if(property.isOccupied()){
            System.out.println("Property is Occupied");
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

    


}
