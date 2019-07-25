package de.unisaar.faphack.model;

public class CharacterModifier implements Storable {
    // what this modifier does to the various aspects of a character

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
     */
    public boolean applyTo(Character c) {

        if (howLong > 0) {
            c.health += health;
            c.magic += magic;
            c.power += power;
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
//    super.marshal(c);
        c.write("health", this.health);
        c.write("magic", this.magic);
        c.write("power", this.power);
        c.write("howLong", this.howLong);

    }

    @Override
    public void unmarshal(MarshallingContext c) {

        health = c.read("health");
        magic = c.read("magic");
        power = c.read("power");
        howLong = c.read("howLong");

    }
}
