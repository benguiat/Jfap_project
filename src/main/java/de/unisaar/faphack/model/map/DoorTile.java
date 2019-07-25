package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class DoorTile extends WallTile implements Storable, Observable<DoorTile> {
  private boolean open = false;

  private boolean locked = false;

  private Hallway hallway;

  private List<Observer<DoorTile>> observers;

  /**
   * To be opened by an item (key) the Effect of that item needs to create a m
   * atching ID.
   */
  private int keyId;

  public DoorTile() {
  }

  public DoorTile(int x, int y, Room room){
    super(x, y, room);
  }

  @Override
  public Tile willTake(Character c) {
    //need to check to see if character is strong enough to open the door
    //if the door is locked
    if (this.open || c.getPower() > this.destructible) {
        Hallway hw = this.getHallway();
        Tile t1 = hw.to();
        Tile t2 = hw.from();
        if (this == t1) {
            return t2;
        }
        else {
            return t1;
        }
    }
    else {
        return null;
    }
    //if the door is open
  }

    //  }
    //  else {
    // return null;

    //}


    // TODO please implement me!
    // return null;


  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me
    super.marshal(c);
    c.write("destructible", this.destructible);
    c.write("hallway", this.hallway);
    c.write("keyId", this.keyId);
    c.write("trait", this.trait);
    c.write("x", this.x);
    c.write("y", this.y);
    if (this.locked == true) {
        c.write("locked", 1);;
    }
    else {
        c.write("locked", 0);
    }
    if (this.open == true) {
        c.write("open", 1);;
    }
    else {
        c.write("open", 0);
    }

  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
  }

public Hallway getHallway(){
    return hallway;
  }

  @Override
  public String getTrait() { return open ? OPENDOOR : DOOR; }

  @Override
  public void register(Observer<DoorTile> observer) {
    // lazy initialization
    // TODO please implement me!

  }

  @Override
  public void notifyObservers(DoorTile object) {
    // TODO please implement me!
  }


}
