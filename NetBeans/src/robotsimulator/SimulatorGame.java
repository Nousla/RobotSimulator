package robotsimulator;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import robotsimulator.api.DirectRobotAPI;
import robotsimulator.api.DirectWorldAPI;
import robotsimulator.api.RobotAPI;
import robotsimulator.api.WorldAPI;
import robotsimulator.common.Position;
import robotsimulator.common.Robot;
import robotsimulator.common.Tile;
import robotsimulator.common.TileType;
import robotsimulator.common.World;

public class SimulatorGame extends BasicGame {
    private final Controller controller;

    private static final int CONTENT_OFFSET = 25;
    private static final int LINE_HEIGHT = 20;

    private Robot robot;
    private World world;
    private RobotAPI robotAPI;
    private WorldAPI worldAPI;
    private Simulation simulation;

    private String stateStr;
    private String itemStr;
    private String soundStr;

    private int movementTimer;

    private Rectangle[][] tileRects;
    private final int TileSize = 60;

    public SimulatorGame(Controller controller) {
        super("Simulator");
        this.controller = controller;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setShowFPS(false);

        robot = new Robot();
        robot.setSpeed(1);
        world = new World();

        robotAPI = new DirectRobotAPI(robot, world);
        worldAPI = new DirectWorldAPI(world);

        simulation = new Simulation() {
            @Override
            public RobotAPI getRobotAPI() {
                return robotAPI;
            }

            @Override
            public WorldAPI getWorldAPI() {
                return worldAPI;
            }
        };

        world.getTilesSource().setListener((Tile[][] element) -> {
            tileRects = new Rectangle[element.length][];
            for (int i = 0; i < element.length; i++) {
                tileRects[i] = new Rectangle[element[i].length];
                for (int j = 0; j < element[i].length; j++) {
                    tileRects[i][j] = new Rectangle(0, 0, TileSize, TileSize);
                }
            }
        });

        controller.init(simulation);

        robot.getChargingSource().setListener((Boolean element) -> {
            if (element) {
                stateStr = "charging";
            }
            else {
                stateStr = "idle";
            }
        });

        robot.getHeldItemSource().setListener((String element) -> {
            itemStr = element;
        });

        robot.getSoundSource().setListener((String element) -> {
            soundStr = element;
        });

        robot.getTargetPositionSource().setListener((Position element) -> {
            stateStr = "moving";
        });
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            gc.exit();
            return;
        }

        controller.tick(simulation);

        if (robot.getPosition() == robot.getTargetPosition()) {
            return;
        }

        movementTimer += delta;
        if (movementTimer >= robot.getSpeed() * 1000) {
            movementTimer -= robot.getSpeed() * 1000;
            robot.setPosition(robot.getTargetPosition());

            if (robot.getPosition().equals(robot.getTargetPosition())) {
                stateStr = "Idle";
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        renderText(g);
        renderWorld(gc, g);
    }

    private void renderText(Graphics g) {
        String stateMsg = String.format("State: %s", stateStr);
        String itemMsg = String.format("Item: %s", itemStr);
        String soundMsg = String.format("Sound: %s", soundStr);

        g.drawString(stateMsg, 0, 0);
        g.drawString(itemMsg, 0, LINE_HEIGHT);
        g.drawString(soundMsg, 0, LINE_HEIGHT * 2);
    }

    private void renderWorld(GameContainer gc, Graphics g) {
        int offsetX = gc.getWidth() / 2
                - (world.getTiles().length * TileSize) / 2;

        for (int i = 0; i < tileRects.length; i++) {
            for (int j = 0; j < tileRects[i].length; j++) {
                Rectangle rect = tileRects[i][j];
                rect.setX(offsetX + j * TileSize);
                rect.setY(i * TileSize + CONTENT_OFFSET);
                if (world.getTile(i, j).getType() == TileType.Wall) {
                    g.fill(rect);
                }
                g.draw(rect);
            }
        }

        g.drawOval(offsetX + robot.getPosition().getX() * TileSize,
                robot.getPosition().getY() * TileSize + CONTENT_OFFSET,
                TileSize, TileSize);
    }

    @Override
    public boolean closeRequested() {
        controller.finish();
        return super.closeRequested();
    }
}
