/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unisaar.jfap;

/**
 * @author Dunfield
 */
public class DoorTile extends WallTile {

  private final boolean locked;
  private final Hallway hallway;
  private final Items matchingKey;

  public DoorTile(boolean locked, Hallway hallway, Items matchingKey, boolean destructable) {
    super(destructable);
    this.locked = locked;
    this.hallway = new Hallway();
    this.matchingKey = matchingKey;
  }

}
