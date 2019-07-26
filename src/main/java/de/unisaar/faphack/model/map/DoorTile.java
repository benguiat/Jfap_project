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

    public DoorTile(int x, int y, Room room) {
        //get the x, y, and room from the Tile class
        super(x, y, room);
    }

    @Override
    public Tile willTake(Character c) {
        //if the door is open or the character has enough power to open the door
        if (this.open || c.getPower() > this.destructible) {
            //initialize hallway variable and call hallway method
            Hallway hw = this.getHallway();
            //the character needs to cross the hallway in order to get through the door
            //therefore, the character is moving from one tile to another
            //initialize variable for the destination tile
            Tile t1 = hw.to();
            //initialize variable for the current tile
            Tile t2 = hw.from();
            //if the character is on the destination tile
            if (this == t1) {
                //return the current tile
                return t2;
                //if the character is not on the destination tile
            } else {
                //return the destination tile
                return t1;
            }
            //the door is locked and the character is not strong enough to open it
        } else {
            return null;
        }
    }

    @Override
    public void marshal(MarshallingContext c) {
        //call the marshal class from JsonMarshallingContext
        super.marshal(c);
        //save the destructible, hallway, keyId variables
        c.write("destructible", this.destructible);
        c.write("hallway", this.hallway);
        c.write("keyId", this.keyId);
        //if the door is locked, write locked is true
        if (this.locked == true) {
            c.write("locked", 1);
            //if door is open, write locked is false
        } else {
            c.write("locked", 0);
        }
        //if the door is open, write open is true
        if (this.open == true) {
            c.write("open", 1);
            //if the door is closed, write open is false
        } else {
            c.write("open", 0);
        }

    }

    @Override
    public void unmarshal(MarshallingContext c) {
        //call the marshal class from JsonMarshallingContext
        super.unmarshal(c);
        //read the variables hallway, keyId, locked, and open
        //we need read and readInt because of strings vs integers
        this.hallway = c.read("hallway");
        this.keyId = c.readInt("keyId");
        
        this.locked = (c.readInt("locked") == 1);
        this.open = (c.readInt("open") == 1);

    }
    //method to get the hallway that the character passes through to open the door
    public Hallway getHallway() {
        return hallway;
    }

    @Override
    //method to get the status of the door: open or locked
    public String getTrait() {
        return open ? OPENDOOR : DOOR;
    }

    @Override
    //methods to give information to GUI
    public void register(Observer<DoorTile> observer) {
        // lazy initialization
        // TODO please implement me!
        if (observers == null) {
      observers = new ArrayList<>();
    }
    observers.add(observer);
  }



    @Override
    public void notifyObservers(DoorTile object) {
        if (observers != null)
      for(Observer<DoorTile> o: observers) { o.update(object); }
        // TODO please implement me!
    }

}
