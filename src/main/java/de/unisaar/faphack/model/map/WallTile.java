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
    if (c.getPower() >= this.destructible && this.destructible != 0){
        return this;
    }
    else {
        return null;
    }
  }

  @Override
  public void marshal(MarshallingContext c) {
    super.marshal(c);
    
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    super.unmarshal(c);
  }

  @Override
  public String getTrait() { return destructible < 0 ? DESTROYED_WALL : WALL; }
}
