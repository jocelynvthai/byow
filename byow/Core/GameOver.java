package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class GameOver {
    private int width;
    private int height;

    public GameOver(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    // construct the main menu options and draw onto world
    public void initialize() {
        this.drawTitle("CONGRATS, you won!");
        this.drawMenuOptions();
    }

    public void drawTitle(String title) {
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 55));
        StdDraw.setPenColor(Color.white);
        StdDraw.text(this.width / 2, this.height * 0.75 - 3, title);
        StdDraw.show();
    }

    public void drawMenuOptions() {
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.setPenColor(Color.white);
        StdDraw.text(this.width / 2, this.height / 2 + 1, "Main Menu (M)");
        StdDraw.text(this.width / 2, this.height / 2 - 5, "Quit (Q)");
        StdDraw.show();
    }

    public boolean listenOption() {
        if (StdDraw.hasNextKeyTyped()) {
            String input = Character.toString(StdDraw.nextKeyTyped());
            if (input.equals("M") || input.equals("m")) {
                Engine e = new Engine();
                e.interactWithKeyboard();
                return true;
            } else if (input.equals("Q") || input.equals("q")) {
                System.exit(0);
                return true;
            }
        }
        return false;
    }
}
