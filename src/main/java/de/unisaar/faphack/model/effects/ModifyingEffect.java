package de.unisaar.faphack.model.effects;

import de.unisaar.faphack.model.CharacterModifier;

/**
 * Effects are connected to character values and items. They are used to modify
 * a character or other effects.
 *
 * @author
 *
 */
@FunctionalInterface
public interface ModifyingEffect extends Effect<CharacterModifier, CharacterModifier> {
  /**
   * Applies this Effect to the T argument and returns a value
   *
   * @param applyTo what this effect is applied to
   * @return possibly a value of type R
   */
  public CharacterModifier apply(CharacterModifier applyTo);

}
