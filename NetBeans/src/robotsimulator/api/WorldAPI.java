package robotsimulator.api;

import robotsimulator.common.Tile;

public interface WorldAPI {
    public Tile[][] getTiles();

    public void setTiles(Tile[][] tiles);
}
