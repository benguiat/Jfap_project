package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Tile;

public interface TileOccupier {

  /** Return the tile this occupier occupies */
  public abstract Tile getTile();

}
