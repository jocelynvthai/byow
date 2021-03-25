package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Load {

    TETile avatarTile;
    TETile hunterTile;
    TETile worldTile;

    // change avatarTile directly, not present time
    public void loadAvatar(Character avatar) {
        if (avatar.equals('0') || avatar.equals('1') || avatar.equals('2')) {
            avatarTile = Tileset.AVATAR;
        } else if (avatar.equals('3') || avatar.equals('4') || avatar.equals('5')) {
            avatarTile = Tileset.TREE;
        } else if (avatar.equals('6') || avatar.equals('7')) {
            avatarTile = Tileset.MOUNTAIN;
        } else if (avatar.equals('8') || avatar.equals('9')) {
            avatarTile = Tileset.FLOWER;
        }
    }

    // change hunterTile directly, not present time
    public void loadHunter(Character hunter) {
        if (hunter.equals('0') || hunter.equals('1') || hunter.equals('2')) {
            hunterTile = Tileset.LOCKED_DOOR;
        } else if (hunter.equals('3') || hunter.equals('4') || hunter.equals('5')) {
            hunterTile = Tileset.TREE;
        } else if (hunter.equals('6') || hunter.equals('7')) {
            hunterTile = Tileset.MOUNTAIN;
        } else if (hunter.equals('8') || hunter.equals('9')) {
            hunterTile = Tileset.FLOWER;
        }
    }


    // change worldTile directly, not present time
    public void loadWorld(Character world) {
        if (world.equals('0') || world.equals('1') || world.equals('2')) {
            worldTile = Tileset.FLOOR;
        } else if (world.equals('3') || world.equals('4') || world.equals('5')) {
            worldTile = Tileset.SAND;
        } else if (world.equals('6') || world.equals('7')) {
            worldTile = Tileset.WATER;
        } else if (world.equals('8') || world.equals('9')) {
            worldTile = Tileset.GRASS;
        }
    }

    // return the avatar tile
    public TETile getAvatarTile() {
        return avatarTile;
    }

    // return the hunter tile
    public TETile getHunterTile() {
        return hunterTile;
    }

    // return the world tile
    public TETile getWorldTile() {
        return worldTile;
    }
}
