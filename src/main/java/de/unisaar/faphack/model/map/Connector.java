package de.unisaar.faphack.model.map;

/**
 * @author
 *
 */
public abstract class Connector<T extends Tile> {
  protected T fromTile;
  protected T toTile;

  public T from() {
    return fromTile;
  }

  public T to() {
    return toTile;
  }
}
