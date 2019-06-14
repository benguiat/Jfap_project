/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unisaar.jfap;

import java.util.ArrayList;

/**
 *
 * @author Dunfield
 */
public class Character {
    
    protected Tile tile;
    protected ArrayList<Items> items;
    protected int health;
    protected Wearable armor;
    protected Effect baseDamage;
    protected int maxWeight;

    public Character(Tile tile, ArrayList<Items> items, int health, Wearable armor, Effect baseDamage, int maxWeight) {
        this.tile = tile;
        this.items = items;
        this.health = health;
        this.armor = armor;
        this.baseDamage = baseDamage;
        this.maxWeight = maxWeight;
    }
    
    public void move(Tile destination){
        
    }
    
    public boolean pickUp(Items what){
        
        return true;
    }
    
    public void interact(){
        
    }
    
    
}
