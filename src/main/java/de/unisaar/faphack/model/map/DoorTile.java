package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;

/**
 * @author
 *
 */
public class DoorTile extends WallTile {
  private boolean locked = false;
  private Hallway hallway;

  /**
   * To be opened by an item (key) the Effect of that item needs to create a m
   * atching ID.
   */
  private int keyId;

  public DoorTile() {

  }

  @Override
  public Tile willTake(Character c) {
    return null;
  }
}
