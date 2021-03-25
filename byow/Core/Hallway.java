package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.ArrayList;

public class Hallway {

    Position start;
    Position end;
    TETile tile;
    TETile[][] world;
    List<Position> positions;

    public Hallway(Position start, Position end, TETile tile, TETile[][] world) {
        this.start = start;
        this.end = end;
        this.tile = tile;
        this.world = world;
        positions = new ArrayList();
    }

    // build the hallway before adding to the world
    public void buildHallway() {
        buildY();
        buildX();
    }

    // move the position to the left or right
    private void buildX() {
        if (start.getX() < end.getX()) {
            int numSpotsToFill = end.getX() - start.getX();
            buildHallwayToRight(numSpotsToFill);
        } else if (start.getX() > end.getX()) {
            int numSpotsToFill = start.getX() - end.getX();
            buildHallwayToLeft(numSpotsToFill);
        }
    }

    // move the position up or down
    private void buildY() {
        if (start.getY() < end.getY()) {
            int numSpotsToFill = end.getY() - start.getY();
            buildHallwayUp(numSpotsToFill);
        } else if (start.getY() > end.getY()) {
            int numSpotsToFill = start.getY() - end.getY();
            buildHallwayDown(numSpotsToFill);
        }
    }

    // move the position right however many spots to fill
    private void buildHallwayToRight(int numSpotsToFIll) {
        for (int i = 1; i <= numSpotsToFIll; i++) {
            positions.add(new Position(start.getX() + i, start.getY()));
        }
        start.x = start.getX() + numSpotsToFIll;
    }

    // move the position left however many spots to fill
    private void buildHallwayToLeft(int numSpotsToFIll) {
        for (int i = 1; i <= numSpotsToFIll; i++) {
            positions.add(new Position(start.getX() - i, start.getY()));
        }
        start.x = start.getX() - numSpotsToFIll;
    }

    // move the position up however many spots to fill
    private void buildHallwayUp(int numSpotsToFIll) {
        for (int i = 1; i <= numSpotsToFIll; i++) {
            positions.add(new Position(start.getX(), start.getY() + i));
        }
        start.y = start.getY() + numSpotsToFIll;
    }

    // move the position down however many spots to fill
    private void buildHallwayDown(int numSpotsToFIll) {
        for (int i = 1; i <= numSpotsToFIll; i++) {
            positions.add(new Position(start.getX(), start.getY() - i));
        }
        start.y = start.getY() - numSpotsToFIll;
    }

    // add all hallway positions that are either blank or a wall (interesting) to the world
    public void addToWorld() {
        for (Position p : positions) {
            if (world[p.getX()][p.getY()] == Tileset.NOTHING
                    || world[p.getX()][p.getY()] == Tileset.WALL) {
                world[p.getX()][p.getY()] = tile;
            }
        }
    }

    // add the hallway walls to the world
    public void addWallsToWorld() {
        for (Position p : positions) {
            if (world[p.getX()][p.getY()] == tile) {
                if (world[p.getX() - 1][p.getY()] == Tileset.NOTHING) { //left
                    world[p.getX() - 1][p.getY()] = Tileset.WALL;
                }
                if (world[p.getX() + 1][p.getY()] == Tileset.NOTHING) { //right
                    world[p.getX() + 1][p.getY()] = Tileset.WALL;
                }
                if (world[p.getX()][p.getY() - 1] == Tileset.NOTHING) { //down
                    world[p.getX()][p.getY() - 1] = Tileset.WALL;
                }
                if (world[p.getX()][p.getY() + 1] == Tileset.NOTHING) { //up
                    world[p.getX()][p.getY() + 1] = Tileset.WALL;
                }

                // corners
                if (world[p.getX() + 1][p.getY() + 1] == Tileset.NOTHING) {
                    world[p.getX() + 1][p.getY() + 1] = Tileset.WALL;
                }
                if (world[p.getX() - 1][p.getY() + 1] == Tileset.NOTHING) {
                    world[p.getX() - 1][p.getY() + 1] = Tileset.WALL;
                }
                if (world[p.getX() + 1][p.getY() - 1] == Tileset.NOTHING) {
                    world[p.getX() + 1][p.getY() - 1] = Tileset.WALL;
                }
                if (world[p.getX() - 1][p.getY() - 1] == Tileset.NOTHING) {
                    world[p.getX() - 1][p.getY() - 1] = Tileset.WALL;
                }
            }
        }
    }
}
