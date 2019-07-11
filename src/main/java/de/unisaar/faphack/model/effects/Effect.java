package de.unisaar.faphack.model.effects;

/**
 * Effects are connected to character values and items. They are used to modify
 * a character or other effects.
 *
 * @author
 *
 */
@FunctionalInterface
public interface Effect<T, R> {
  /**
   * Applies this Effect to the T argument and returns a value
   *
   * @param applyTo what this effect is applied to
   * @return possibly a value of type R
   */
  public R apply(T applyTo);

}
