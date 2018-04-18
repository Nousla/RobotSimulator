package robotsimulator;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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

public class Simulator extends BasicGame {
    private final Controller controller;

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

    public Simulator(Controller controller) {
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
                stateStr = "Charging";
            }
            else {
                stateStr = "Idle";
            }
        });

        robot.getHeldItemSource().setListener((String element) -> {
            itemStr = element;
        });

        robot.getSoundSource().setListener((String element) -> {
            soundStr = element;
        });

        robot.getTargetPositionSource().setListener((Position element) -> {
            stateStr = "Moving";
        });
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
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
        String format = "Current state: %s | Current held item: %s | Playing sound: %s";
        String message = String.format(format, stateStr, itemStr, soundStr);
        int messageHeight = g.getFont().getHeight(message);
        g.drawString(message, 0, 0);

        g.drawLine(0, messageHeight, gc.getWidth(), messageHeight);

        int offsetX = gc.getWidth() / 2
                - (world.getTiles().length * TileSize) / 2;

        for (int i = 0; i < tileRects.length; i++) {
            for (int j = 0; j < tileRects[i].length; j++) {
                Rectangle rect = tileRects[i][j];
                rect.setX(offsetX + j * TileSize);
                rect.setY(messageHeight + i * TileSize);
                if (world.getTile(i, j).getType() == TileType.Wall) {
                    g.fill(rect);
                }
                g.draw(rect);
            }
        }

        g.drawOval(offsetX + robot.getPosition().getX() * TileSize,
                messageHeight + robot.getPosition().getY() * TileSize,
                TileSize, TileSize);
    }
}
