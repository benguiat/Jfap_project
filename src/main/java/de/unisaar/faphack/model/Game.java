package de.unisaar.faphack.model;

import java.util.List;

import de.unisaar.faphack.model.effects.MoveEffect;
import de.unisaar.faphack.model.map.Tile;
import de.unisaar.faphack.model.map.World;

/**
 * @author
 *
 */
public class Game implements Storable {
  private World world;

  public Game() {

  }

  /**
   * @param whom
   * @param direction
   * @return boolean
   */
  public boolean move(Character whom, Direction direction) {
    // TODO: fill this
    return false;
  }

  /**
   *
   * @param who
   * @param direction
   * @return List<Item>
   */
  public List<Item> listItems(Character who, Direction direction) {
    // TODO: fill this
    return null;
  }

  /**
   * @param who
   * @param item
   * @return boolean
   */
  public boolean pickUp(Character who, Item item) {
    // TODO: fill this
    return false;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO: fill this
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO: fill this
  }

}
