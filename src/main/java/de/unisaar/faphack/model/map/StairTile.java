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

  public StairTile(int x, int y, Room room){
    super(x, y, room);
    trait = STAIR;
  }

  /**
   * A stair can (possibly) be used in both directions: it depends on where you
   * are currently.
   *
   * Remember to update the level of the character.
   *
   * @return the new tile, or null if not possible to use
   */
  @Override
  public Tile willTake(Character c) {
    // TODO please implement me!
    Stair stair = this.getStair();
    boolean oneWay = stair.onlyDown();
    Tile toTile = stair.to();
    Tile fromTile = stair.from();
    if (oneWay == true && this == toTile) {
        return null;
    } 
    else {
        if (this == toTile) {
            return fromTile;
        }
        else {
            return toTile;
        }
    }
  }

  /** Return non-null if this is a trap */
  @Override
  public Trap hasTrap() {
    return trap;
  }

  @Override
  public void marshal(MarshallingContext c) {
    super.marshal(c);
    c.write("stair", this.stair);
    c.write("trap", this.trap);
    c.write("trait", this.trait);
//    c.write("x", this.x);
//    c.write("y", this.y);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    super.unmarshal(c);
    stair = c.read("stair");
    trap = c.read("trap");
    trait = c.read("trait");
    
  }

  public Stair getStair(){
    return stair;
  }
}
