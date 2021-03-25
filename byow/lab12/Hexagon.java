package byow.lab12;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class Hexagon {

    private int side;
    private Position upperLeft;
    private TETile tile;

    private static final Random RANDOM = new Random(100);
    private static final TETile[] allTiles = {
            Tileset.FLOWER, Tileset.AVATAR, Tileset.LOCKED_DOOR,
            Tileset.MOUNTAIN, Tileset.LOCKED_DOOR, Tileset.TREE
    };

    public Hexagon(int side, Position upperLeft, TETile tile) {
        this.side = side;
        this.upperLeft = upperLeft;
        this.tile =tile;

    }

    public Hexagon(int side, Position upperLeft) {
        this(side, upperLeft, randomTile());
    }

    private static TETile randomTile() {
        return allTiles[RANDOM.nextInt((allTiles.length))];
    }

    public TETile getTile() {
        return tile;
    }

    // return a list of the x, y positions that this hexagon covers
    public List<Position> getCoordinates() {
        List<Position> list = new LinkedList<>();

        // add the upper half of the hexagon
        for (int i = 0; i < side; i++) {
            int x = upperLeft.getX() - i;
            int y = upperLeft.getY() - i;
            addRow(list, new Position(x, y), side + i * 2);
        }

        // add the lower half of the hexagon
        for (int i = 0; i < side; i++) {
            int x = upperLeft.getX() - (side - 1 - i);
            int y = upperLeft.getY() - side - i;
            addRow(list, new Position(x, y), side + (side - 1 - i) * 2);
        }

        return list;
    }

    private void addRow(List<Position> list, Position startPosition, int length) {
        for (int offset = 0; offset < length; offset++) {
            int y = startPosition.getY();
            int x = startPosition.getX() + offset;
            list.add(new Position(x, y));
        }
    }
}
