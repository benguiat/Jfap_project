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
public class World {
    
    private final ArrayList<Room> mapElements;
    
    public World(){
        
        this.mapElements = new ArrayList<Room>();
    }
    
}
