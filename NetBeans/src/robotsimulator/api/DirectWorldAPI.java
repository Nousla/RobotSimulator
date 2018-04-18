package robotsimulator.api;

import robotsimulator.common.Tile;
import robotsimulator.common.World;

public class DirectWorldAPI implements WorldAPI{
    
    private final World world;
    
    public DirectWorldAPI(World world) {
        this.world = world;
    }
    
    @Override
    public Tile[][] getTiles() {
        return world.getTiles();
    }

    @Override
    public void setTiles(Tile[][] tiles) {
        world.setTiles(tiles);
    }
}
