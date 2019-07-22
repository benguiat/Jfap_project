package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestUtils {

  private static final File testResourceDir = new File("src/test/resources");

  public static void modifyField(Object modifierdObject,boolean inSuperClass, String fieldname, Object value)  {
    try {
      Field f;
      if (inSuperClass){
        f = modifierdObject.getClass().getSuperclass().getDeclaredField(fieldname);
      } else {
        f = modifierdObject.getClass().getDeclaredField(fieldname);
      }
      f.setAccessible(true);
      f.set(modifierdObject, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String getTestResource(String subdir, String name) {
    return new File(new File(testResourceDir, subdir), name).getPath();
  }

  public static Game createGame(){
    Game game = new Game();
    World world = createWorld();
    // register the world with the game and vice versa
    modifyField(game, false, "world", world);
    modifyField(world, false, "g", game);
    return game;
  }

  private static World createWorld()  {
    World world = new World();
    List<Room> mapElements = new ArrayList<>();
    // Create the rooms
    Room room1 = createSimpleRoom(8,8, world);
    mapElements.add(room1);
    Room room2 = createSimpleRoom(7,7, world);
    mapElements.add(room2);
    Room room3 = createSimpleRoom(4, 10, world);
    mapElements.add(room3);
    modifyField(world, false,"mapElements", mapElements);

    // Connect Room 1 and 2 with a hallway
    Hallway hallway = new Hallway();
    DoorTile doorTile1 = (DoorTile) room1.getTiles()[0][8/2];
    modifyField(doorTile1, false,"hallway", hallway);
    modifyField(hallway, true,"fromTile",doorTile1 );
    DoorTile doorTile2 = (DoorTile) room2.getTiles()[0][7/2];
    modifyField(doorTile2, false,"hallway", hallway);
    modifyField(hallway, true,"toTile", doorTile2);

    // Connect Room 2 and 3 with stairs
    Stair stair = new Stair();
    StairTile stairTile1 = (StairTile) room2.getTiles()[7/2][7/2];
    modifyField(stairTile1, false, "stair", stair);
    modifyField(stair, true, "fromTile", stairTile1);
    StairTile stairTile2 = (StairTile) room3.getTiles()[4/2][10/2];
    modifyField(stairTile2, false, "stair", stair);
    modifyField(stair, true, "toTile", stairTile2);

    // add two characters to the world
    Character c1 = addCharacter(room1, 2,2,"Foo");
    Character c2 = addCharacter(room3, 1,4,"Bar");

    // create a bunch of items and place them in the world
    Item sword = new Wearable();
    modifyField(sword, false, "weight", 2 );
    modifyField(sword, false, "isWeapon", true );
    modifyField(sword, false, "character",  c1);
    modifyField(c1, false, "activeWeapon", sword );
    Item fountain = new Fixtures();
    List<Item> onTile = new ArrayList<>();
    onTile.add(fountain);
    modifyField(fountain, true, "onTile", room1.getTiles()[3][3]);
    modifyField(room1.getTiles()[3][3], false, "items", onTile);
    Item rottenApple = new Wearable();
    onTile = new ArrayList<>();
    onTile.add(rottenApple);
    modifyField(rottenApple, true, "onTile", room3.getTiles()[2][8]);
    modifyField(room3.getTiles()[2][8], false, "items", onTile);



    return world;
  }

  public static Room createSimpleRoom(int x, int y, World world){
    Room r = new Room();
    Tile[][] tiles = createTiles(x, y, r);
    modifyField(r, false,"tiles", tiles);
    modifyField(r, false,"w", world);
    return r;
  }

  private static Tile[][] createTiles(int x, int y, Room room){
    Tile[][] result = new Tile[x][y];
    for(int i = 0; i < x; i++){
      for(int j = 0; j< y; j++){
        if (i == 0 || j == 0 || i == (x-1) || j == (y-1)){
          result[i][j] = new WallTile(i, j ,room);
        } else {
          result[i][j] = new FloorTile(i, j ,room);
        }
      }
    }
    // place a door
    result[0][y/2] = new DoorTile(0, y/2 , room);
    // place a stair tile right in the center of the room
    result[x/2][y/2] = new StairTile(x/2, y/2, room);
    return result;
  }



  private static Character addCharacter(Room room, int x, int y, String name){
    Character character = new Character();
    List<Character> inhabitants = new ArrayList<>();
    inhabitants.add(character);
    modifyField(room,false, "inhabitants", inhabitants);
    modifyField(character, false, "tile", room.getTiles()[x][y]);
    modifyField(character, false, "name", name);
    return character;
  }

  @Test
  public void testCreateWorld(){
    World testObject = createWorld();
    testDefaultWordData(testObject);
  }


  /**
   * This method can be used to check whether everything in out test/default world is at its place.
   * @param testObject the world to test.
   */
  public static void testDefaultWordData(World testObject){
    assertNotNull(testObject);
    List<Room> mapElements = testObject.getMapElements();
    assertEquals(3, mapElements.size());
    // Test if the rooms are instantiated and connected correctly
    Room r1 = mapElements.get(0);
    Room r2 = mapElements.get(1);
    Room r3 = mapElements.get(2);
    // r1 should be of size [8][8], have a door at [0][4]
    assertEquals(8,r1.getTiles().length);
    assertEquals(8,r1.getTiles()[0].length);
    DoorTile dr1 = (DoorTile) r1.getTiles()[0][4];
    assertEquals(DoorTile.class, dr1.getClass());
    // r2 should be of size [7][7], have a door at [0][7/2] and stairs at [7/2][7/2]
    assertEquals(7,r2.getTiles().length);
    assertEquals(7,r2.getTiles()[0].length);
    DoorTile dr2 = (DoorTile) r2.getTiles()[0][7/2];
    StairTile sr2 = (StairTile) r2.getTiles()[7/2][7/2];
    assertEquals(DoorTile.class, dr2.getClass());
    // both doors are connected
    assertEquals(dr1.getHallway(), dr2.getHallway());
    // r3 should be of size [4][10], have a door at [0][5] and stairs at [2][5]
    assertEquals(4,r3.getTiles().length);
    assertEquals(10,r3.getTiles()[0].length);
    DoorTile dr3 = (DoorTile) r3.getTiles()[0][5];
    StairTile sr3 = (StairTile) r3.getTiles()[2][5];
    assertEquals(DoorTile.class, dr3.getClass());
    // both stairs are connected
    assertEquals(sr2.getStair(), sr3.getStair());
    // there should be a character named "Foo" in room1 carrying a sword
    Character foo = r1.getInhabitants().get(0);
    assertNotNull(foo);
    assertEquals("Foo",foo.name);
    assertNotNull(foo.activeWeapon);
    assertEquals(2,foo.activeWeapon.weight);
    // there should be a character named "Bar" in room3
    Character bar = r3.getInhabitants().get(0);
    assertNotNull(bar);
    // there should be an item in room on tile [][]
    assertEquals(Fixtures.class, r1.getTiles()[3][3].onTile().get(0).getClass());
    // there should be a fixture in room on tile [][]
    assertEquals(Wearable.class, r3.getTiles()[2][8].onTile().get(0).getClass());
  }

}