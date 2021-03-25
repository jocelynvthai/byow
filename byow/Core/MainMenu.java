package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class MainMenu {
    private int width;
    private int height;
    private long seed;
    TETile avatarTile = Tileset.AVATAR;
    TETile worldTile = Tileset.FLOOR;
    String userInput;
    Integer chosenMenuOption;
    String chooseTileString = "";

    boolean changedTile = false;

    public MainMenu(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        userInput = "";
    }

    // construct the main menu options and draw onto world
    public void initializeMainMenu() {
        this.drawTitle("CS61B: THE GAME");
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
        StdDraw.text(this.width / 2, this.height / 2 + 1, "New Game (N)");
        StdDraw.text(this.width / 2, this.height / 2 - 2, "Load Game (L)");
        StdDraw.text(this.width / 2, this.height / 2 - 5, "Quit (Q)");
        StdDraw.text(this.width / 2, this.height / 2 - 8, "Change Avatar and World Tile (T)");
        StdDraw.show();
    }

    // get user input to create the world
    public boolean listenMenuOption() {
        if (StdDraw.hasNextKeyTyped()) {
            String input = Character.toString(StdDraw.nextKeyTyped());
            if (input.equals("N") || input.equals("n")) {
                chosenMenuOption = 1;
                userInput += "N";
                promptRandomSeed();
                return true;
            } else if (input.equals("L") || input.equals("l")) {
                chosenMenuOption = 2;
                SavedFile savedFile = new SavedFile();
                userInput = savedFile.readFromFile();
                return true;
            } else if (input.equals("Q") || input.equals("q")) {
                System.exit(0);
            } else if (input.equals("T") || input.equals("t")) {
                userInput += "T";
                chooseTileString += "T";
                chooseAvatar();
                chooseWorld();
                changedTile = true;
                initializeMainMenu();
                listenMenuOption();
            }
        }
        return false;
    }

    // have the user choose a seed to create the world
    public void promptRandomSeed() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 37));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.width / 2, this.height / 2 + 2,
                "Please enter a positive integer followed by S");
        StdDraw.show();

        Boolean hasFinishedInputingSeed = false;
        String seedInput = "";
        while (!hasFinishedInputingSeed) {
            if (StdDraw.hasNextKeyTyped()) {
                String nextChar = Character.toString(StdDraw.nextKeyTyped());
                if (nextChar.equals("S") || nextChar.equals("s")) {
                    seed = Long.valueOf(seedInput);
                    hasFinishedInputingSeed = true;
                }
                seedInput += nextChar;
                userInput += nextChar;
                StdDraw.clear(Color.BLACK);
                StdDraw.text(this.width / 2, this.height / 2 + 2,
                        "Please enter a positive integer followed by S");
                StdDraw.text(this.width / 2, this.height / 2 - 3, seedInput);
                StdDraw.show();
            }
        }
    }

    // have the user choose an avatar tile
    public void chooseAvatar() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 45));
        StdDraw.setPenColor(Color.white);
        StdDraw.text(this.width / 2, this.height * 0.75, "Choose Your Avatar");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(this.width / 2, this.height * 0.75 - 4, "Please enter the digit "
                + "pertaining to desired avatar:");
        StdDraw.text(this.width / 2, this.height / 2,  " @ (0 - 2)");
        StdDraw.text(this.width / 2, this.height / 2 - 3, "Tree (3 - 5)");
        StdDraw.text(this.width / 2, this.height / 2 - 6, "Mountain (6 - 7)");
        StdDraw.text(this.width / 2, this.height / 2 - 9, "Flower (8 - 9)");
        StdDraw.show();

        Boolean hasInputAvatar = false;
        while (!hasInputAvatar) {
            if (StdDraw.hasNextKeyTyped()) {
                String nextChar = Character.toString(StdDraw.nextKeyTyped());
                if (nextChar.equals("0") || nextChar.equals("1") || nextChar.equals("2")) {
                    chooseTileString += nextChar;
                    avatarTile = Tileset.AVATAR;
                    hasInputAvatar = true;
                } else if (nextChar.equals("3") || nextChar.equals("4") || nextChar.equals("5")) {
                    chooseTileString += nextChar;
                    avatarTile = Tileset.TREE;
                    hasInputAvatar = true;
                } else if (nextChar.equals("6") || nextChar.equals("7")) {
                    chooseTileString += nextChar;
                    avatarTile = Tileset.MOUNTAIN;
                    hasInputAvatar = true;
                } else if (nextChar.equals("8") || nextChar.equals("9")) {
                    chooseTileString += nextChar;
                    avatarTile = Tileset.FLOWER;
                    hasInputAvatar = true;
                }
                userInput += nextChar;
                StdDraw.clear(Color.BLACK);
                StdDraw.show();
            }
        }
    }

    // have the user choose a world tile
    public void chooseWorld() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 45));
        StdDraw.setPenColor(Color.white);
        StdDraw.text(this.width / 2, this.height * 0.75, "Choose Your World");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(this.width / 2, this.height * 0.75 - 4, "Please enter the digit "
                + "pertaining to desired world:");
        StdDraw.text(this.width / 2, this.height / 2,  "Floor (0 - 2)");
        StdDraw.text(this.width / 2, this.height / 2 - 3, "Sand (3 - 5)");
        StdDraw.text(this.width / 2, this.height / 2 - 6, "Water (6 - 7)");
        StdDraw.text(this.width / 2, this.height / 2 - 9, "Grass (8 - 9)");
        StdDraw.show();

        Boolean hasInputWorld = false;
        while (!hasInputWorld) {
            if (StdDraw.hasNextKeyTyped()) {
                String nextChar = Character.toString(StdDraw.nextKeyTyped());
                if (nextChar.equals("0") || nextChar.equals("1") || nextChar.equals("2")) {
                    chooseTileString += nextChar;
                    worldTile = Tileset.FLOOR;
                    hasInputWorld = true;
                } else if (nextChar.equals("3") || nextChar.equals("4") || nextChar.equals("5")) {
                    chooseTileString += nextChar;
                    worldTile = Tileset.SAND;
                    hasInputWorld = true;
                } else if (nextChar.equals("6") || nextChar.equals("7")) {
                    chooseTileString += nextChar;
                    worldTile = Tileset.WATER;
                    hasInputWorld = true;
                } else if (nextChar.equals("8") || nextChar.equals("9")) {
                    chooseTileString += nextChar;
                    worldTile = Tileset.GRASS;
                    hasInputWorld = true;
                }
                userInput += nextChar;
                StdDraw.clear(Color.BLACK);
                StdDraw.show();
            }
        }
    }

    // return the seed
    public long getSeed() {
        return seed;
    }

    // return the avatar tile
    public TETile getAvatarTile() {
        return avatarTile;
    }

    // return the world tile
    public TETile getWorldTile() {
        return worldTile;
    }

    // return the user input in format "N****S"
    public String getUserInput() {
        return userInput;
    }

    // return the chosen menu option, N or L
    public int getChosenMenuOption() {
        return chosenMenuOption;
    }

    // return whether a tile was changed before loading saved game
    public boolean isChangedTile() {
        return changedTile;
    }

    // return string of choosing tile
    public String getChooseTileString() {
        return chooseTileString;
    }
}
