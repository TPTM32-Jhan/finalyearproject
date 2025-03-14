import java.awt.Color;
import java.util.Random;

public class Property extends Entity{
    // Class fields(Characteristics shared by all properties)
    // Interest Rate, Inflation Rate, Other market properties..
    

    // Instance Fields(Individual Characteristics)
    private double price; // Property's value on the market
    private Field field;
    private PropertyState state; //State of the property/house
    private Agent owner; //Owner of the property

    public Property(Field field, Location location){
        super(field,location);
        this.state = getRandomState();
        handleState();
    }

    // Method to handle the current state
    public void handleState(){
        state.handleState(this);
    }

    public PropertyState getState(){
        return this.state;
    }
    // Method to set the current state of the property
    public void setState(PropertyState state){
        this.state = state;
        handleState(); //re-handle the new state
    }

    public Color getStateColor(){
        return state.getColor();
    }
    // isOccupied state property
    public boolean isOccupied(){
        return state instanceof OccupiedState;
    }
    // isForSale State property 
    public boolean isForSale(){
        return state instanceof ForSaleState;
    }

    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }

    private PropertyState getRandomState(){
        Random rand = Randomizer.getRandom();
        if(rand.nextBoolean()){
            return new ForSaleState();
        } else {
            return new OccupiedState();
        }
    }

    public void updatePrice(double interestRate, double inflationRate){
        this.price = price * (1 + interestRate) * (1 + inflationRate); // Adjust the price according to inflation rate
    }

    public void setOwner(Agent newOwner){
        owner = newOwner;
    }

    public Agent getOwner(){
        return owner;
    }

    /*
     * Increase price if near a facility function.
     * nearFacility()
     */

     public void adjustPriceForFacilities() {
        // if (isNearFacility("school")) {
        //     this.price *= 1.05; // Increase price by 5% for proximity to a school
        // }
        // if (isNearFacility("hospital")) {
        //     this.price *= 1.02; // 2% increase for proximity to a hospital
        // }
    }


}