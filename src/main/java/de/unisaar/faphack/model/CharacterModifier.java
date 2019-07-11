package de.unisaar.faphack.model;

public class CharacterModifier {
  // what this modifier does to the various aspects of a character
  public final int health;
  public final int magic;
  public final int power;

  private int howLong;

  public CharacterModifier(int h, int m, int p, int hl) {
    health = h;
    magic = m;
    power = p;
    howLong = hl;
  }

  /**
   * Apply the changes of this modifier to c, but only if howLong is not zero
   */
  public boolean applyTo(Character c) {
    return false;
  }

  public int howLong() {
    return howLong;
  }
}
