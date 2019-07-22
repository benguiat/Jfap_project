package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Tile;

import java.util.Collection;

public interface MarshallingContext {

  /** Save the object and all its depending objects to background storage */
  public void save(Storable s);

  /** Read an object and all its depending objects from background storage */
  public Storable read();

  /** Write field key, which contains a Storable */
  public void write(String key, Storable object);

  /** Read the object for field key, which contains a Storable */
  public <T extends Storable> T read(String key);

  /** Write field key, which contains an int */
  public void write(String key, int object);

  /** Read the value for field key, which contains an int */
  public int readInt(String key);

  /** Write field key, which contains a double */
  public void write(String key, double object);

  /** Read the value for field key, which contains a double */
  public double readDouble(String key);

  /** Write field key, which contains a String */
  public void write(String key, String object);

  /** Read the value for field key, which contains a String */
  public String readString(String key);

  /** Write field key, which contains a collection of Storables */
  public void write(String key, Collection<? extends Storable> coll);

  /** Read field key, putting the Storables into the fresh collection coll */
  public void readAll(String key, Collection<? extends Storable> coll);

  /** Specialized writing / reading for boards */
  public void write(String key, Tile[][] coll);

  public Tile[][] readBoard(String key);
}
