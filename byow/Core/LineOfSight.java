package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class LineOfSight {

    TETile[][] originalWorld;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 48;
    TETile avatarTile;
    TETile worldTile;

    public LineOfSight(TETile[][] originalWorld, TETile avatarTile, TETile worldTile) {
        this.originalWorld = originalWorld;
        this.avatarTile = avatarTile;
        this.worldTile = worldTile;
    }

    public TETile[][] display(Position avatarPosition) {
        int startX = avatarPosition.getX() - 4;
        int startY = avatarPosition.getY() - 4;

        Position ll = new Position(startX, startY);
        Position ul = new Position(startX, startY + 8);
        Position lr = new Position(startX + 8, startY);
        Position ur = new Position(startX + 8, startY + 8);

        TETile[][] newWorld = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                newWorld[x][y] = Tileset.NOTHING;
            }
        }

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Position p = new Position(startX + x, startY + y);
                if (!outOfBounds(startX + x, startY + y)) {
                    if (!p.equals(avatarPosition) && !p.equals(ll) && !p.equals(ul)
                            && !p.equals(lr) && !p.equals(ur)) {
                        newWorld[startX + x][startY + y] = originalWorld[startX + x][startY + y];
                    }
                }
            }
        }
        newWorld[avatarPosition.getX()][avatarPosition.getY()] = avatarTile;

        return newWorld;
    }

    private boolean outOfBounds(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return true;
        }
        return false;
    }
}
