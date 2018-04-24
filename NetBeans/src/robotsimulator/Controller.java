package robotsimulator;

public interface Controller {
    void init(Simulation simulation);
    void tick(Simulation simulation);
    void finish();
}
