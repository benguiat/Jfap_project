package de.unisaar.faphack.model.map;

import java.util.List;

import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.Direction;
import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Storable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class Room implements Storable {

  /** The world this room belongs to */
  World w;

  /** The Characters that currently are in this room */
  private List<Character> inhabitants = new ArrayList<>();

  /**
   * A 2-dimensional Array defining the layout of the tiles in the room.
   */
  private Tile[][] tiles;

  public Room(){}

  /**
   /**
   * This method returns a tile determined by the specified tile <code> t </ code> and the <code> direction </ code> d.
   * If the path between the specified tile and the derived tile is blocked by a wall,
   * the wall tile is returned.
   *
   * HINT: use the computeDDA to compute the path
   *
   * @param t the start tile
   * @param d the direction to follow
     * @return
     */
    public Tile getNextTile(Tile t, Direction d) {

        int x;
        int y;

        if (Math.abs(d.x) >= (this.tiles.length - 2) || Math.abs(d.y) >= (this.tiles.length - 2)) {
            if (d.x > 0 && d.y > 0) {
                x = t.getX() + (this.tiles.length - 2);
                y = t.getY() + (this.tiles.length - 2);
            } else if (d.x < 0 && d.y > 0) {
                x = t.getX() - (this.tiles.length - 2);
                y = t.getY() + (this.tiles.length - 2);
            } else if (d.x < 0 && d.y < 0) {
                x = t.getX() - (this.tiles.length - 2);
                y = t.getY() - (this.tiles.length - 2);
            } else if (d.x > 0 && d.y < 0){
               
                x = t.getX() + (this.tiles.length - 2);
                y = t.getY() - (this.tiles.length - 2);
            } else {
                x = t.getX() + d.x;
                y = t.getY() + d.y;
            }
        } else {
            x = t.getX() + d.x;
            y = t.getY() + d.y;
        }
        return tiles[x][y];
    }


  private List<Tile> computeDDA(Tile t, Direction d){
    List<Tile> path = new ArrayList<>();

    // Calculate number of steps
    int steps = Math.abs(d.x) > Math.abs(d.y) ? Math.abs(d.x) : Math.abs(d.y);


    // Calculate increments
    double xIncrement = d.x / (float) steps;
    double yIncrement = d.y / (float) steps;

    // Compute points
    double x = t.x;
    double y = t.y;
    path.add(tiles[(int) x][((int) y)]);
    for (int i = 0; i < steps; i++) {
      x += xIncrement;
      y += yIncrement;
      if (x >= tiles.length || y >= tiles[0].length)
        break;
      Tile tile = tiles[(int) Math.round(x)][(int) Math.round(y)];
      path.add(tile);
    }
    return path;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  public List<Character> getInhabitants() {
    return inhabitants;
  }

  @Override
  public void marshal(MarshallingContext c) {
    c.write("inhabitants", this.inhabitants);
    c.write("tiles", this.tiles);
    // TODO please implement me!
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO please implement me!
  }
}
