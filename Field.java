import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Field {

    // Class variables
    private static final Random rand = Randomizer.getRandom();

    // instance fields
    private int depth;
    private int width;

    private Object[][] field;

    public Field(int depth, int width){
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
    }

    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }

    public void clear(Location location)
    {
        field[location.getRow()][location.getCol()] = null;
    }

    public void place(Object agent, int row, int col)
    {
        place(agent, new Location(row, col));
    }

    public void place(Object agent, Location location)
    {
        field[location.getRow()][location.getCol()] = agent;
    }

    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }

    public Object getObjectAt(int row, int col)
    {
        return field[row][col];
    }

    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }

    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    public int getDepth()
    {
        return depth;
    }

    public int getWidth()
    {
        return width;
    }

}
