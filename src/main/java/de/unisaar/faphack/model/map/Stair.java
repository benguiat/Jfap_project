package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.MarshallingContext;

/**
 * (NULL)
 *
 * @author
 *
 */
public class Stair extends Connector<StairTile> {

    /**
     * If true, can only be used in direction from -> to
     */
    private boolean oneWay = false;

    public Stair() {

    }

    public boolean onlyDown() {
        return oneWay;
    }

    @Override
    public void marshal(MarshallingContext c) {
        super.marshal(c);

        c.write("fromTile", this.fromTile);
        c.write("toTile", this.toTile);

        if (this.oneWay) {
            c.write("oneWay", 1);
        } else {
            c.write("oneWay", 0);
        }
    }

    @Override
    public void unmarshal(MarshallingContext c) {
        super.unmarshal(c);

        fromTile = c.read("fromTile"); // should this be readBoard?????
        toTile = c.read("toTile"); // should this be readBoard?????
        
        oneWay = (c.readInt("oneWay") == 1);
    }
}
