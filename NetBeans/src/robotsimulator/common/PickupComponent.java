package robotsimulator.common;

public class PickupComponent implements Component {
    
    private final String item;
    
    public PickupComponent(String item) {
        this.item = item;
    }
    
    @Override
    public void interact(Robot robot) {
        robot.setHeldItem(item);
    }
}