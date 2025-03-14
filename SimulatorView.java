
import java.awt.Color;

public interface SimulatorView {
    // Define color for a given class of actor
    void setColor(Class<?> actorClass, Color color);
    // Prepare for a new run
    void reset();
    // show current status of the field
    // which step it is, and the field whose status is to be displayed
    void showStatus(int step, Field field);
    // Determine whether simulation should continue to run,
    // return true if there is more than one agent alive
    boolean isViable(Field field);

}
