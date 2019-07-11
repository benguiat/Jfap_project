package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.ItemEffect;
import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;

/**
 * @author
 *
 */
public abstract class Item {
  /**
   * The Tile on which the item is placed. This is null if the Item is in the
   * inventory of a character.
   */
  protected Tile onTile;
  /**
   * The room in which the tile is located. This is null if the Item is in the
   * inventory of a character.
   */
  protected Room room;
  /**
   * The Effect connected to the item.
   */
  protected ItemEffect effect;

  public Item() {

  }

}
