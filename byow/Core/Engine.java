package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import edu.princeton.cs.introcs.StdDraw;

public class Engine {

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 48;
    TETile[][] world = new TETile[WIDTH][HEIGHT];
    Random r;

    List<Room> rooms = new ArrayList<>();
    List<Hallway> hallways = new ArrayList<>();
    Boolean enteredMenuOption = false;
    Boolean changedTile = false;

    Move avatar;
    Font originalFont;

    Position end;
    Position door;
    Position startPosition;

    TETile avatarTile = Tileset.AVATAR;
    TETile worldTile = Tileset.FLOOR;

    String userInput;
    String chooseTileString = "";

    long seed = 0;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

        ter.initialize(WIDTH, HEIGHT);
        originalFont = StdDraw.getFont();

        // create main menu
        MainMenu mainMenu = new MainMenu(WIDTH, HEIGHT);
        mainMenu.initializeMainMenu();

        // get seed, avatar tile, and world tile
        while (!enteredMenuOption) {
            enteredMenuOption = mainMenu.listenMenuOption();
        }

        // if loading new world (N) or loading saved world (L)
        userInput = mainMenu.getUserInput();
        if (mainMenu.getChosenMenuOption() == 1) {
            avatarTile = mainMenu.getAvatarTile();
            worldTile = mainMenu.getWorldTile();
        } else if (mainMenu.getChosenMenuOption() == 2) {
            changedTile = mainMenu.isChangedTile();
            if (changedTile) {
                avatarTile = mainMenu.getAvatarTile();
                worldTile = mainMenu.getWorldTile();
                chooseTileString = mainMenu.getChooseTileString();
            }
            userInput = chooseTileString + userInput;
        }
        interactWithInputString(userInput);
        StdDraw.setFont(originalFont);
        ter.renderFrame(world);

