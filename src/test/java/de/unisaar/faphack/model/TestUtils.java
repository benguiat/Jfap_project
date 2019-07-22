package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestUtils {

  private static final File testResourceDir = new File("src/test/resources");

  private static void modifyField(Object modifierObject,boolean inSuperClass, String fieldname, Object value)  {
    try {
      Field f;
      if (inSuperClass){
        f = modifierObject.getClass().getSuperclass().getDeclaredField(fieldname);
      } else {
        f = modifierObject.getClass().getDeclaredField(fieldname);
      }
      f.setAccessible(true);
      f.set(modifierObject, value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String getTestResource(String subdir, String name) {
    return getTestResourceFile(subdir, name).getPath();
  }

  public static File getTestResourceFile(String subdir, String name) {
    return new File(new File(testResourceDir, subdir), name);
  }

  public static Game createGame(){
    Game game = new Game();
    World world = createWorld();
    // register the world with the game and vice versa
    modifyField(game, false, "world", world);
    modifyField(world, false, "g", game);
    return game;
  }

  public static World createWorld()  {
    World world = new World();
    List<Room> mapElements = new ArrayList<>();
    // Create the rooms
    Room room1 = createSimpleRoom(8,8,  1);
    modifyField(room1, false,"w", world);
    mapElements.add(room1);
    Room room2 = createSimpleRoom(7,7, 2);
    modifyField(room2, false,"w", world);
    mapElements.add(room2);
    Room room3 = createSimpleRoom(4, 10, 3);
    modifyField(room3, false,"w", world);
    mapElements.add(room3);
    modifyField(world, false,"mapElements", mapElements);

    // Connect Room 1 and 2 with a hallway
    DoorTile doorTile1 = (DoorTile) room1.getTiles()[0][8/2];
    DoorTile doorTile2 = (DoorTile) room2.getTiles()[0][7/2];
    connectTiles(doorTile1, doorTile2);
    // Connect Room 2 and 3 with stairs
    StairTile stairTile1 = (StairTile) room2.getTiles()[7/2][7/2];
    StairTile stairTile2 = (StairTile) room3.getTiles()[4/2][10/2];
    connectTiles(stairTile1, stairTile2);

    // add two characters to the world
    Character c1 = createBaseCharacter("Foo", 0, 10);
    Character c2 = createBaseCharacter("Bar", 0, 10);
    addCharacter(room1, 2,2,c1);
    addCharacter(room3, 1,4,c2);

    // create a bunch of items and place them in the world
    Item sword = createWearable(2, true);
    modifyField(sword, false, "character",  c1);
    modifyField(c1, false, "activeWeapon", sword );
    Item fountain = new Fixtures();
    List<Item> onTile = new ArrayList<>();
    onTile.add(fountain);
    modifyField(fountain, true, "onTile", room1.getTiles()[3][3]);
    modifyField(room1.getTiles()[3][3], false, "items", onTile);

    Wearable rottenApple = createWearable(1, false);
    placeItemsInRoom(room3,2,8, rottenApple);
    modifyField(rottenApple, true, "effect", new CharacterModifier(1, 0, 0, 1));
    return world;
  }

  public static Room createSimpleRoom(int x, int y, int roomNo){
    Room r = new Room();
    Tile[][] tiles = createTiles(x, y, r, roomNo);
    modifyField(r, false,"tiles", tiles);
    return r;
  }

  private static Tile[][] createTiles(int x, int y, Room room, int roomNo){
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
    if (roomNo < 3) result[0][y/2] = new DoorTile(0, y/2 , room);
    // place a stair tile right in the center of the room
    if (roomNo > 0) result[x/2][y/2] = new StairTile(x/2, y/2, room);
    return result;
  }



  public static void addCharacter(Room room, int x, int y, Character character){
    List<Character> inhabitants = new ArrayList<>();
    inhabitants.add(character);
    modifyField(room,false, "inhabitants", inhabitants);
    placeCharacter(character,room.getTiles()[x][y]);
    //modifyField(character, false, "tile", room.getTiles()[x][y]);
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
    StairTile sr3 = (StairTile) r3.getTiles()[2][5];
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
    // there should be a fixture in room 1 on tile [3][3]
    assertEquals(Fixtures.class, r1.getTiles()[3][3].onTile().get(0).getClass());
    // there should be a Wearable  in room 3 on tile [2][8]
    assertEquals(Wearable.class, r3.getTiles()[2][8].onTile().get(0).getClass());
  }

  public static void placeItemsInRoom(Room room, int x, int y, Wearable... items) {
    List<Wearable> onTile = new ArrayList<>(Arrays.asList(items));
    for(Wearable wearable : onTile){
      modifyField(wearable, true, "onTile", room.getTiles()[x][y]);
    }
    modifyField(room.getTiles()[x][y], false, "items", onTile);
  }

  public static void placeCharacter(Character testObject, Tile tile) {
    modifyField(testObject, false, "tile", tile);
  }

  public static Wearable createWearable(int weight, boolean isWeapon) {
    Wearable item = new Wearable();
    modifyField(item, false, "weight", weight);
    modifyField(item, false, "isWeapon", isWeapon );
    return item;
  }

  public static DoorTile createDoorTile(int destructible, boolean isLocked) {
    DoorTile doorTile = new DoorTile();
    modifyField(doorTile, false, "locked", isLocked);
    modifyField(doorTile, true, "destructible", destructible);
    return doorTile;
  }

  public static WallTile createWallTile(int destructible){
    WallTile wallTile = new WallTile();
    modifyField(wallTile, false, "destructible", destructible);
    return wallTile;
  }

  public static Character createBaseCharacter(String name, int power, int maxWeight ){
    Character character = new Character();
    modifyField(character, false, "name", name);
    modifyField(character,false, "items", new ArrayList<>());
    modifyField(character, false, "maxWeight", maxWeight);
    modifyField(character, false, "power", power);
    modifyField(character, false, "role", character.WARRIOR);
    return character;
  }


  public static Hallway connectTiles(DoorTile t1, DoorTile t2) {
    Hallway connector = new Hallway();
    modifyField(connector,true,"fromTile", t1);
    modifyField(connector,true,"toTile", t2);
    modifyField(t1, false, "hallway", connector);
    TestUtils.modifyField(t2, false, "hallway", connector);
    return connector;
  }

  public static Stair connectTiles(StairTile t1, StairTile t2) {
    Stair connector = new Stair();
    modifyField(connector,true,"fromTile", t1);
    modifyField(connector,true,"toTile", t1);
    modifyField(t1, false, "stair", connector);
    TestUtils.modifyField(t2, false, "stair", connector);
    return connector;
  }

}