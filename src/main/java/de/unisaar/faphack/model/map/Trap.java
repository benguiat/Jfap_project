package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Fixtures;

/**
 * A Trap is a special Fixture. Its effect is triggered by moving on the tile it
 * is placed on.
 * 
 * @author
 *
 */
public class Trap extends Fixtures {
  /**
   * Traps might also be placed on StairTiles. In this case, the stair is mas ked
   * by the Trap and thus not visible for the character, i.e. a trap door.
   * 
   */
  protected StairTile trapDoor = null;

  public Trap() {

  }

}
