package robotsimulator.common;

public class DropoffComponent implements Component {
    private final String item;

    public DropoffComponent(String item) {
        this.item = item;
    }
    
    @Override
    public void interact(Robot robot) {
        if(robot.getHeldItem().equals(item)){
            robot.setHeldItem("");
        }
    }
}