package de.unisaar.faphack.model.effects;

import de.unisaar.faphack.model.CharacterModifier;

/**
 * Additive effects directly modify the health and magic of a character.
 *
 * @author
 *
 */
public class MultiplicativeEffect implements ModifyingEffect {
  /**
   * The amount by which the health of the affected character is increased/dec
   * reased.
   */
  protected double health = 1;

  /**
   * The amount by which the magic of the affected character is increased/decr
   * eased.
   */
  protected double magic = 1;

  /**
   * The amount by which the magic of the affected character is increased/decr
   * eased.
   */
  protected double power = 1;

  /**
   * This integer defines how long an effect applies. The value decreases when
   * ever a character moves or performs an action.
   */
  protected int howLong = 1;

  public MultiplicativeEffect(int h, int m, int p, int hl) {
    health = h;
    magic = m;
    power = p;
    howLong = hl;
  }

  /**
   * Modifies the values the given character.
   *
   * @param on
   * @return void
   */
  public CharacterModifier apply(CharacterModifier c) {
    return null;
  }

}
