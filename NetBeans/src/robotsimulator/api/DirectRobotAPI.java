package robotsimulator.api;

import robotsimulator.common.Component;
import robotsimulator.common.Position;
import robotsimulator.common.Robot;
import robotsimulator.common.Tile;
import robotsimulator.common.World;

public class DirectRobotAPI implements RobotAPI {
    private final Robot robot;
    private final World world;

    public DirectRobotAPI(Robot robot, World world) {
        this.robot = robot;
        this.world = world;
    }

    @Override
    public void moveTo(int x, int y) {
        robot.setTargetPosition(new Position(x, y));
    }

    @Override
    public void playSound(String sound) {
        robot.setSound(sound);
    }

    @Override
    public void pickupItem() {
        interact();
    }

    @Override
    public void dropoffItem() {
        interact();
    }

    @Override
    public void charge() {
        interact();
    }

    @Override
    public Position getPosition() {
        return robot.getPosition();
    }

    @Override
    public boolean isPlayingSound() {
        return robot.getSound() == null;
    }

    @Override
    public String getHeldItem() {
        return robot.getHeldItem();
    }

    @Override
    public boolean isCharging() {
        return robot.isCharging();
    }

    private void interact() {
        Position pos = robot.getPosition();
        Tile tile = world.getTile(pos.getX(), pos.getY());
        Component component = tile.getComponent();
        if(component != null) {
            component.interact(robot);
        }
    }

    @Override
    public void setSpawnpoint(Position position) {
        robot.setPosition(position);
    }
}
