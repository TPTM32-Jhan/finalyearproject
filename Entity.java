public abstract class Entity {

    Location location;
    Field field;

    public Entity(Field field, Location location){
        this.field = field;
        setLocation(location);
    }

    public void setLocation(Location newlocation){
        if(location != null){
            field.clear(location);
        }
        this.location = newlocation;
        field.place(this, newlocation);
    }

    public Location getLocation(){
        return this.location;
    }

}
