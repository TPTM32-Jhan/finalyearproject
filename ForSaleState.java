
import java.awt.Color;


public class ForSaleState implements PropertyState{

    @Override
    public void handleState(Property property){
        // Add logic for property being for sale
        /*
         * - potential buyer actions
         * - negotiation or price changes
         */
        System.out.println("Property is for Sale");
        // Add something here..
        // Transition to OccupiedState when occupied
        // property.setState(new OccupiedState());
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }


}
