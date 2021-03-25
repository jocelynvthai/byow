package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Room {

    int width;
    int height;
    Position start;
    Position middle;
    int roomNum;
    Random r;
    TETile tile;
    TETile[][] world;

    List<Position> positions;
    List<Position> wallPositions;

    public Room(int roomNum, Random r, TETile tile, TETile[][] world) {
        width = r.nextInt(world.length / 5);
        height = r.nextInt(world[0].length / 3);
        start = new Position(r.nextInt(world.length - width), r.nextInt(world[0].length - height));
        middle = new Position(start.getX() + (width / 2), start.getY() + (height / 2));

        this.roomNum = roomNum;
        this.r = r;
        this.tile = tile;
        this.world = world;

        positions = new ArrayList<>();
        wallPositions = new ArrayList<>();
    }

    // return the width of the room
    public int getWidth() {
        return width;
    }

    // return the height of the room
    public int getHeight() {
        return height;
    }

    // return the middle position of the room
    public Position getMiddle() {
        return middle;
    }

    // return the middle position of the room
    public List<Position> getPositions() {
        return positions;
    }

    // return the middle position of the room
    public List<Position> getWallPositions() {
        return wallPositions;
    }

    // build the room before adding to the world
    public void buildRoom() {
        int startX = start.getX();
        int startY = start.getY();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                positions.add(new Position(startX + x, startY + y));
            }
        }
        buildWalls();
    }

    // build the room walls before adding to the world
    public void buildWalls() {
        int lowerLeftX = start.getX() - 1;
        int lowerLeftY = start.getY() - 1;
        for (int x = 0; x < width + 2; x++) {
            for (int y = 0; y < height + 2; y++) {
                wallPositions.add(new Position(lowerLeftX + x, lowerLeftY + y));
            }
        }
    }

    // check if any rooms/walls are overlapping with another room/wall
    public boolean overlapping() {
        for (Position p : wallPositions) {
            if (isEdge(p)) {
                return true;
            }
            if (world[p.getX()][p.getY()] != Tileset.NOTHING) {
                return true;
            }
            if (p.getX() == 0 || p.getX() == world.length - 1 || p.getY() == 0
                    || p.getY() == world[0].length - 1) {
                return true;
            }
        }
        return false;
    }

    // add the room to the world
    public void addToWorld() {
        for (Position p : positions) {
            world[p.getX()][p.getY()] = tile;
        }
    }

    // add the room walls to the world
    public void addWallsToWorld() {
        for (Position p : wallPositions) {
            world[p.getX()][p.getY()] = Tileset.WALL;
        }
    }

    // return whether the position is on the edge of the world
    public boolean isEdge(Position p) {
        if (p.getX() <= 1 || p.getX() >= world.length - 2
                || p.getY() <= 1 || p.getY() >= world[0].length - 2) {
            return true;
        }
        return false;
    }
}
