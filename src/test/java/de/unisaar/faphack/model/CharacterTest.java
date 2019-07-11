package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.FloorTile;
import de.unisaar.faphack.model.map.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

  @Test
  void moveTo() {
    Character testObject = new Character();
    Tile w = new FloorTile();
    testObject.move(w);
    assertEquals(w, testObject.getTile());
  }

}
