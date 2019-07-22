package de.unisaar.faphack.model.map;

import de.unisaar.faphack.model.Character;
import org.junit.jupiter.api.Test;

import static de.unisaar.faphack.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class DoorTileTest {

  @Test
  void willTake() {
    Character character = createBaseCharacter("Foo", 3,3);
    DoorTile t1 = createDoorTile(4, true);
    DoorTile t2 = createDoorTile(2,true);
    DoorTile t3 = createDoorTile(0,false);
    DoorTile t4 = createDoorTile(0,false);
    connectTiles(t1, t2);
    connectTiles(t3,t4);
    // The door is locked and you are not strong enough to open it by force
    assertNull(t1.willTake(character));
    // The door is locked but you are strong enough to force it open
    assertEquals(t1, t2.willTake(character));
    // the door is open
    assertEquals(t4, t3.willTake(character));
  }




}