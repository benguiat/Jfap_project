package de.unisaar.faphack.model.map;

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
}
