package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.ModifyingEffect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unisaar.faphack.model.effects.MultiplicativeEffect;
import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;

/**
 * @author
 *
 */
public class Character extends AbstractObservable<TraitedTileOccupier>
        implements Storable, TraitedTileOccupier {

    /**
     * I'm currently on this level
     */
    private int level = 0;

    /**
     * The position of the character.
     */
    protected Tile tile;
    /**
     * The characters inventory. The amount of items in the inventory is limited
     * by the maxWeight value of a character.
     */
    protected List<Wearable> items = new ArrayList<>();

    /**
     * The base health of the character, which can be modified by Modifiers.
     *
     * If health is zero, this character is dead!
     */
    int health = 100;

    /**
     * The base magic of the character, which can be modified by Modifiers.
     */
    int magic = 0;

    /**
     * The base power of the character, which can be modified by Modifiers.
     */
    int power = 0;

    /**
     * This models the character's trait, i.e., how effective are the different
     * skills of the character.
     */
    protected MultiplicativeEffect skills;

    /**
     * This might be shield / bodyarmor / etc.
     */
    protected List<Armor> armor = new ArrayList<>();

    /**
     * The maximal amount of weight the character can carry. The sum of the
     * weight of all items in the character's inventory plus the armor must not
     * exceed this value.
     */
    protected int maxWeight;

    /**
     * The currentWeight is the combined weights of armor, weapon and inventory
     */
    private int currentWeight = 0;

    /**
     * All effects that currently apply on the character, for example damage or
     * heal over time
     */
    protected Set<CharacterModifier> activeEffects = new HashSet<>();

    /**
     * That's my name
     */
    protected String name;

    /**
     * That's my role
     */
    protected String role;

    /**
     * The currently active weapon
     */
    protected Wearable activeWeapon;

    public Character() {

    }

    /**
     * Change my position to the given Tile.
     *
     * @param destination
     * @return void
     */
    public void move(Tile destination) {
        tile = destination;
    }

    /**
     * Pick up the given Wearable. Returns true if the action is possible. The
     * character can only pickup an item if it is 1. on the same tile 2. the
     * current weight of all items the character carries + the weight of the
     * item is less then maxWeight
     *
     * @param what the item to be picked up
     * @return boolean <code>true</code> if the action was successful,
     * <code>false</code> otherwise
     */
    public boolean pickUp(Wearable what) {
        // TODO Auto-generated method stub: CHECK
        Tile CharLoc = this.tile;
        Tile ItemLoc = what.getTile();

        Double sum = 0.0;
        for (Wearable item : items) {
            sum = Double.sum(sum, item.weight);
        }
        if (this.maxWeight > sum && CharLoc == ItemLoc) {
            ItemLoc.onTile().remove(what);
            this.items.add(what);
            return true;
        } else {
            return false;

        }
    }

    /**
     * @return void
     */
    public void interact() {
        // TODO Auto-generated method stub
    }

    public Wearable activeWeapon() {
        return activeWeapon;
    }

    public Tile getTile() {
        return tile;
    }

    public Room getRoom() {
        return tile.getRoom();
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMagic() {
        return magic;
    }

    public int getPower() {
        return power;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public Wearable getActiveWeapon() {
        return activeWeapon;
    }

    public int getWeight() {
        // TODO: implement
        return 0;
    }

    public int levelDown() {
        return ++level;
    }

    public int levelUp() {
        return --level;
    }

    /**
     * Apply the effects of an attack, taking into account the armor
     */
    public void applyAttack(CharacterModifier eff) {
        /*
     * Example of an attack - an adversary uses his weapon (different dimensions,
     * like affecting health, armor, magic ability, and how long the effect
     * persists)
     *
     * - several factors modulate the outcome of this effect: current health
     * stamina, quality of different armors, possibly even in the different
     * dimensions.
         */
        Character affected = this;

        // turns is for how many turns will the effects occur
        int turns = eff.howLong();

        while (turns > 0) {
            // implement effects: health, magic, power

            if (affected.armor.isEmpty()) {
                affected.health += eff.health;
                affected.magic += eff.magic;
                affected.power += eff.power;
            } else {
                // for every armor in affected.armor we gotta check damage

                for (Armor arm : armor) {

                    ModifyingEffect armCm = arm.getModifyingEffect();
                    CharacterModifier newEff = armCm.apply(eff);

                    //double armPower = armCm.power;
                    affected.health += newEff.health;
                    affected.magic += newEff.magic;
                    affected.power += newEff.power;

                }
            }

            turns -= 1;
        }

    }

    /**
     * Apply the effects of, e.g., a poisoning, eating something, etc.
     */
    public void applyItem(CharacterModifier eff) {

        // turns is for how many turns will the effects occur
        int turns = eff.howLong();

        while (turns > 0) {
            // implement effects: health, magic, power
            Character affected = this;
            affected.health += eff.health;
            affected.magic += eff.magic;
            affected.power += eff.power;

            turns -= 1;
        }

    }

    /**
     * removes the given Item from the characters inventory
     *
     * @param item the item to be removed
     * @return <code>true</code> if the action was successful,
     * <code>false</code> otherwise
     */
    public boolean dropItem(Wearable item) {
        // TODO please implement me!
        return false;
    }

    /**
     * Equips the given Wearable as active Weapon or armor depending
     *
     * @param wearable the item to be equipped
     * @return <code>true</code> the action was successful, <code>false</code>
     * otherwise
     */
    public boolean equipItem(Wearable wearable) {
        // TODO please implement me!
        return false;
    }

    @Override
    public String getTrait() {
        return (health == 0 ? "DEAD_" : "") + role;
    }

    @Override
    public void marshal(MarshallingContext c) {
        // TODO please implement me!
    }

    @Override
    public void unmarshal(MarshallingContext c) {
        // TODO please implement me!
    }

    public void rest() {
        this.power += 5;
    }
}
