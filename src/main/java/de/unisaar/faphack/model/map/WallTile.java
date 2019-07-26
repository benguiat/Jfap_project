package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.MarshallingContext;

/**
 * Walltiles are used to determine the arrangement of a room. They usually
 * define the outer borders of a room, but might also be used within a room to
 * separate areas.
 * __________________
 * |                |
 * |                |
 * |   _______      |
 * |  |_____  |     D
 * |________________|
 *
 * @author
 *
 */
public class WallTile extends Tile {
  /** 0 means infinitely strong, > 0 means: must apply at least this force */
  protected int destructible;

  public WallTile() { }

  public WallTile(int x, int y, Room room){
    super(x, y, room);
  }

  @Override
  public Tile willTake(Character c) {
    //if the character has enough power than the door and the door's destructibility is 0
    if (c.getPower() >= this.destructible && this.destructible != 0){
      //the character can destroy the wall tile
        return this;
    }
    //the character cannot destroy the wall tile
    else {
        return null;
    }
  }

  @Override
  public void marshal(MarshallingContext c) {
    //call the marshal method from JsonMarshallingContext
    super.marshal(c);
    //write the destructible variable
    c.write("destructible", this.destructible);
   
    
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    //call the marshal method from JsonMarshallingContext
    super.unmarshal(c);
    //read the destructible variable
    this.destructible = c.readInt("destructible");
  }

  @Override
  //get the wall destructibility
  public String getTrait() { return destructible < 0 ? DESTROYED_WALL : WALL; }
}
