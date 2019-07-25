
package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Game;
import de.unisaar.faphack.model.MarshallingContext;
import de.unisaar.faphack.model.Storable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class World implements Storable {

    public Game g;

    private List<Room> mapElements;

    public World() {
    }

    @Override
    public void marshal(MarshallingContext c) {
        c.write("mapElements", this.mapElements);
    }

    @Override
    public void unmarshal(MarshallingContext c) {
        List<Room> mapE = new ArrayList<>(); // this might be crap
        
        c.readAll("mapElements", mapE);
        this.g = c.read("game");
        mapElements = mapE;
    }

    public List<Room> getMapElements() {
        return mapElements;
    }
}
