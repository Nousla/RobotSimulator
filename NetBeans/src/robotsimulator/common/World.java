package robotsimulator.common;

public class World {
    private Tile[][] tiles;

    private Source<Tile[][]> tilesSource;

    public World() {
        tilesSource = new Source<>();
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
        tilesSource.update(tiles);
    }
    
    public Tile getTile(int x, int y) {
        if(x < 0 || x >= tiles.length || y < 0 || y >= tiles[x].length) {
            return null;
        }
        
        return tiles[x][y];
    }

    public Source<Tile[][]> getTilesSource() {
        return tilesSource;
    }
}
