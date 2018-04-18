package robotsimulator;

import robotsimulator.common.ChargeDockComponent;
import robotsimulator.common.DropoffComponent;
import robotsimulator.common.PickupComponent;
import robotsimulator.common.Position;
import robotsimulator.common.Tile;
import robotsimulator.common.TileType;

public class TestController implements Controller {
    private boolean moved;

    @Override
    public void init(Simulation sim) {
        Tile[][] tiles = new Tile[][]{
            {new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty)},
            {new Tile(TileType.Wall), new Tile(TileType.Wall), new Tile(TileType.Wall), new Tile(TileType.Empty), new Tile(TileType.Empty)},
            {new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Wall)},
            {new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty)},
            {new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Empty), new Tile(TileType.Wall), new Tile(TileType.Empty)}
        };

        tiles[0][0].setComponent(new PickupComponent("square"));
        tiles[4][0].setComponent(new PickupComponent("triangle"));
        tiles[2][3].setComponent(new PickupComponent("circle"));
        tiles[0][2].setComponent(new ChargeDockComponent());
        tiles[0][4].setComponent(new DropoffComponent("circle"));
        tiles[2][4].setComponent(new DropoffComponent("square"));
        tiles[4][4].setComponent(new DropoffComponent("triangle"));

        sim.getWorldAPI().setTiles(tiles);
        sim.getRobotAPI().setSpawnpoint(new Position(2, 2));
    }

    @Override
    public void tick(Simulation sim) {
        if (!moved) {
            sim.getRobotAPI().moveTo(4, 0);
            moved = true;
        }
        
        if (sim.getRobotAPI().getPosition().getX() == 4 
                && sim.getRobotAPI().getPosition().getY() == 0) {
            sim.getRobotAPI().pickupItem();
            sim.getRobotAPI().playSound("hello");
        }
    }
}
