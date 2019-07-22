package de.unisaar.faphack.model;

import de.unisaar.faphack.model.effects.MoveEffect;
import de.unisaar.faphack.model.map.Room;
import de.unisaar.faphack.model.map.Tile;
import org.junit.jupiter.api.Test;

import static de.unisaar.faphack.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

  @Test
  void moveTo() {
    Room room = TestUtils.createSimpleRoom(8,8,1);
    Tile[][] tiles = room.getTiles();
    Character testObject = createBaseCharacter("Foo", 2, 2);
    addCharacter(room, 1, 1, testObject);
    MoveEffect moveEffect = new MoveEffect(new Direction(0,1));
    moveEffect.apply(testObject);
    assertEquals(tiles[1][2], testObject.tile);
    moveEffect = new MoveEffect(new Direction(0,-1));
    moveEffect.apply(testObject);
    assertEquals(tiles[1][1], testObject.tile);
    moveEffect = new MoveEffect(new Direction(-1,0));
    moveEffect.apply(testObject);
    assertEquals(tiles[1][1], testObject.tile);
    assertThrows(IllegalArgumentException.class, () ->  new MoveEffect(new Direction(0, 2)));
  }

  @Test
  void pickUp() {
    Room room = TestUtils.createSimpleRoom(8,8,1);
    Tile[][] tiles = room.getTiles();
    Character testObject = createBaseCharacter("Foo", 2, 2);
    addCharacter(room, 1, 1, testObject);
    Wearable item1 = createWearable(2, false);
    Wearable item2 = createWearable(2, false);
    placeItemsInRoom(room, 1,2,item1, item2);
    // the item is not on the field the character is
    assertFalse(testObject.pickUp(item1));
    // Wearable at [1][2]: picking this up should work
    placeCharacter(testObject, tiles[1][2]);
    assertTrue(testObject.pickUp(item1));
    // the item should have been removed from the tile and moved into the inventory of the character
    assertTrue(testObject.items.contains(item1));
    assertTrue(!room.getTiles()[1][2].onTile().contains(item1));
    // the item is too heavy for the character
    assertFalse(testObject.pickUp(item2));
  }


  @Test
  void applyAttack() {
    fail("Please implement me");
  }

  @Test
  void applyItem() {
    fail("Please implement me");
  }
}
