package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.MarshallingContext;

/**
 * @author
 *
 */
public class StairTile extends Tile {

    protected Stair stair;

    protected Trap trap;

    public StairTile() {
        trait = STAIR;
    }

    public StairTile(int x, int y, Room room) {
        super(x, y, room);
        trait = STAIR;
    }

    /**
     * A stair can (possibly) be used in both directions: it depends on where
     * you are currently.
     *
     * Remember to update the level of the character.
     *
     * @return the new tile, or null if not possible to use
     */
    @Override
    public Tile willTake(Character c) {
        //initialize stair variable
        Stair stair = this.getStair();
        //initialize variable to only descend the stairs
        boolean oneWay = stair.onlyDown();
        //initialize variable to go to another tile in the stairs
        Tile toTile = stair.to();
        //initialize variable to go from another tile in the stairs
        Tile fromTile = stair.from();

        //if the character is only descending the stairs and going to another tile
        if (oneWay == true && this == toTile) {
            //the character can't
            return null;
        } else {
            //if the character is going to another tile
            if (this == toTile) {
                //character levels down
                c.levelDown();
                //character goes from another tile in the stairs
                return fromTile;

            } else {
                //character levels up
                c.levelUp();
                //character goes to another tile in the stairs
                return toTile;
            }
        }
    }

    /**
     * Return non-null if this is a trap
     */
    @Override
    public Trap hasTrap() {
        return trap;
    }

    @Override
    public void marshal(MarshallingContext c) {
        //call the marshal method from JsonMarshallingContext
        super.marshal(c);
        //write stair and trap variables
        c.write("stair", this.stair);
        c.write("trap", this.trap);

    }

    @Override
    public void unmarshal(MarshallingContext c) {
        //call the unmarshal method from JsonMarshallingContext
        super.unmarshal(c);
        //read stair and trap variables
        stair = c.read("stair");
        trap = c.read("trap");

    }

    public Stair getStair() {
        return stair;
    }
}
