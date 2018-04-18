package robotsimulator.common;

public class Robot {
    private boolean charging;
    private Position position;
    private Position targetPosition;
    private String heldItem;
    private String sound;
    // Speed is units per second
    private int speed;

    private final Source<Boolean> chargingSource;
    private final Source<Position> positionSource;
    private final Source<Position> targetPositionSource;
    private final Source<String> heldItemSource;
    private final Source<String> soundSource;

    public Robot() {
        chargingSource = new Source<>();
        positionSource = new Source<>();
        targetPositionSource = new Source<>();
        heldItemSource = new Source<>();
        soundSource = new Source<>();
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
        chargingSource.update(charging);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        setCharging(false);
        positionSource.update(position);
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Position targetPosition) {
        this.targetPosition = targetPosition;
        setCharging(false);
        targetPositionSource.update(position);
    }

    public String getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(String heldItem) {
        this.heldItem = heldItem;
        setCharging(false);
        heldItemSource.update(heldItem);
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
        soundSource.update(sound);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Source<Boolean> getChargingSource() {
        return chargingSource;
    }

    public Source<Position> getPositionSource() {
        return positionSource;
    }

    public Source<String> getHeldItemSource() {
        return heldItemSource;
    }

    public Source<String> getSoundSource() {
        return soundSource;
    }

    public Source<Position> getTargetPositionSource() {
        return targetPositionSource;
    }
}
