import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class People extends Agent{

    private List<?> preferences; //Family list, or something else
    private int familySize; //need 
    private double income; //budget, need
    // private List<FamilyMember> familyMembers;
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
        Location newLocation = findProperty();
        if(newLocation == null){
            // No property found - try to move to a free location
            newLocation = getField().freeAdjacentLocation(getLocation());
        }

        if(newLocation != null){
            setLocation(newLocation);
        }

    }

    private Location findProperty(){
        /*
         * specify coordinate/location and region of the house they want to go to.
         * 
         */
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()){
            Location where = it.next();
            Object agent = field.getObjectAt(where);
            if(agent instanceof Property){
                Property property = (Property) agent;
                if(property.isForSale() && canAffordProperty(property)){
                    property.setOwner(this);
                    property.setState(new OccupiedState());
                    return where;
                }
            }
        }
        return null;
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
