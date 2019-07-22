package de.unisaar.faphack.model;

import java.util.List;
import java.util.Set;

import de.unisaar.faphack.model.effects.MultiplicativeEffect;
import de.unisaar.faphack.model.map.Tile;

/**
 * @author
 *
 */
public class Character implements Storable, TraitedTileOccupier {

  /**
   * I'm currently on this level
   */
  private int level = 0;

  /**
   * The position of the character.
   */
  protected Tile tile;
  /**
   * The characters inventory. The amount of items in the inventory is limited by
   * the maxWeight value of a character.
   */
  protected List<Wearable> items;

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
  protected List<Wearable> armor;

  /**
   * The maximal amount of weight the character can carry. The sum of the weight
   * of all items in the character's inventory plus the armor must not exceed this
   * value.
   */
  protected int maxWeight;

  /**
   * All effects that currently apply on the character, for example damage or heal
   * over time
   */
  protected Set<CharacterModifier> activeEffects;

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
    // TODO: FILL THIS
  }

  /**
   * Pick up the given Wearable. Returns true if the action is possible.
   *
   * @param what
   * @return boolean
   */
  public boolean pickUp(Wearable what) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * @return void
   */
  public void interact() {
    // TODO Auto-generated method stub
  }

  public Item activeWeapon() {
    return activeWeapon;
  }

  public Tile getTile() {
    return tile;
  }

  public int hasForce() {
    return power;
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

  }

  /**
   * Apply the effects of, e.g., a poisoning, eating something, etc.
   */
  public void applyItem(CharacterModifier eff) {
  }

  @Override
  public String getTrait() { return (health == 0 ? "DEAD_" : "") + role; }

  @Override
  public void marshal(MarshallingContext c) {
    // TODO fill this
  }

  @Override
  public void unmarshal(MarshallingContext c) {
    // TODO fill this
  }

}
