package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.Item;
import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Wearable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class FloorTile extends Tile {
  /**
   * The items placed on this tile.
   */
  protected List<Item> items = new ArrayList<>();

  public FloorTile() {
    trait = FLOOR;
  }

  public FloorTile(int x, int y, Room room){
    //get x, y, and room variables from the tile class
    super(x, y, room);
    trait = FLOOR;
  }

  /**
   *
   * @param c the character that wants to enter this tile
   * @return  returns <code>this</code> tile if
   *            1. the class is not already occupied by an character or
   *            2. is occupied by the character itself
   *           else null
   *
   */
  @Override
  public Tile willTake(Character c) {
    //initialize variable for the current tile
    Tile currentTile = c.getTile();
    //initialize variable for the destination tile
    Tile destinationTile = this;
    //the tile is not occupied, then the character can enter the tile
    if (this.isOccupied() == false){
      return this;
    }
    //if the character is already on the tile
    if (currentTile == destinationTile){
        return this;
    }
    //tile is already occupied by another character
    //the character cannot occupy the tile
    else{
      return null;
    }
  }

  /** FloorTiles can have items on them */
  public List<Item> onTile() {
    return items;
  }

  @Override
  //method to remove wearables
  public boolean removeItem(Wearable what) {
    return items.remove(what);
  }

  @Override
  //method to add wearables
  public boolean addItem(Wearable what) {
    return items.add(what);
  }

  @Override
  public void marshal(MarshallingContext c) {
    //call marshal class from JsonMarshallingContext
    super.marshal(c);
    //write items variable
    c.write("items", this.items);

  }

  @Override
  public void unmarshal(MarshallingContext c) {
    //call unmarshal class from JsonMarshallingContext
    super.unmarshal(c);

    //create an array for the items to unmarshal
    List<Item> it = new ArrayList<>();
    //read the items
    c.readAll("items",it);
    this.items = it;
    

  }

  /**
   *
   * @return true if the tile is occupied by a character
   */
  @Override
  public boolean isOccupied(){
    //initialize list for all of the characters in the room
    List<Character> inhabitants = this.room.getInhabitants();
    //if the tile is occupied by a character
    for (Character character : inhabitants) {
        Tile tile = character.getTile();
        if (this.equals(tile)){
            return true;
        }
    }//if the tile is not occupied by a character
    return false;
  }

}
