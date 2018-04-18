package robotsimulator;

import robotsimulator.api.RobotAPI;
import robotsimulator.api.WorldAPI;

public interface Simulation {
    RobotAPI getRobotAPI();
    WorldAPI getWorldAPI();
}
