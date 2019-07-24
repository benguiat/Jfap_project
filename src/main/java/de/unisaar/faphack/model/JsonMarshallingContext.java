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

  private IdentityHashMap<Storable, String> writecache;

  private Deque<JSONObject> stack;

  private Map<String, Storable> readcache;

  private int idGenerator = 1;

  public JsonMarshallingContext(File f, StorableFactory fact) {
    file = f;
    stack = new ArrayDeque<>();
    factory = fact;
    writecache = new IdentityHashMap<Storable, String>();
    readcache = new IdentityHashMap<String, Storable>();
 
  }
  
  private JSONObject toJson(Storable s){
      
      JSONObject obj = new JSONObject();
      
      obj.put("id", s + "@" + idGenerator);
      
      idGenerator += 1;
      
      return obj;
  } 


  @Override
  public void save(Storable s) {

      JSONObject obj = toJson(s);
      
      stack.push(obj);
   
      s.marshal(this);
      
      System.out.println(readcache.get("game"));
      
      stack.pop();
      
      
    
  }

  @Override
    public Storable read() {  
        
    return null;

    }
    

  @Override
  public void write(String key, Storable object) {
    readcache.put(key, object);
    writecache.put(object, key);

  }

  @Override
  public <T extends Storable> T read(String key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void write(String key, int object) {

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
