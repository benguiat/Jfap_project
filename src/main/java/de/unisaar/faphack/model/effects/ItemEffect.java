package de.unisaar.faphack.model.effects;

import de.unisaar.faphack.model.Item;

/**
 * Multiplicative effects can be used to modify additive effects.
 *
 * @author
 *
 */
@FunctionalInterface
public interface ItemEffect<R> extends Effect<Item, R> {

  /**
   * Modifies the values the given character.
   *
   * @param on
   * @return void
   */
  public R apply(Item i);

}
