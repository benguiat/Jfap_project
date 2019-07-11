package de.unisaar.faphack.model.map;

import java.util.List;

import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.Item;

/**
 * @author
 *
 */
public abstract class Tile {
  protected int x;
  protected int y;
  /**
   * The room this tile is located in. This must not be null.
   */
  protected Room room;
  /**
   * The items placed on this tile.
   */
  protected List<Item> items;

  public Tile() {

  }

  /**
   * Given the "vector" d, what's the tile you get in return? (Hint: ask the room)
   * 
   * @return the next tile in direction d
   */
  public Tile getNextTile(Direction d) {
    return null;
  }

  /**
   * Can c proceed onto this tile?
   *
   * @return the current tile if you can move the Character c onto this tile, null
   *         otherwise
   */
  public abstract Tile willTake(Character c);

  public List<Item> onTile() {
    return items;
  }
}
