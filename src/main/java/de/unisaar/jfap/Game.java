/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unisaar.jfap;

import java.util.ArrayList;

/**
 * @author Dunfield
 */
public class Game {

  private final World world;

  public Game(World world) {
    this.world = new World();

  }

  public boolean move(Character character, Tile tile) {

    return true;
  }

  public ArrayList<Items> listItems(Character character, Tile tile) {

    ArrayList<Items> list = new ArrayList<Items>();

    return list;
  }

  public boolean pickUp(Character character, Items item) {

    return true;
  }


}

