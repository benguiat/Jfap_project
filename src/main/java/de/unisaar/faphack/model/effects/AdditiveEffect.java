package de.unisaar.faphack.model.effects;

import de.unisaar.faphack.model.CharacterModifier;

/**
 * Additive effects directly modify the health and magic of a character.
 *
 * @author
 *
 */
public class AdditiveEffect implements ModifyingEffect {
  /**
   * The amount by which the health of the affected character is increased/dec
   * reased.
   */
  protected int health = 0;

  /**
   * The amount by which the magic of the affected character is increased/decr
   * eased.
   */
  protected int magic = 0;

  /**
   * The amount by which the magic of the affected character is increased/decr
   * eased.
   */
  protected int power = 0;

  /**
   * This integer defines how long an effect applies. The value decreases when
   * ever a character moves or performs an action.
   */
  protected int howLong = 1;

  public AdditiveEffect(int h, int m, int p, int hl) {
    health = h;
    magic = m;
    power = p;
    howLong = hl;
  }

  /**
   * Adapts the values the given Modifier. Returns a new Modifier, without
   * affecting the argument object.
   *
   * @param c
   * @return a new CharacterModifier
   */
  public CharacterModifier apply(CharacterModifier c) {
    return null;
  }

}
