package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Storable;

/**
 * @author
 *
 */
public abstract class Connector<T extends Tile> implements Storable {
  protected T fromTile;
  protected T toTile;

  public T from() {
    return fromTile;
  }

  public T to() {
    return toTile;
  }

  public void marshal(MarshallingContext c) {
    // TODO: FILL THIS
  }

  public void unmarshal(MarshallingContext c) {
    // TODO: FILL THIS
  }
}
