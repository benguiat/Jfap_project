package de.unisaar.faphack.model.map;

import java.util.List;

import de.unisaar.faphack.model.Game;
import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Storable;

/**
 * @author
 *
 */
public class World implements Storable {
  public Game g;

  private List<Room> mapElements;

  public World() {}

  @Override
  public void marshal(MarshallingContext c) {
    // TODO: FILL THIS
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO: FILL THIS
  }

  public List<Room> getMapElements(){
    return mapElements;
  }
}
