package byow.Core;

public class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // return the x position
    public int getX() {
        return x;
    }

    // return the y position
    public int getY() {
        return y;
    }

    public boolean equals(Position p) {
        if (this.getX() == p.getX() && this.getY() == p.getY()) {
            return true;
        }
        return false;
    }
}

