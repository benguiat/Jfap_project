package de.unisaar.faphack.model;

public class Direction {
  public int x, y;

  /** While for MoveEffects, x and y should be either -1, 0, 1, we may want
   *  to allow other values, e.g, to shoot an arrow.
   */
  public Direction(int x, int y) {
    this.x = x;
    this.y = y;
  }

}
