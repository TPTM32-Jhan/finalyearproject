import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GridView extends JFrame implements SimulatorView{
    // Colors used for empty locations
    public static final Color EMPTY_COLOR = Color.white;
    // Color used for objects that have no defined color
    private static final Color UNKNOWN_COLOR = Color.GRAY;

    private final String STEP_PREFIX = "Step: ";
    private final String ENTITIES_PREFIX = "Entities: ";
    private JLabel stepLabel, agents;
    private FieldView fieldView;
    private FieldStats stats;

    // Map for storing colors for particpants in simulation
    private Map<Class<?>, Color> colors;
    
    public GridView(int height, int width){
        stats = new FieldStats();
        colors = new HashMap<>();
        
        setTitle("Housing Market Simulation");
        stepLabel = new JLabel(STEP_PREFIX,JLabel.CENTER);
        agents = new JLabel(ENTITIES_PREFIX, JLabel.CENTER);

        setLocation(20, 50);
        fieldView = new FieldView(height, width);
        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(agents, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        
    }
    
    @Override
    public void setColor(Class<?> actorClass, Color color) {
        colors.put(actorClass, color);
    }

    @Override
    public void reset() {
        stats.reset();
    }

    @Override
    public void showStatus(int step, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object obj = field.getObjectAt(row, col);
                if(obj != null) {
                    stats.incrementCount(obj.getClass());
                    fieldView.drawMark(col, row, getColor(obj.getClass()));
                    // Display different color for different property states
                    if(obj instanceof Property){
                        Property property = (Property) obj;
                        if(property.isOccupied()){ // Property State isOccupied
                            fieldView.drawMark(col, row, property.getStateColor()); // Color Red
                        } else if(property.isForSale()){ // Property State isForsale 
                            fieldView.drawMark(col, row, property.getStateColor()); // Color Green
                        }
                    }
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        agents.setText(ENTITIES_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }


    @Override
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    private Color getColor(Class<?> actorClass){
        Color col = colors.get(actorClass);
        if(col == null){
            return UNKNOWN_COLOR;
        } else {
            return col;
        }
    }


    private class FieldView extends JPanel {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        // Define boundaries for 6 regions (2 rows, 3 columns)
        private int regionWidth;
        private int regionHeight;

        public FieldView(int height, int width){
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0,0);

            // Calculate region width and height based on grid size
            regionWidth = gridWidth / 3; // 3 columns
            regionHeight = gridHeight / 2; // 2 rows

        }

        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        // New method to draw regions behind the grid
        private void drawRegions(Graphics g){
            // Define Colors for the regions
            Color[] regionColors = {
                Color.RED, Color.BLUE, Color.GREEN,
                Color.CYAN, Color.MAGENTA, Color.YELLOW
            };

            // Draw 6 regions (2 rows, 3 cols)
            for(int row = 0; row < 2; row++){ // 2 rows
                for(int col = 0; col < 3;col++){ // 3 cols
                    // Calculate the position of the region
                    int xStart = col * regionWidth;
                    int yStart = row * regionHeight;

                    // Set the color for the region 
                    g.setColor(regionColors[row * 3 + col]);
                    // Draw the region ( a rectangle for each region)
                    g.fillRect(xStart * xScale, yStart * yScale, regionWidth * xScale, regionHeight * yScale);
                }
            }
        }

        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
                // Draw the regions in the background
                drawRegions(g);
            }
        }

        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }

    

}
