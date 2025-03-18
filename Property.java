import java.awt.Color;
import java.util.List;
import java.util.Random;

public class Property extends Entity{
    // Class fields(Characteristics shared by all properties)
    // Interest Rate, Inflation Rate, Other market properties..
    

    // Instance Fields(Individual Characteristics)
    private double price; // Property's value on the market
    private PropertyState state; //State of the property/house
    private Agent owner; //Owner of the property

    public Property(Field field, Location location, Agent owner){
        super(field,location);
        this.owner = owner;
        setState(setRandomState());
        if(isOccupied()){
            setOwner(owner); //Set owner in the occupied state
        } else if(isForSale()){
            setOwner(null); // no owner if property is for sale
        }

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

    // Needed
    public Agent getOwner(){
        return owner;
    }

    private PropertyState setRandomState(){
        Random rand = Randomizer.getRandom();
        // Define probabilities for different states
        double forSaleProbability = 0.7; //70% chance property 
        double occupiedProbability = 0.3; // 30% chance property

        double chance = rand.nextDouble(); //Generate random num between 0.0 and 1.0

        // Depending on the chance, return the appropriate state
        if(chance < forSaleProbability){
            return new ForSaleState(); //Property is forsale 
        } else {
            return new OccupiedState(owner); //property is occupied with an owner(need to handle owner logic)
        }

        /* Improvements for external factors 
         * private PropertyState getRandomState(double marketConditionFactor) {
            Random rand = Randomizer.getRandom();

            // Modify probabilities based on market conditions (e.g., higher inflation leads to more properties for sale)
             double forSaleProbability = 0.7 * marketConditionFactor; // Adjust probability by market condition
             double occupiedProbability = 1.0 - forSaleProbability;
         */
    }

    public void updatePrice(double interestRate, double inflationRate){
        this.price = price * (1 + interestRate) * (1 + inflationRate); // Adjust the price according to inflation rate
    }

    // Method to allow an agent to occupy the property (if in correct state)
    public void setOwner(Agent newOwner){
        if(state instanceof OccupiedState){
            this.owner = newOwner; // set the owner for occupied property
        } else if(state instanceof ForSaleState){
            this.owner = null; // no owner if property is for sale 
        }
    }

    public void adjustPriceForFacilities(List<Facility> facilities) {
        for(Facility facility : facilities){
            double distance = getDistanceToFacility(facility);
            if(distance <= 3){
                this.price = price * 1.1;
                break;
            }
        }
    }

    private double getDistanceToFacility(Facility facility){
        Location propertyLocation = this.getLocation();
        Location facilityLocation = facility.getLocation();

        return Math.abs(propertyLocation.getRow() - facilityLocation.getRow()) 
        + Math.abs(propertyLocation.getCol() - facilityLocation.getCol());
    }


}