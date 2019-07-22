package de.unisaar.faphack.model;

public interface Observer<T> {

  /**
   *  If Observable changed, it has to call the update function for all
   *  Observers
   */
  public void update(T observable);
}
