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

    /**
     * Constructs a new <tt>JsonMarshallingContext</tt>.
     *
     * @param f is the JSON file in which the objects will be written.
     * @param fact creates storable objects instances, which we will later
     * populate with the data we read from the JSON file.
     */
    public JsonMarshallingContext(File f, StorableFactory fact) {
        file = f;
        // the stack will be used to temporarily store the key-value pairs that will be written to the JSON
        stack = new ArrayDeque<>();
        factory = fact;
        writecache = new IdentityHashMap<Storable, String>();
        readcache = new HashMap<String, Storable>();

    }

    /**
     * getIdClassName creates an ID for the storable object, so that it can be
     * used as the key in the JSON file (value is the object).
     *
     * @return idName: the ID of the string will be the simple name of the
     * underlying class + a random numerical ID
     */
    private String getIdClassName(Storable s) {
        String idName = s.getClass().getSimpleName() + "@" + String.format("%06d", idGenerator);
        idGenerator++;

        return idName;
    }

    /**
     * toJson uses the marshaling methods in other classes to serialize a
     * storable object to the JSON file as a stream of bytes which contain
     * enough information to be able to re-build the object.
     *
     * @param s is the storable object
     * @return obj is the JSON object
     */
    private JSONObject toJson(Storable s) {

        // A JSONObject is an unordered collection of name/value pairs. 
        JSONObject obj = new JSONObject();

        if (s == null) {
            // empty storable should not be saved
            return null;
        } else if (this.writecache.get(s) != null) {
            // the object is loaded, so we just return it to save to file
            obj.put("id", this.writecache.get(s));
            return obj;

        } else {

            String idName = getIdClassName(s);
            // put() creates the key-value pair
            this.writecache.put(s, idName);
            // put in in an object
            obj.put("id", idName);
            // push() will populate the stack with the key-value pair (obj) 
            // so that it's retrievable with the right functions
            this.stack.push(obj);
            // the marshaling method 
            s.marshal(this);
            // now we need to return the object to save to file
            obj = this.stack.pop();

            return obj;
        }
    }

    /**
     * fromJson uses the unmarshaling methods in other classes to deserialize
     * the JSON encoded contents to Java objects content tree.
     *
     * @return s is the storable object.
     */
    private Storable fromJson(JSONObject object) {
        // Create a new instance of s.
        Storable s = null;

        if (object != null) {
            // The ID of the object is the key value in the JSON.
            String id = (String) object.get("id");

            if (this.readcache.get(id) != null) {
                // if the object is already loaded
                s = this.readcache.get(id);

            } else {
                stack.push(object);
                // the simple name of the class is the first part of the ID
                String className = id.split("@")[0];
                // create a new instance of the object with the name we fetched
                s = factory.newInstance(className);
                // load the ID and the (for now, empty) object
                this.readcache.put(id, s);
                // convert the encoded JSON information to the content tree
                s.unmarshal(this);
                // now we're done! return the object.
                stack.pop();

                return s;
            }
        } else {
            return null;
        }
        return s;

    }

    /**
     * save() uses write() to serialize the Java object, then it writes it to a
     * file with toJSONString()
     *
     * @param s is the storable Java object.
     */
    @Override
    public void save(Storable s) {

        JSONObject obj = toJson(s);

        try (FileWriter writer = new FileWriter(this.file)) {
            writer.write(obj.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            // FileNotFoundException
            Logger.getLogger(JsonMarshallingContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * read() uses fromJson() to read a JSON file to Java objects.
     *
     * @return s is the storable Java object
     */
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

    /**
     * write() serializes objects to JSON format and is used by save().
     *
     * @param key is the key for the JSON
     * @param object is the Java object
     */
    @Override
    public void write(String key, Storable object) {

        if (this.stack.size() > 0) {
            // clear the stack from first object
            JSONObject obj = this.stack.pop();
            // convert the following object to JSON
            JSONObject obj2 = toJson(object);
            obj.put(key, obj2);
            this.stack.push(obj);

        }
    }

    /**
     * This read() method is used when the JSON value is a String. //TODO Sure?
     * There's a readString below
     *
     * @param <T> // TODO what is this????
     * @param key is the JSON key
     * @return
     */
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

    /**
     * This write() method is used when the Java Object is a String.
     *
     * @param key is the JSON key
     * @param object is the String
     */
    @Override
    public void write(String key, int object) {

        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            obj.put(key, object);
            this.stack.push(obj);

        }

    }

    /**
     * This read() method is used when the JSON value is an integer.
     *
     * @param key is the JSON key
     * @return the Java object
     */
    @Override
    public int readInt(String key) {

        int output = 0;
        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            Object object = obj.get(key);
            output = ((Long) object).intValue();

            this.stack.push(obj);

        }

        return output;
    }

    /**
     * This write() method is used to write a float number(double) to the JSON.
     *
     * @param key is the JSON key
     * @param object is the Java Object
     */
    @Override
    public void write(String key, double object) {

        if (this.stack.size() > 0) {
            JSONObject obj = this.stack.pop();
            obj.put(key, object);
            this.stack.push(obj);
        }

    }

    /**
     * This read() method is used when the JSON value is a float number(double).
     *
     * @param key is the JSON key
     * @return
     */
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

    /**
     * This write() method is used if the Java Object is a String.
     *
     * @param key is the JSON key
     * @param object is a string.
     */
    @Override
    public void write(String key, String object) {

        if (this.stack.size() > 0) {
            JSONObject obj = this.stack.pop();
            obj.put(key, object);
            this.stack.push(obj);
        }

    }

    /**
     * This read() method is used when the JSON object is a string.
     *
     * @param key is the JSON key
     * @return a string
     */
    @Override
    public String readString(String key) {
        String output = null;
        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            Object object = obj.get(key);
            output = (String) object;

            this.stack.push(obj);

        }

        return output;
    }

    /**
     * Write a Collection (Array) to a String to serialize into the JSON.
     *
     * @param key is the JSON key
     * @param coll is the ArrayList
     */
    @Override
    public void write(String key, Collection<? extends Storable> coll) {

        if (coll == null) {

        } else {

            if (this.stack.size() > 0) {
                JSONObject obj = this.stack.pop();
                // create a JSON Array to store the Array Object
                JSONArray arr = new JSONArray();
                // add every element of the Array to the JSON array
                for (Storable item : coll) {
                    // a. convert 
                    JSONObject obj2 = toJson(item);
                    // b. add to JSON Array
                    arr.add(obj2);

                }
                // push JSON array to stack, you know the rest
                obj.put(key, arr);
                this.stack.push(obj);
            }
        }
    }

    /**
     * This function is used in readAll() to convert an item of a JSON Array to
     * a Java Object. You'll see why.
     *
     * @param <T>
     * @param item is the item in JSON array
     * @return
     */
    private <T extends Storable> T item2Storable(Object item) {
        JSONObject obj = new JSONObject();
        obj.put("item", item);
        stack.push(obj);

        T storable = (T) read("item");

        stack.pop();

        return storable;
    }

    /**
     * This read() method will read a JSON Array to a Java Array, converting
     * every item in the array to a Java Object (or whatever it needs to be).
     *
     * @param key is the JSON key
     * @param coll is the Java Array
     */
    @Override
    public void readAll(String key, Collection<? extends Storable> coll) {

        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            
            Object object = obj.get(key);
            Collection<JSONObject> objectCollection = (Collection<JSONObject>) object;

            // for every item in the array, use item2Storable to make it an Object
            for (Object item : objectCollection) {

                coll.add(item2Storable(item));
            }

            this.stack.push(obj);

        }

    }

    /**
     * This write() method is responsible for writing the Tiles in the JSON
     * file. The tiles are a vector.
     *
     * @param key is the JSON key.
     * @param coll is the Tile collection.
     */
    @Override
    public void write(String key, Tile[][] coll) {

        if (coll == null) {

        } else {

            if (this.stack.size() > 0) {
                JSONObject obj = this.stack.pop();

                JSONArray arr = new JSONArray();
                
                // For every tile in the collection...
                for (Tile[] tile : coll) {
                    JSONArray arr2 = new JSONArray();
                    // ...add the tile to the JSON Array
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

    /**
     * The read() method to read the entire board, which is composed of Tiles.
     *
     * @param key is JSON key
     * @return the Board, hopefully
     */
    @Override
    public Tile[][] readBoard(String key) {
        Tile[][] tileBoard = new Tile[0][0];

        if (this.stack.size() > 0) {

            JSONObject obj = this.stack.pop();
            Object object = obj.get(key);
            JSONArray arr = (JSONArray) object;
            
            // Make an empty lsit of tiles
            List<Tile[]> tiles = new ArrayList<>();

            // Make an empty list for a row.
            for (Object item : arr) {
                List<Tile> tileRow = new ArrayList<>();
                JSONArray itemArray = (JSONArray) item;
                
                // Add the tile to the row.
                for (Object i : itemArray) {
                    Storable s = fromJson((JSONObject) i);
                    tileRow.add((Tile) s);
                }
                
                // Add the row to the Board
                tiles.add((Tile[]) tileRow.toArray(new Tile[0]));
            }
            
            // Stack the board, stack it good
            tileBoard = (Tile[][]) tiles.toArray(new Tile[0][0]);
            stack.push(obj);
        }

        return tileBoard;
    }

}
