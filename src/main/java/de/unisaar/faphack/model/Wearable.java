
package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Tile;

/**
 * Wearables are Items that can be carried by a Character. These include armor,
 * weapons, food, potions, key and others.
 */
public class Wearable extends Item {

    /**
     * The weight of the item.
     */
    protected int weight;

    /**
     * Whether item is a weapon.
     */
    protected boolean isWeapon;

    /**
     * The character who carries this item. This is null if the Item is placed
     * on a Tile.
     */
    protected Character character;

    public Wearable() {

    }

    @Override
    public void marshal(MarshallingContext c) {
        super.marshal(c);
        
        c.write("character", this.character);
        c.write("weight", this.weight);

        if (this.isWeapon) {
            c.write("isWeapon", 1);
        } else {
            c.write("isWeapon", 0);
        }
    }

    @Override
    public void unmarshal(MarshallingContext c) {
        super.unmarshal(c);
        this.weight = c.readInt("weight");
        this.character = c.read("character");
        this.isWeapon = (c.readInt("isWeapon")==1);
    }

    /**
     * Pick up wearable from tile. Adds wearable to character, and remove wearable from tile.
     * 
     * @param c Character to apply item to
     */
    public void pickUp(Character c) {
  
        this.character = c;
        this.onTile = null;
    }

    /**
     * Drops wearable on tile. Add wearable to tile, and remove wearable for character.
     * 
     * @param t Tile to drop item on
     */
    public void drop(Tile t) {
        this.onTile = t;
        this.character = null;
    }
}
