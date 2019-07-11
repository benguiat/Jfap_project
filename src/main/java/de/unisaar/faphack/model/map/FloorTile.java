package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;

/**
 * @author
 *
 */
public class FloorTile extends Tile {

  public FloorTile() {

  }

  @Override
  public Tile willTake(Character c) {
    return this;
  }

}
