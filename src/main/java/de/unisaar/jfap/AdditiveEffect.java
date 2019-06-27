
package de.unisaar.jfap;

/**
 *
 * @author Dunfield
 */
public class AdditiveEffect {
    protected int health = 0;
    protected int magic = 0;
    protected int howLong = 1;
    
    public AdditiveEffect (int health, int magic, int howLong) {
        this.health = health;
        this.magic = magic;
        this.howLong = howLong;
    }
    
}
