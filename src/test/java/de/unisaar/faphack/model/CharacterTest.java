package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.MoveEffect;
import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

  @Test
  void moveTo() {
    Character testObject = new Character();
    Room room = TestUtils.createSimpleRoom(8,8,null);
    Tile[][] tiles = room.getTiles();
    TestUtils.modifyField(testObject, false,"tile", tiles[1][1]);
    MoveEffect moveEffect = new MoveEffect(new Direction(0,1));
    moveEffect.apply(testObject);
    assertEquals(tiles[1][2], testObject.tile);
    moveEffect = new MoveEffect(new Direction(0,-1));
    moveEffect.apply(testObject);
    assertEquals(tiles[1][1], testObject.tile);
    moveEffect = new MoveEffect(new Direction(-1,0));
    moveEffect.apply(testObject);
    assertEquals(tiles[1][1], testObject.tile);
  }

}
