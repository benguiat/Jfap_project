package de.unisaar.faphack.model;

import static de.unisaar.faphack.model.TestUtils.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import de.unisaar.faphack.dirtyhacks.StorableRegistrator;

class LoadTest {

  @Test
  void saveGame() {
    Game game = createGame();
    File f = getTestResourceFile("", "game.json");
    StorableFactory fact = new StorableFactory();
    StorableRegistrator.registerStorables(fact);
    MarshallingContext mc = new JsonMarshallingContext(f, fact);
    mc.save(game);
    assertTrue(f.canRead());

  }


  @Test
  void loadSword() throws IOException, ParseException {
    File f = getTestResourceFile("", "sword.json");
    StorableFactory fact = new StorableFactory();
    StorableRegistrator.registerStorables(fact);
    MarshallingContext mc = new JsonMarshallingContext(f, fact);
    Item sword = (Item)mc.read();
    assertNotNull(sword);
    File f2 = getTestResourceFile("", "sword_out.json");
    mc = new JsonMarshallingContext(f2, fact);
    mc.save(sword);
    JSONParser parser = new JSONParser();
    JSONObject orig, saved;
    Reader reader = new FileReader(f);
    orig = (JSONObject) parser.parse(reader);
    reader.close();
    reader = new FileReader(f2);
    saved = (JSONObject) parser.parse(reader);
    reader.close();
    assertEquals(orig, saved);
  }
}
