package de.unisaar.faphack.model.map;

import java.util.ArrayList;
import java.util.List;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.Item;
import de.unisaar.faphack.model.MarshallingContext;

/**
 * @author
 *
 */
public class FloorTile extends Tile {
  /**
   * The items placed on this tile.
   */
  protected List<Item> items;

  public FloorTile() {
    trait = FLOOR;
  }

  public FloorTile(int x, int y, Room room){
    super(x, y, room);
    trait = FLOOR;
  }

  @Override
  public Tile willTake(Character c) {
    return this;
  }

  /** FloorTiles can have items on them */
  public List<Item> onTile() {
    return items;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO: FILL THIS
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO: FILL THIS
  }

  @Override
  public String toString(){
    return "FloorTile (" + x + ","+y+") in room "+ room;
  }
}
