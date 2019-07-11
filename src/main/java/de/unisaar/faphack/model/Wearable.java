package de.unisaar.faphack.model;

/**
 * Wearables are Items that can be carried by a Character. These include armor,
 * weapons, food, potions, key and others.
 * 
 * @author
 *
 */
public class Wearable extends Item {
  /**
   * The weight of the item.
   */
  protected int weight;
  protected boolean isWeapon;
  /**
   * The character who carries this item. This is null if the Item is placed o n a
   * Tile.
   */
  protected Character character;

  public Wearable() {

  }

}
