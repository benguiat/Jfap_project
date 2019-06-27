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
public class Tile {
    protected int x;
    protected int y;
    protected Room room;
    protected ArrayList<Items> items;
    
    public Tile (int x, int y, Room room, ArrayList<Items> items) {
        this.x = x;
        this.y = y;
        this.room = room;
        this.items = items;
    }
}
