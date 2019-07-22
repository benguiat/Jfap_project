package de.unisaar.faphack.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class StorableFactory {
  public static final Logger logger =
      LoggerFactory.getLogger(StorableFactory.class);

  private Map<String, Class<? extends Storable>> registry = new HashMap<>();

  public static String getClassName(Class<? extends Storable> c) {
    String name = c.getSimpleName();
    return name;
  }

  /**
   * Register a subclass of Storable, so it can be used as prototype. Every
   * class registered here must have a public default constructor.
   */
  public void register(Class<? extends Storable> c) {
    registry.put(getClassName(c), c);
  }

  public Storable newInstance(String clazz) {
    Class<? extends Storable> cl = registry.get(clazz);
    try {
      return cl.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e) {
      logger.error("Requesting unregistered class: {}", clazz);
    }
    return null;
  }
}
