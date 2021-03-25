package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class HeadsUpDisplay {

    TETile[][] world;
    private int width;
    private int height;
    TERenderer ter;

    TETile tile;
    String name = "";

    public HeadsUpDisplay(TETile[][] world, int width, int height, TERenderer ter) {
        this.world = world;
        this.width = width;
        this.height = height;
        this.ter = ter;
    }

    public void displayTileName() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        tile = world[x][y];

        if (tile == Tileset.NOTHING) {
            name = "Empty Space";
        } else if (tile == Tileset.LOCKED_DOOR) {
            name = "Locked Door";
        } else if (tile == Tileset.WALL) {
            name = "Wall";
        } else if (tile == Tileset.AVATAR) {
            name = "Avatar";
        } else if (tile == Tileset.TREE) {
            name = "Tree";
        } else if (tile == Tileset.MOUNTAIN) {
            name = "Mountain";
        } else if (tile == Tileset.FLOWER) {
            name = "Flower";
        } else if (tile == Tileset.FLOOR) {
            name = "Floor";
        } else if (tile == Tileset.SAND) {
            name = "Sand";
        } else if (tile == Tileset.WATER) {
            name = "Water";
        } else if (tile == Tileset.GRASS) {
            name = "Grass";
        }
        showOnWorld();
    }

    public void showOnWorld() {
        ter.renderFrame(world);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width - 3, height - 1, name);
        StdDraw.show();
    }

    public void lost() {
        StdDraw.clear(Color.BLACK);
        StdDraw.text(10, this.height - 2,
                "The prisoner caught you!");
        StdDraw.show();
    }
}
