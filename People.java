import java.util.ArrayList;
import java.util.List;

public class People extends Agent{

    private int familySize; //need 
    private double income; //budget, need
    private List<Property> propertiesList;

    public People(Field field, Location location){
        super(field,location);
        this.propertiesList = new ArrayList<>();
        income = 0; //default, but generate randomly
    }

    public void setIncome(double income){
        this.income = income;
    }

    public double getIncome(){
        return income;
    }

    @Override
    public void act() {
        /*
         * What this will do during for each step on the simulation
         */
        // Move towardss a property
        // Location newLocation = findProperty();
        // if(newLocation == null){
        //     // No property found - try to move to a free location
        //     newLocation = getField().freeAdjacentLocation(getLocation());
        // }

        // if(newLocation != null){
        //     setLocation(newLocation);
        // }

    }


    // @Override
    // public boolean isPreferredProperty(Property property) {
    //     // TODO Auto-generated method stub
    //     return property.getLocation();
    // }

    // Method to get property listings that fit the family's criteria
    // public void findAffordableProperties(int maxPrice or List<Property> properties) {
    //     System.out.println("\nAffordable Properties under $" + maxPrice + ":");
    //     for (PropertyListing listing : propertyListings) {
    //         if (listing.getPrice() <= maxPrice) {
    //             System.out.println(listing);
    //         }
    //     }
    // }

    /*
     * If the properties state is occupied
     * set the location of the people to the occupied properties location and 
     * region on the grid.
     * 
     */

}
