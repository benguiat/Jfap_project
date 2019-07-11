package de.unisaar.faphack.model;

import java.util.List;

import de.unisaar.faphack.model.effects.MoveEffect;
import de.unisaar.faphack.model.map.Tile;
import de.unisaar.faphack.model.map.World;

/**
 * @author
 *
 */
public class Game {
  private World world;

  public Game() {

  }

  /**
   * @param whom
   * @param destination
   * @return boolean
   */
  public boolean move(Character whom, Direction direction) {
    return false;
  }

  /**
   *
   * @param who
   * @param direction
   * @return List<Item>
   */
  public List<Item> listItems(Character who, Direction direction) {
    return null;
  }

  /**
   * @param who
   * @param item
   * @return boolean
   */
  public boolean pickUp(Character who, Item item) {
    return false;
  }

}
