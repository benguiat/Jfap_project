package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;

/**
 * @author
 *
 */
public class StairTile extends Tile {
  protected Stair stair;

  public StairTile() {

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
    return null;
  }

}
