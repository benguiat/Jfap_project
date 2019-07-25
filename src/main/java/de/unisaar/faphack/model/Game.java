package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.MoveEffect;
import de.unisaar.faphack.model.map.Tile;
import de.unisaar.faphack.model.map.World;

import java.util.List;

/**
 * @author
 *
 */
public class Game implements Storable {
  private World world;
  private Character protagonist;

  public Game() {

  }

  /**
  * return the protagonist of this game
  */
  public Character getProtagonist() {
    return protagonist;
  }

  /**
   * tries to move the character into the given direction.
   * @param whom
   * @param direction
   * @return boolean
   */
  public boolean move(Character whom, Direction direction) {
    // TODO please implement me!
    if (whom.power > 0) {
    if (Math.abs(direction.x) + Math.abs(direction.y) <= 1) {
        return true;
    }
    else {
        return false;
    }
    }
    else {
        return false;
    }
  }

  /**
   * The character rests, i.e. it moves with direction (0,0) and its power increases by 5
  */
  public boolean rest(Character whom){
    // TODO please implement me!
    Direction dir = new Direction(0,0);
    Tile newTile = whom.getTile().getNextTile(dir);
    whom.move(newTile);
    whom.power += 5;
    return true;
  }

  /**
   *
   * @param who
   * @param direction
   * @return List<Item>
   */
  public List<Item> listItems(Character who, Direction direction) {
    Tile current = who.getTile();
    Tile next = current.getNextTile(direction);
    return next.onTile();
  }

  /**
   * Let a character pickup the given item
   * @param who the character
   * @param item the item to be picked up
   * @return boolean <code>true</code> if the character managed to pickup the item, <code>false</code> otherwise
   */
    public boolean pickUp(Character who, Item item) {
        // TODO please implement me!
         if (who.getTile() == item.getTile()) {
            Tile itemTile = item.getTile();

            if (item instanceof Wearable) {
                ((Wearable) item).onTile = null;
                itemTile.onTile().remove(item);
                who.items.add((Wearable) item);
                ((Wearable) item).character = who;

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

  /**
   * Removes an item from the given characters inventory and places it on the tile
   * @param who the character performing the action
   * @param what the item to be removed
   * @return <code>true</code> if the action was successful, <code>false</code> otherwise
   */
  public boolean drop(Character who, Wearable what){
    // TODO please implement me!
    if (who.items.contains(what) || who.armor.contains(what)) {
        if (who.items.contains(what)){
            who.items.remove(who);
        }
        else {
            who.armor.remove(who);
        }
        Tile CharTile = who.getTile();
        CharTile.addItem(what);
        what.onTile = CharTile;
        return true;
    }
    else {
        return false;
    }
  }

  /**
   * Equips the given Wearable as active Weapon or armor depending
   *
   * @param who the character performing the action
   * @param what the item to be equipped
   * @return <code>true</code> the action was successful, <code>false</code> otherwise
   */
  public boolean equip(Character who, Wearable what){
        if (who.items.contains(what) || who.armor.contains(what)) {
        if (who.items.contains(what)){
            who.items.remove(who);
        }
        else {
            who.armor.remove(who);
        }
        Tile CharTile = who.getTile();
        CharTile.addItem(what);
        what.onTile = CharTile;
        return true;
    }
    else {
        return false;
    }
  }

  @Override
  public void marshal(MarshallingContext c) {
    c.write("world", this.world);
    c.write("protagonist", this.protagonist);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
    world = c.read("world");
    protagonist = c.read("protagonist");
  }

  public World getWorld() {
    return world;
  }

  /** Add the game's protagonist to a random floor tile in the first room */
  public void setProtagonist(Character prot) {
    // TODO: fill here
    protagonist = prot;
  }

  /** get the game's protagonist */
  public Character getProtagonist(Character prot) {
    // TODO: fill here
    return protagonist;
  }
}
