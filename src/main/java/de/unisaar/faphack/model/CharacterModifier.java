package de.unisaar.faphack.model;

public class CharacterModifier implements Storable {
  // what this modifier does to the various aspects of a character
  public int health;
  public int magic;
  public int power;

  private int howLong;

  public CharacterModifier(){}

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
    // TODO fill this
    return false;
  }

  public int howLong() {
    return howLong;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO fill this
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO fill this
  }
}
