package robotsimulator.api;

import robotsimulator.common.Position;

public interface RobotAPI {
    void moveTo(int x, int y);

    void playSound(String sound);

    void pickupItem();

    void dropoffItem();

    void charge();
    
    Position getPosition();
    
    boolean isPlayingSound();
    
    String getHeldItem();
    
    boolean isCharging();
    
    void setSpawnpoint(Position position);
}
