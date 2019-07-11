
package de.unisaar.jfap;

import java.util.ArrayList;


public class Tile {
  protected int x;
  protected int y;
  protected Room room;
  protected ArrayList<Items> items;

  public Tile(int x, int y, Room room, ArrayList<Items> items) {
    this.x = x;
    this.y = y;
    this.room = room;
    this.items = items;
  }
}
