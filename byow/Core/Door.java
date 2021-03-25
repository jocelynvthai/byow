package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

public class Door {
    List<Room> rooms;
    List<Hallway> hallways;
    TETile avatarTile;
    TETile worldTile;
    TETile[][] world;
    Random r;
    Position door;
    Position positionAtDoor;

    public Door(List<Room> rooms, List<Hallway> hallways, Random r, TETile avatarTile,
                TETile worldTile, TETile[][] world) {
        this.rooms = rooms;
        this.hallways = hallways;
        this.avatarTile = avatarTile;
        this.worldTile = worldTile;
        this.world = world;
        this.r = r;
        createDoor();
    }

    public void createDoor() {
        Boolean validDoor = false;

        while (!validDoor) {
            Room doorRoom = rooms.get(r.nextInt(rooms.size() - 1));
            List<Position> wallPositions = doorRoom.getWallPositions();
            door = wallPositions.get(r.nextInt(wallPositions.size() - 1));
            Position left = new Position(door.getX() - 1, door.getY());
            Position right = new Position(door.getX() + 1, door.getY());
            Position up = new Position(door.getX(), door.getY() + 1);
            Position down = new Position(door.getX(), door.getY() - 1);

            if (world[left.getX()][left.getY()] == Tileset.NOTHING
                    || world[right.getX()][right.getY()] == Tileset.NOTHING
                    || world[up.getX()][up.getY()] == Tileset.NOTHING
                    || world[down.getX()][down.getY()] == Tileset.NOTHING) {
                if (world[left.getX()][left.getY()] == worldTile
                        || world[right.getX()][right.getY()] == worldTile
                        || world[up.getX()][up.getY()] == worldTile
                        || world[down.getX()][down.getY()] == worldTile) {
                    validDoor = true;
                }
            }
        }
    }

    public Position getDoor() {
        return door;
    }

    public void addDoorToWorld() {
        world[door.getX()][door.getY()] = Tileset.UNLOCKED_DOOR;
    }

    public Position getPositionAtDoor() {
        Position left = new Position(door.getX() - 1, door.getY());
        Position right = new Position(door.getX() + 1, door.getY());
        Position up = new Position(door.getX(), door.getY() + 1);
        Position down = new Position(door.getX(), door.getY() - 1);

        if (world[left.getX()][left.getY()] == worldTile) {
            positionAtDoor = left;
        } else if (world[right.getX()][right.getY()] == worldTile) {
            positionAtDoor = right;
        } else if (world[up.getX()][up.getY()] == worldTile) {
            positionAtDoor = up;
        } else {
            positionAtDoor = down;
        }
        return positionAtDoor;
    }

    public void addAvatar() {
        world[positionAtDoor.getX()][positionAtDoor.getY()] = avatarTile;
    }
}
