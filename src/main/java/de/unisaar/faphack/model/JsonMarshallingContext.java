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
        readcache = new HashMap<String, Storable>();

    }

    private String getIdClassName(Storable s) {

        String idName = s.getClass().getSimpleName() + "@" + String.format("%06d", idGenerator);
        idGenerator++;

        return idName;
    }

    private JSONObject toJson(Storable s) {

        JSONObject obj = new JSONObject();

        if (s == null) {
            return null;
        } else if (this.writecache.get(s) != null) {

            obj.put("id", this.writecache.get(s));
            return obj;

        } else {

            String idName = getIdClassName(s);

            this.writecache.put(s, idName);
            obj.put("id", idName);

            this.stack.push(obj);
            s.marshal(this);
            obj = this.stack.pop();

            return obj;
        }
    }

    private Storable fromJson(JSONObject object) {

        Storable s;

        if (object != null) {
            

            String id = (String) object.get("id");
            
            if (this.readcache.get(id) != null) {
                s = this.readcache.get(id);
              
            } else {
                stack.push(object);
                String className = id.split("@")[0];
                
                s = factory.newInstance(className);
         
                this.readcache.put(id, s);
                
                s.unmarshal(this);
                
                System.out.println(s);
                
                stack.pop();
                
             
                
                return s;
            }
        }
        else{
            return null;
        }
        return s;

    }

    @Override
    public void save(Storable s) {

        JSONObject obj = toJson(s);

        try (FileWriter writer = new FileWriter(this.file)) {
            writer.write(obj.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(JsonMarshallingContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Storable read() {

        JSONParser parser = new JSONParser();
        Storable s = null;

        try (FileReader reader = new FileReader(this.file);) {
            Object readFile = parser.parse(reader);
            JSONObject obj = (JSONObject) readFile;
            System.out.println("File: " + this.file);
            
            s = fromJson(obj);
            
        } catch (IOException | ParseException ex) {
            Logger.getLogger(JsonMarshallingContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return s;

    }

    @Override
    public void write(String key, Storable object) {

        if (object == null) {

        } else {

            if (this.stack.size() > 0) {

                JSONObject obj = this.stack.pop();

                JSONObject obj2 = toJson(object);
                obj.put(key, obj2);
                this.stack.push(obj);

            }
        }
    }

    @Override
    public <T extends Storable> T read(String key) {

        T output = null;

        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();

            Object object = obj.get(key);
            
            JSONObject getObject = (JSONObject) object;
  
            output = (T) fromJson(getObject);
         
            
            this.stack.push(obj);

        }

        return output;

    }

    @Override
    public void write(String key, int object) {

        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            obj.put(key, object);
            this.stack.push(obj);

        }

    }

    @Override
    public int readInt(String key) {

        int output = 0;
        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            Object object = obj.get(key);
            output = ((Long)object).intValue();

            this.stack.push(obj);

        }

        return output;
    }

    @Override
    public void write(String key, double object) {

        if (this.stack.size() > 0) {
            JSONObject obj = this.stack.pop();
            obj.put(key, object);
            this.stack.push(obj);
        }

    }

    @Override
    public double readDouble(String key) {
        double output = 0;
        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            Object object = obj.get(key);
            output = (double) object;

            this.stack.push(obj);

        }

        return output;
    }

    @Override
    public void write(String key, String object) {

        if (object != null) {
            if (this.stack.size() > 0) {
                JSONObject obj = this.stack.pop();
                obj.put(key, object);
                this.stack.push(obj);
            }
        }

    }

    @Override
    public String readString(String key) {
        String output = "";
        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            Object object = obj.get(key);
            output = (String) object;

            this.stack.push(obj);

        }

        return output;
    }

    @Override
    public void write(String key, Collection<? extends Storable> coll) {

        if (coll == null) {

        } else {

            if (this.stack.size() > 0) {
                JSONObject obj = this.stack.pop();

                JSONArray arr = new JSONArray();

                for (Storable item : coll) {

                    JSONObject obj2 = toJson(item);
                    arr.add(obj2);

                }

                obj.put(key, arr);
                this.stack.push(obj);
            }
        }
    }

    private <T extends Storable> T item2Storable(Object item){
      JSONObject obj = (JSONObject) item;
      JSONObject bigObject = new JSONObject();
      bigObject.put("item", obj);
      stack.push(bigObject);
   
      T storable = (T) read("item");
      
      stack.pop();
      
      return storable;
    }
  

    @Override
    public void readAll(String key, Collection<? extends Storable> coll) {

        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            
            Object object = obj.get(key);
            Collection<JSONObject> objectCollection = (Collection<JSONObject>) object;

            for (Object item : objectCollection) {
             

                coll.add(item2Storable(item));
            }

        
            this.stack.push(obj);

        }

    }

    @Override
    public void write(String key, Tile[][] coll) {

        if (coll == null) {

        } else {

            if (this.stack.size() > 0) {
                JSONObject obj = this.stack.pop(); //

                JSONArray arr = new JSONArray(); //

                for (Tile[] tile : coll) {
                    JSONArray arr2 = new JSONArray();
                    for (Tile t : tile) {
                        JSONObject obj2 = toJson(t);
                        arr2.add(obj2);
                    }
                    arr.add(arr2);
                }

                obj.put(key, arr);
                this.stack.push(obj);
            }
        }
    }

    @Override
    public Tile[][] readBoard(String key) {
        Tile[][] tileBoard = null;

        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            Object object = obj.get(key);

            JSONArray arr = (JSONArray) object;

            Collection<Tile[]> tiles = new ArrayList<>();

            for (Object tile : arr) {

                Collection<Tile> tileIndividual = new ArrayList<>();
                JSONArray arr2 = (JSONArray) arr;
                for (Object item : arr2) {
                    JSONObject t = (JSONObject) item;
                    Storable s = fromJson(t);

                    tileIndividual.add((Tile) s);
                    Tile[] tileOne = tileIndividual.toArray(new Tile[0]);
                    tiles.add(tileOne);

                }
                tileBoard = tiles.toArray(new Tile[0][0]);

            }

            this.stack.push(obj);

        }

        return tileBoard;
    }

}
