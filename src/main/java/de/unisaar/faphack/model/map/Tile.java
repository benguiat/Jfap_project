package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.*;

import java.util.Collections;
import java.util.List;

/**
 * @author
 *
 */
public abstract class Tile implements Storable, TraitOwner {

    protected int x;
    protected int y;

    /**
     * The room this tile is located in. This must not be null.
     */
    protected Room room;

    /**
     * The trait of this item.
     */
    protected String trait;

    public Tile() {

    }

    public Tile(int x, int y, Room room) {
        //initialize room, x, and y
        this.room = room;
        this.x = x;
        this.y = y;
    }

    //get x
    public int getX() {
        return x;
    }

    //get y
    public int getY() {
        return y;
    }

    public Room getRoom() {
     return room;
    }
    /**
     * Given the "vector" d, what's the tile you get in return? (Hint: ask the
     * room)
     *
     * @return the next tile in direction d
     */
    public Tile getNextTile(Direction d) {
        //get the next tile in the room with the distance d

        return room.getNextTile(this, d);
    }

    /**
     * Can c proceed onto this tile?
     *
     * @return the current tile if you can move the Character c onto this tile,
     * null otherwise
     */
    public abstract Tile willTake(Character c);

    /**
     * Almost all tiles can not have items on them.
     */
    public List<Item> onTile() {
        return Collections.emptyList();
    }

    @Override
    public String getTrait() {
        return trait;
    }

    /**
     * Most tiles have no trap
     */
    public Trap hasTrap() {
        return null;
    }

    public void marshal(MarshallingContext c) {
        //write variables to marshal
        c.write("room", this.room);
        c.write("trait", this.trait);
        c.write("x", this.x);
        c.write("y", this.y);

    }

    public void unmarshal(MarshallingContext c) {
        //write variables to unmarshal
        room = c.read("room");
        trait = c.readString("trait");
        x = c.readInt("x");
        y = c.readInt("y");
    }

    public boolean removeItem(Wearable what) {
        return false;
    }

    public boolean addItem(Wearable what) {
        return false;
    }

    /**
     * Almost all tiles can not be occupied by a character.
     */
    public boolean isOccupied() {
        return false;
    }

    //public Room getRoom() {
    //    return room;
    //}
}