        // while inside the world
        liveGamePlay();
    }

    private void liveGamePlay() {
        TETile[][] originalWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                originalWorld[x][y] = world[x][y];
            }
        }
        originalWorld[startPosition.getX()][startPosition.getY()] = worldTile;
        originalWorld[avatar.getCurrentPosition().getX()][avatar.getCurrentPosition().getY()]
                = worldTile;
        LineOfSight lineOfSight = new LineOfSight(originalWorld, avatarTile, worldTile);
        //set up heads up display
        HeadsUpDisplay hud = new HeadsUpDisplay(world, WIDTH, HEIGHT, ter);
        boolean hasEnteredExitCommand = false;
        boolean colonEntered = false;
        double xMousePos = StdDraw.mouseX();
        double yMousePos = StdDraw.mouseY();
        boolean gameOver = false;

        //live game play loop
        while (!hasEnteredExitCommand && !gameOver) {
            world = lineOfSight.display(avatar.getCurrentPosition());
            // change current mouse position if mouse position has changed
            double currentXMousePos = StdDraw.mouseX();
            double currentYMousePos = StdDraw.mouseY();
            if (currentXMousePos >= 0 && currentXMousePos < WIDTH
                    && currentYMousePos >= 0 && currentYMousePos < HEIGHT) {
                if (currentXMousePos != xMousePos || currentYMousePos != yMousePos) {
                    xMousePos = currentXMousePos;
                    yMousePos = currentYMousePos;
                    hud.displayTileName();
                }
            }

            if (avatar.getCurrentPosition().equals(end)) {
                GameOver gameOverObject = new GameOver(WIDTH, HEIGHT);
                gameOver = true;
                gameOverObject.initialize();
                boolean hasEnteredGameOverOption = false;
                while (!hasEnteredGameOverOption) {
                    hasEnteredGameOverOption = gameOverObject.listenOption();
                }
            }

            //read and respond to keyboard commmands
            if (StdDraw.hasNextKeyTyped()) {
                String nextChar = Character.toString(StdDraw.nextKeyTyped());
                if (colonEntered) {
                    if (nextChar.equals("Q") || nextChar.equals("q")) {
                        //save and quit
                        userInput = getUserInput();
                        SavedFile savedFile = new SavedFile();
                        savedFile.writeToFile(userInput);
                        hasEnteredExitCommand = true;
                        System.exit(0);
                    }
                }
                if (nextChar.equals(":")) {
                    colonEntered = true;
                } else {
                    //move avatar
                    colonEntered = false;
                    avatar.makeMove(nextChar.charAt(0));
                    world = lineOfSight.display(avatar.getCurrentPosition());
                    ter.renderFrame(world);
                }
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        String moves;
        if (Character.toString(input.charAt(0)).equals("L")
            || Character.toString(input.charAt(0)).equals("l")) {
            SavedFile sf = new SavedFile();
            String oldInput = sf.readFromFile();
            getWorld();
            moves = load(oldInput + input);
            getStartPosition();
        } else {
            moves = load(input);
            r = new Random(seed);
            setRooms();
            setHallways();
            setDoor();
            setEnd();
            setWorld();
            saveStartPosition();
        }

        avatar = new Move(startPosition, world, avatarTile, worldTile);
        if (!moves.isEmpty()) {
            for (int i = 0; i < moves.length(); i++) {
                avatar.makeMove(moves.charAt(i));
            }
        }
        return world;
    }

    // load the new world
    private String load(String input) {
        String moves = "";
        seed = 0;
        Load load = new Load();
        Boolean finishedSeed = false;
        boolean changeTile = false;
        Boolean colonInserted = false;
        int tileLoad = 0;

        for (int i = 0; i < input.length(); i++) {
            if (changeTile) {
                if (changedTile) {
                    if (tileLoad == 1) {
                        tileLoad += 1;
                    } else if (tileLoad == 2) {
                        changeTile = false;
                        tileLoad = 0;
                    }
                } else {
                    if (tileLoad == 1) {
                        load.loadAvatar(input.charAt(i));
                        avatarTile = load.getAvatarTile();
                        tileLoad += 1;
                    } else if (tileLoad == 2) {
                        load.loadWorld(input.charAt(i));
                        worldTile = load.getWorldTile();
                        changeTile = false;
                        changedTile = true;
                        tileLoad = 0;
                    }
                }
                continue;
            }

            if (Character.toString(input.charAt(i)).equals("Q")
                    || Character.toString(input.charAt(i)).equals("q")) {
                if (colonInserted && i == input.length() - 1) {
                    colonInserted = false;
                    SavedFile sf = new SavedFile();
                    sf.writeToFile(input);
                }
                continue;
            }

            if (Character.toString(input.charAt(i)).equals("N")
                    || Character.toString(input.charAt(i)).equals("n")) {
                continue;

            } else if ((Character.toString(input.charAt(i)).equals("S")
                    || Character.toString(input.charAt(i)).equals("s")) && !finishedSeed) {
                finishedSeed = true;
            } else if (Character.toString(input.charAt(i)).equals("T")
                    || Character.toString(input.charAt(i)).equals("t")) {
                changeTile = true;
                tileLoad = 1;
            } else if (Character.toString(input.charAt(i)).equals(":")) {
                colonInserted = true;
            }  else if ((Character.toString(input.charAt(i)).equals("L")
                    || Character.toString(input.charAt(i)).equals("l"))) {
                continue;
            } else if (Character.isDigit(input.charAt(i))) {
                seed = seed * 10 + (input.charAt(i) - '0');
            } else {
                moves += input.charAt(i);
            }
        }
        return moves;
    }

    // add Rooms
    private void setRooms() {
        int roomNum = r.nextInt(15) + 4;

        while (roomNum != 0) {
            Room room = new Room(roomNum, r, worldTile, world);
            room.buildRoom();
            if (room.getWidth() != 0 && room.getHeight() != 0 && !room.overlapping()) {
                room.addWallsToWorld();
                room.addToWorld();
                roomNum -= 1;
                rooms.add(room);
            }
        }
    }

    // add hallways
    private void setHallways() {
        List<Room> roomsToConnect = new ArrayList<>();
        for (Room room : rooms) {
            roomsToConnect.add(room);
        }

        int numHallways = r.nextInt(5) + roomsToConnect.size();
        Room lastRoom = roomsToConnect.remove(r.nextInt(roomsToConnect.size() - 1));

        while (numHallways != 0) {
            Room room = rooms.get(r.nextInt(rooms.size() - 1));
            if (roomsToConnect.size() != 0) {
                if (roomsToConnect.size() == 1) {
                    room = roomsToConnect.remove(0);
                } else {
                    room = roomsToConnect.remove(r.nextInt(roomsToConnect.size() - 1));
                }
            }

            Hallway hallway = new Hallway(lastRoom.getMiddle(), room.getMiddle(),
                    worldTile, world);

            hallway.buildHallway();
            hallway.addToWorld();
            hallway.addWallsToWorld();
            hallways.add(hallway);
            lastRoom = room;
            numHallways -= 1;
        }
    }

    // add Door and avatar at door
    private void setDoor() {
        Door doorClass = new Door(rooms, hallways, r, avatarTile, worldTile, world);
        doorClass.addDoorToWorld();
        door = doorClass.getDoor();
        startPosition = doorClass.getPositionAtDoor();
        doorClass.addAvatar();
    }

    // set the locked door that ends the game
    private void setEnd() {
        Room endRoom = rooms.get(r.nextInt(rooms.size() - 1));
        end = endRoom.getMiddle();
        world[end.getX()][end.getY()] = Tileset.LOCKED_DOOR;
    }


    // set the saved world
    private void setWorld() {
        // 0 = nothing
        // 1 = wall
        // 2 = unlocked door
        // 3 = locked door
        // 4 = worldTile
        // 5 = avatarTile
        String input = "";
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                String key = "";
                if (world[x][y] == Tileset.NOTHING) {
                    key = "n";
                } else if (world[x][y] == Tileset.WALL) {
                    key = "w";
                } else if (world[x][y] == Tileset.UNLOCKED_DOOR) {
                    key = "u";
                } else if (world[x][y] == Tileset.LOCKED_DOOR) {
                    key = "l";
                } else if (world[x][y] == worldTile) {
                    key = "f";
                } else if (world[x][y] == avatarTile) {
                    key = "a";
                }
                input += key;
            }
        }
        SavedWorld sw = new SavedWorld();
        sw.writeToFile(input);
    }

    // get the saved world
    private void getWorld() {
        // 0 = nothing
        // 1 = wall
        // 2 = unlocked door
        // 3 = locked door
        // 4 = worldTile
        // 5 = avatarTile
        SavedWorld sw = new SavedWorld();
        String input = sw.readFromFile();
        int x = 0;
        int y = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.toString(input.charAt(i)).equals("n")) {
                world[x][y] = Tileset.NOTHING;
            } else if (Character.toString(input.charAt(i)).equals("w")) {
                world[x][y] = Tileset.WALL;
            } else if (Character.toString(input.charAt(i)).equals("u")) {
                world[x][y] = Tileset.UNLOCKED_DOOR;
            } else if (Character.toString(input.charAt(i)).equals("l")) {
                world[x][y] = Tileset.LOCKED_DOOR;
            } else if (Character.toString(input.charAt(i)).equals("f")) {
                world[x][y] = worldTile;
            } else if (Character.toString(input.charAt(i)).equals("a")) {
                world[x][y] = avatarTile;
            }
            if (y == HEIGHT - 1) {
                y = 0;
                x += 1;
            } else {
                y += 1;
            }
        }
    }

    // save the start position in text file
    private void saveStartPosition() {
        SavedStartPosition ssp = new SavedStartPosition();
        ssp.writeToFile("x" + startPosition.getX() + "y" + startPosition.getY());
    }

    // instantiate startPosition
    private void getStartPosition() {
        SavedStartPosition ssp = new SavedStartPosition();
        String spString = ssp.readFromFile();
        int x = 0;
        int y = 0;
        boolean xAccess = false;
        boolean yAccess = false;
        for (int i = 0; i < spString.length(); i++) {
            if (Character.toString(spString.charAt(i)).equals("x")) {
                xAccess = true;
            } else if (Character.toString(spString.charAt(i)).equals("y")) {
                xAccess = false;
                yAccess = true;
            }
            if (xAccess && Character.isDigit(spString.charAt(i))) {
                x = x * 10 + (spString.charAt(i) - '0');
            } else if (yAccess && Character.isDigit(spString.charAt(i))) {
                y = y * 10 + (spString.charAt(i) - '0');
            }
        }
        startPosition = new Position(x, y);
    }

    private String getUserInput() {
        String newUserInput = "";
        for (int i = 0; i < userInput.length(); i++) {
            if (Character.toString(userInput.charAt(i)).equals("s")) {
                newUserInput += Character.toString(userInput.charAt(i));
                break;
            } else {
                newUserInput += Character.toString(userInput.charAt(i));
            }
        }
        newUserInput += avatar.getUserInput();
        return newUserInput;
    }
}
