package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Storable;

import java.util.List;

/**
 * @author
 *
 */
public class Room implements Storable {

  /** The world this room belongs to */
  World w;

  /** The Characters that currently are in this room */
  private List<Character> inhabitants;

  /**
   * A 2-dimensional Array defining the layout of the tiles in the room.
   */
  private Tile[][] tiles;

  public Room(){}

  Room(Tile[][] tiles) { this.tiles = tiles; }

  public Tile getNextTile(Tile t, Direction d) {
      int x = t.getX() + d.x;
      int y = t.getY() + d.y;
      
      return tiles[x][y];
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  public List<Character> getInhabitants() {
    return inhabitants;
  }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO please implement me!
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
  }
}
