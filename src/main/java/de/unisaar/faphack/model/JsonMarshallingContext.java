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

    public JSONObject toJson(Storable s) {

        JSONObject obj = new JSONObject();

        String idName = getIdClassName(s);

        //CHECK IF IN WRITECACHE
        this.writecache.put(s, idName);
        obj.put("id", idName);

        stack.push(obj);
        s.marshal(this);
        stack.pop();

        return obj;
    }

    @Override
    public void save(Storable s) {

        JSONObject obj = new JSONObject();
        

        obj = toJson(s);

        try (FileWriter writer = new FileWriter(this.file)) {
            writer.write(obj.toJSONString());
        } catch (IOException ex) {
            Logger.getLogger(JsonMarshallingContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Storable read() {

        return null;

    }

    @Override
    public void write(String key, Storable object) {


        if (stack.size() > 0) {

            JSONObject obj = stack.pop();
            JSONObject obj2 = new JSONObject();
            obj2 = toJson(object);
            stack.pop();
            obj.put(key, obj2);
            stack.push(obj);

        } else {
        }

    }

    @Override
    public <T extends Storable> T read(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void write(String key, int object) {
        

        
        if (stack.size() > 0) {

            JSONObject obj = stack.pop();
            obj.put(key, object);
            stack.push(obj);

        } else {
        }
       

    }

    @Override
    public int readInt(String key) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void write(String key, double object) {
        

        
        if (stack.size() > 0) {
            JSONObject obj = stack.pop();
            obj.put(key, object);
            stack.push(obj);
        } else {
        }
        


    }

    @Override
    public double readDouble(String key) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void write(String key, String object) {
        

        
        if (stack.size() > 0) {
            JSONObject obj = stack.pop();
            obj.put(key, object);
            stack.push(obj);
        } else {
        }
       

    }

    @Override
    public String readString(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void write(String key, Collection<? extends Storable> coll) {
        


        if (stack.size() > 0) {
            JSONObject obj = stack.pop();

            JSONArray arr = new JSONArray();

            for (Storable item : coll) {
                JSONObject obj2 = toJson(item);
                arr.add(obj2);
            }

            obj.put(key, arr);
            this.stack.push(obj);
        } else {
        }

    }

    @Override
    public void readAll(String key, Collection<? extends Storable> coll) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(String key, Tile[][] coll) {
        


        if (stack.size() > 0) {
            JSONObject obj = stack.pop();

            obj.put(key, coll);
            stack.push(obj);
        } else {
        }

    }

    @Override
    public Tile[][] readBoard(String key) {
        // TODO Auto-generated method stub
        return null;
    }

}
