package de.unisaar.faphack.dirtyhacks;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unisaar.faphack.model.Storable;
import de.unisaar.faphack.model.StorableFactory;

public class StorableRegistrator {

  public static final Logger logger =
      LoggerFactory.getLogger(StorableRegistrator.class);

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static void registerStorables(StorableFactory factory) {
    ClassFinder.findClasses((String clazz) -> {
      if (clazz.startsWith("de.unisaar.faphack")) {
        if (clazz.contains("model") && clazz.indexOf('$') < 0) {
          try {
            Class c = Class.forName(clazz);
            if ((c.getModifiers() & (Modifier.ABSTRACT | Modifier.INTERFACE))
                == 0) {
              Constructor con = c.getDeclaredConstructor();
              Object o = con.newInstance();
              if (o instanceof Storable) {
                factory.register(c);
              }
            }
          } catch (Exception e) {
            logger.warn("Not registering {}: No public default constructor?",
                clazz);
          }
        }
        return true;
      }
      return false;
    });
  }
}

