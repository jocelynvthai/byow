package byow.lab12;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.LinkedList;

public class TesseLation {

    private int tesselationSide;
    private int hexSide;

    public TesseLation(int tesselationSide, int hexSide) {
        this.tesselationSide = tesselationSide;
        this.hexSide = hexSide;
    }

    //create a list of all hexagons that comprise this tesselation object
    public List<Hexagon> getHexagons() {
        LinkedList<Hexagon> hexagons = new LinkedList<>();

        // x distance between two adjacent hexagons
        int xSeparation = hexSide * 2 - 1;

        // distance between two hexagons that are symmetric about middle
        int xOffset = xSeparation * (tesselationSide * 2 - 2);

        for (int i = 0; i < tesselationSide; i++) {
            // find x and y for the bottom left corner
            int x1 = i * xSeparation * hexSide;
            int x2 = x1 + xOffset;

            int y = (tesselationSide - i - 1) * hexSide + 2 * hexSide;
            int numHexagons = tesselationSide + i;

            addColumn(hexagons, new Position(x1, y), numHexagons);
            addColumn(hexagons, new Position(x2, y), numHexagons);

            xOffset -= 2 * xSeparation;
        }
        return hexagons;
    }

    private void addColumn(LinkedList<Hexagon> hexagons, Position position, int numHexagons) {
        for (int i = 0; i < numHexagons; i++) {
            int x = position.getX();
            int y = position.getY() + hexSide * 2 + i;
            hexagons.add(new Hexagon(hexSide, new Position(x, y), Tileset.AVATAR));
        }
    }
}
