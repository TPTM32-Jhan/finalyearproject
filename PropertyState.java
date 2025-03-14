import java.awt.Color;

public interface PropertyState {

    void handleState(Property property); // Handle a particulate state
    Color getColor(); // Retrieve State color

}
