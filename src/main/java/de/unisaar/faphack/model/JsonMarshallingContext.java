package de.unisaar.faphack.model;

import de.unisaar.faphack.model.map.Tile;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonMarshallingContext implements MarshallingContext {

  private final File file;

  private static StorableFactory factory;

  private IdentityHashMap<Object, String> writecache;

  private Deque<JSONObject> stack;

  private Map<String, Storable> readcache;

  private int idGenerator = 1;

  public JsonMarshallingContext(File f, StorableFactory fact) {
    file = f;
  }

  @Override
  public void save(Storable s) {
//    JSONObject obj = new JSONObject();
//    
//    
//    
//    obj.put("game", s.toString());
//    
//    
//    try (FileWriter f = new FileWriter(file)) {
// 
//            f.write(obj.toJSONString());
//            f.flush();
// 
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    
    
  }

  @Override
    public Storable read() {

    JSONParser parser = new JSONParser();
    
    Storable request = null;

        try (Reader reader = new FileReader(file)) {

            JSONObject obj = (JSONObject) parser.parse(reader);
            
            
            System.out.println(obj);
            
            

//            String name = (String) jsonObject.get("name");
//            System.out.println(name);
//
//            long age = (Long) jsonObject.get("age");
//            System.out.println(age);
//
//            // loop array
//            JSONArray msg = (JSONArray) jsonObject.get("messages");
//            Iterator<String> iterator = msg.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
      return request;

    }

    

  @Override
  public void write(String key, Storable object) {
    // TODO Auto-generated method stub

  }

  @Override
  public <T extends Storable> T read(String key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void write(String key, int object) {
    // TODO Auto-generated method stub

  }

  @Override
  public int readInt(String key) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void write(String key, double object) {
    // TODO Auto-generated method stub

  }

  @Override
  public double readDouble(String key) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void write(String key, String object) {
    // TODO Auto-generated method stub

  }

  @Override
  public String readString(String key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void write(String key, Collection<? extends Storable> coll) {
    // TODO Auto-generated method stub

  }

  @Override
  public void readAll(String key, Collection<? extends Storable> coll) {
    // TODO Auto-generated method stub

  }

  @Override
  public void write(String key, Tile[][] coll) {
    // TODO Auto-generated method stub

  }

  @Override
  public Tile[][] readBoard(String key) {
    // TODO Auto-generated method stub
    return null;
  }

}
