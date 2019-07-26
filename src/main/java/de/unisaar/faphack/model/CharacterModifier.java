//Commented!

package de.unisaar.faphack.model;

public class CharacterModifier implements Storable {

    public int health;
    public int magic;
    public int power;

    private int howLong;

    public CharacterModifier() {
    }

    public CharacterModifier(int h, int m, int p, int hl) {
        health = h;
        magic = m;
        power = p;
        howLong = hl;
    }

    /**
     * Apply the changes of this modifier to c, but only if howLong is not zero
     * 
     * @param c
     * @return true if howLong is greater than 0, false otherwise
     */
    public boolean applyTo(Character c) {
        if (howLong > 0) {
            c.health = health;
            c.magic = magic;
            c.power = power;
            return true;
        } else {
            return false;
        }

    }

    public int howLong() {
        return howLong;
    }

    @Override
    public void marshal(MarshallingContext c) {
        c.write("health", this.health);
        c.write("magic", this.magic);
        c.write("power", this.power);
        c.write("howLong", this.howLong);

    }

    @Override
    public void unmarshal(MarshallingContext c) {
        this.health = c.readInt("health");
        this.magic = c.readInt("magic");
        this.power = c.readInt("power");
        this.howLong = c.readInt("howLong");

    }
}
