package de.unisaar.jfap;

import org.junit.jupiter.api.Test;
import de.unisaar.faphack.model.Character;
import de.unisaar.faphack.model.map.*;

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