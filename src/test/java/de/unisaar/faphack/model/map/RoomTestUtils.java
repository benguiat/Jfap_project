package de.unisaar.faphack.model.map;

public class RoomTestUtils {

  public static Room createSimpleRoom(){
    return new Room(createTiles(8,8));
  }

  private static Tile[][] createTiles(int x, int y){
    Tile[][] result = new Tile[x][y];
    for(int i = 0; i < x; i++){
      for(int j = 0; j< y; j++){
        if (i == 0 || j == 0 || i == (x-1) || j == (y-1)){
          result[i][j] = new WallTile();
        } else {
          result[i][j] = new FloorTile();
        }
      }
    }
    // place a door in the mid of the upper row
    result[0][y/2] = new DoorTile();
    return result;
  }
}
