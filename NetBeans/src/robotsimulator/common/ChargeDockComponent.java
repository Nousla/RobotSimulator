package robotsimulator.common;

public class ChargeDockComponent implements Component {
    @Override
    public void interact(Robot robot) {
        robot.setCharging(true);
    }
}