package robotsimulator;

public class Main {
    public static void main(String[] args) {
        Simulator sim = new SlickSimulator();
        sim.start(new TestController());
    }
}