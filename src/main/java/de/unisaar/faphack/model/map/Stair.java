package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.MarshallingContext;

/**
 * (NULL)
 *
 * @author
 *
 */
public class Stair extends Connector<StairTile> {
  /**
   * If true, can only be used in direction from -> to
   */
  private boolean oneWay = false;

  public Stair() {

  }

  public boolean onlyDown() {
    return oneWay;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
  }
}
