package robotsimulator;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class SlickSimulator implements Simulator {
    private AppGameContainer appgc;

    @Override
    public void start(Controller controller) {
        try {
            appgc = new AppGameContainer(new SimulatorGame(controller));
            appgc.setDisplayMode(1280, 720, false);
            appgc.start();
        }
        catch (SlickException ex) {
            Logger.getLogger(SlickSimulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        if(appgc != null) {
            appgc.exit();
        }
    }
}
