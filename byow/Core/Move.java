package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Move {

    Position start;
    TETile[][] world;
    TETile avatarTile;
    TETile worldTile;

    Position newPosition;
    Position lastPosition;
    Position currentPosition;

    String userInput;

    public Move(Position start, TETile[][] world, TETile avatarTile, TETile worldTile) {
        this.start = start;
        this.world = world;
        this.avatarTile = avatarTile;
        this.worldTile = worldTile;

        lastPosition = start;
        currentPosition = start;
        userInput = "";
    }

    public void makeMove(Character move) {
        if (move == 'W' || move == 'w') { //up
            newPosition = new Position(currentPosition.getX(), currentPosition.getY() + 1);
        }
        if (move == 'A' || move == 'a') { //left
            newPosition = new Position(currentPosition.getX() - 1, currentPosition.getY());
        }
        if (move == 'S' || move == 's') { //down
            newPosition = new Position(currentPosition.getX(), currentPosition.getY() - 1);
        }
        if (move == 'D' || move == 'd') { //right
            newPosition = new Position(currentPosition.getX() + 1, currentPosition.getY());
        }

        userInput += move;

        if (world[newPosition.getX()][newPosition.getY()] == worldTile
                || world[newPosition.getX()][newPosition.getY()] == Tileset.LOCKED_DOOR) {
            lastPosition = currentPosition;
            currentPosition = newPosition;
        }
        updateWorld();
    }

    public void updateWorld() {
        world[lastPosition.getX()][lastPosition.getY()] = worldTile;
        world[currentPosition.getX()][currentPosition.getY()] = avatarTile;
    }

    public String getUserInput() {
        return userInput;
    }

    // return current position of avatar
    public Position getCurrentPosition() {
        return currentPosition;
    }
}
