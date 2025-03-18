
public abstract class Agent {
    protected double budget;
    protected double propertyValue;
    
    private Property occupiedProperty;
    private Location location;
    private Field field;

    public Agent(Field field, Location location){
        this.field = field;
        setLocation(location);
    }

    public abstract void act();
    //public abstract boolean isPreferredProperty(Property property);//agent preferences

    protected Location getLocation(){
        return this.location;
    }
    
    protected void setLocation(Location newLocation){
        if(location != null){
            field.clear(location);
        }
        this.location = newLocation;
        field.place(this, newLocation);
    }
    /*
     * Return agent's field
     */
    protected Field getField(){
        return field;
    }

    protected Agent getAgent(){
        return this;
    }


    protected double getBudget(){
        return budget;
    }

    protected void setBudget(double budget){
        this.budget = budget;
    }

    protected double getPropertyValue(Property property){
        //get their current propertyValue
        return property.getPrice();
    }

    protected void setPropertyValue(Property property, double newPropertyValue){
        // when agent wants to sell their current property
        if(property.isForSale()){
            property.setPrice(newPropertyValue);
        }
    }

    protected boolean canAffordProperty(Property property){
        return this.budget >= property.getPrice();
    }

    protected Property getOccupiedProperty(){
        return occupiedProperty;
    }
    

    /*
     * Idea, create random agents either people, or investor or banks
     * 
     */
    // private Agent getRandomAgents(){
    //     Random rand = Randomizer.getRandom();
    //     if(rand.nextBoolean()){

    //     }
    // }



}
