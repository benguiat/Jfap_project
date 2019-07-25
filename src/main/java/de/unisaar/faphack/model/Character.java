package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.ModifyingEffect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unisaar.faphack.model.effects.MultiplicativeEffect;
import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;
import java.util.Iterator;

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
    if (tile != null) {
      Room current = tile.getRoom();
      if (destination.getRoom() != current) {
        current.getInhabitants().remove(this);
        destination.getRoom().getInhabitants().add(this);
      }
    } else {
      destination.getRoom().getInhabitants().add(this);
    }
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

        Tile CharLoc = this.tile;
        Tile ItemLoc = what.getTile();

        // calculate the weight that the char is currently carrying
        Double sum = 0.0;
        for (Wearable item : items) {
            sum = Double.sum(sum, item.weight);
        }

        if (this.maxWeight > sum && CharLoc == ItemLoc) {
            ItemLoc.onTile().remove(what);
            this.items.add(what);
            // we have to declare that the item's char is Character
            what.character = this;
            // we have to remove any trace of Tile from the Item
            what.onTile = null;

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

//        while (turns > 0) {
        // implement effects: health, magic, power
        if (affected.armor.isEmpty()) {
            affected.health += eff.health;
            affected.magic += eff.magic;
            affected.power += eff.power;
        } else {
            // for every armor in affected.armor we gotta check damage

            CharacterModifier newEff = eff;

            for (Armor arm : armor) {
                ModifyingEffect armCm = arm.getModifyingEffect();
                newEff = armCm.apply(newEff);

            }

            affected.health += newEff.health;
            affected.magic += newEff.magic;
            affected.power += newEff.power;

        }

//            turns -= 1;
//        }
    }

    /**
     * Apply the effects of, e.g., a poisoning, eating something, etc.
     */
    public void applyItem(CharacterModifier eff) {

        // turns is for how many turns will the effects occur
//        int turns = eff.howLong();
//        while (turns > 0) {
        // implement effects: health, magic, power
        Character affected = this;
        affected.health += eff.health;
        affected.magic += eff.magic;
        affected.power += eff.power;

//            turns -= 1;
//        }
    }

    /**
     * removes the given Item from the characters inventory
     *
     * @param item the item to be removed
     * @return <code>true</code> if the action was successful,
     * <code>false</code> otherwise
     */
    public boolean dropItem(Wearable item) {
        // drop should only work for items that exist in character
        if (this.items.contains(item)) {
            // place item in the Tile that the Char stands on
            Tile CharLoc = this.tile;
            CharLoc.onTile().add(item);

            if (item.isWeapon) {
                //if the item is the active weapon, make love not war
                this.activeWeapon = null;
            } else if (this.armor.contains(item)) {
                // if the item is armor, you also remove it from armor list
                this.armor.remove(item);
            }
            // in any case, you have to remove it from the items list
            this.items.remove(item);

            return true;
        } else {
            // this means that there's no such item in items list
            return false;
        }

    }

    /**
     * Equips the given Wearable as active Weapon or armor depending
     *
     * @param wearable the item to be equipped
     * @return <code>true</code> the action was successful, <code>false</code>
     * otherwise
     */
    public boolean equipItem(Wearable wearable) {

        // First thing first, the wearable has to be in the inventory
        if (this.items.contains(wearable)) {
            if (wearable.isWeapon) {
                // if the wearable is a weapon, Annie get your gun
                this.activeWeapon = wearable;
            } else if (wearable instanceof Armor) {
                // if the wearable is an armor, put it in the armor list
                this.armor.add((Armor) wearable);
            }
            return true;
        } else {
            // then you tried to put on sth you don't own you dirty thief
            return false;
        }
    }




    @Override
    public String getTrait() {
        return (health == 0 ? "DEAD_" : "") + role;
    }

    @Override
    public void marshal(MarshallingContext c) {
        // TODO please implement me!
        c.write("name", this.name);
        c.write("role", this.role);
        c.write("activeEffects", this.activeEffects);
        c.write("activeWeapon", this.activeWeapon);
        c.write("armor",this.armor);
        c.write("currentWeight", this.currentWeight);
        c.write("health", this.health);
        c.write("items", this.items);
        c.write("level", this.level);
        c.write("magic", this.magic);
        c.write("maxWeight", this.maxWeight);
        c.write("power", this.power);
        c.write("tile", this.tile);
        c.write("skills", this.skills);
    }

    @Override
    public void unmarshal(MarshallingContext c) {
        this.name = c.readString("name");
        this.role = c.readString("role");
        Set<CharacterModifier> activeEff = new HashSet<>();
        c.readAll("activeEffects", activeEff);
        this.activeEffects = activeEff;
        this.activeWeapon = c.read("activeWeapon");
        List<Armor> arm = new ArrayList<>();
        c.readAll("armor", arm);
        this.armor = arm;
        this.currentWeight = c.readInt("currentWeight");
        this.health = c.readInt("healsth");
        List<Wearable> it = new ArrayList<>();
        c.readAll("items",it);
        this.items = it;
        this.level = c.readInt("level");
        this.magic = c.readInt("magic");
        this.maxWeight = c.readInt("maxWeight");
        this.power = c.readInt("power");
        this.tile = c.read("tile");
        this.skills = c.read("skills");
    }

    public void rest() {
        this.power += 5;
    }
}
