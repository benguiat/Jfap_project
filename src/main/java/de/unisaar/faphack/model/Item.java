package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Tile;

/**
 * @author
 *
 */
public abstract class Item implements Storable, TraitedTileOccupier {
  /**
   * The Tile on which the item is placed. This is null if the Item is in the
   * inventory of a character.
   */
  protected Tile onTile;

  /**
   * The trait of this item.
   */
  protected String trait;

  /**
   * The Effect connected to the item.
   */
  protected CharacterModifier effect;

  public Item() {

  }

  public void marshal(MarshallingContext c) {
    // TODO FILL THIS
  }

  public void unmarshal(MarshallingContext c) {
    // TODO FILL THIS
  }

  @Override
  public Tile getTile() { return onTile; }

  @Override
  public String getTrait() { return trait; }
}
