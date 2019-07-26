package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.MoveEffect;
import de.unisaar.faphack.model.map.Tile;
import de.unisaar.faphack.model.map.WallTile;
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
      //if the character has power
    if (whom.power > 0) {
        //if the direction is less than or equal to 1
    if (Math.abs(direction.x) + Math.abs(direction.y) <= 1) {
        //set variable for the character's destination-
        //the direction of the next tile
        Tile dest = whom.tile.getNextTile(direction);
        //if the destination is a Wall Tile
        if (dest instanceof WallTile) {
            //if the character is able to move to the destination
            //i.e. character has enough power, tile is destructible, etc.
            if (dest == dest.willTake(whom)) {
                //character moves to its destination
                whom.move(dest);
                return true;
            }
            //if there is an obstacle and the character can't move
            else {
                return false;
            }
        }
        //character can move
        else {
            whom.move(dest);
                return true;
        }
    }//character can't move due to lack of direction
    else {
        return false;
    }
    }//character can't move due to lack of power
    else {
        return false;
    }
  }

  /**
   * The character rests, i.e. it moves with direction (0,0) and its power increases by 5
  */
  public boolean rest(Character whom){
    // TODO please implement me!
      //at rest, there is no direction
    Direction dir = new Direction(0,0);
    //instantiate a new tile, get current tile, and the next tile (which is 0 because the character is resting)
    Tile newTile = whom.getTile().getNextTile(dir);
    //the character rests
    whom.move(newTile);
    //the character increases its power by 5
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
      //get the current tile
    Tile current = who.getTile();
    //get the next tile from the current tile
    Tile next = current.getNextTile(direction);
    //get the next tile
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
        //if the character gets the item from the tile
         if (who.getTile() == item.getTile()) {
             //the item from the tile
            Tile itemTile = item.getTile();
            //if the item is a wearable
            if (item instanceof Wearable) {
                //take the wearable off the the tile
                ((Wearable) item).onTile = null;
                itemTile.onTile().remove(item);
                //add the wearable to the character
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
      //if the character or the character's armor contains the wearable
    if (who.items.contains(what) || who.armor.contains(what)) {
        //if the character has the wearable
        if (who.items.contains(what)){
            //remove the wearable from the character
            who.items.remove(who);
        }
        else {
            //remove the wearable from the armor
            who.armor.remove(who);
        }
        //instantiate a new tile and get the current tile
        Tile CharTile = who.getTile();
        //add the freshly removed item to the tile
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
      //if the wearable is on the character or on the armor
        if (who.items.contains(what) || who.armor.contains(what)) {
            //if the character has the wearable
        if (who.items.contains(what)){
            //remove the wearable from the character
            who.items.remove(who);
        }
        //remove the wearable from the armor
        else {
            who.armor.remove(who);
        }
        //add the wearable to the new tile
        Tile CharTile = who.getTile();
        CharTile.addItem(what);
        what.onTile = CharTile;
        return true;
    }
        //if all fails, the wearable is still on the character/armor
    else {
        return false;
    }
  }

  @Override
  public void marshal(MarshallingContext c) {
      //marshal the world and protagonist
    c.write("world", this.world);
    c.write("protagonist", this.protagonist);
  }

  @Override
  public void unmarshal(MarshallingContext c) {
      //unmarshal the world and protagonist
    world = c.read("world");
    protagonist = c.read("protagonist");
  }

  public World getWorld() {
    return world;
  }

  /** Add the game's protagonist to a random floor tile in the first room */
  public void setProtagonist(Character prot) {
    // TODO: fill here
      //initialize the protagonist
    protagonist = prot;
  }

  /** get the game's protagonist */
  public Character getProtagonist(Character prot) {
    // TODO: fill here
      //get the protagonist
    return protagonist;
  }
}
