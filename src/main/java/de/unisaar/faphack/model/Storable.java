package de.unisaar.faphack.model;

public interface Storable {
  public void marshal(MarshallingContext c);
  
  public void unmarshal(MarshallingContext c);
}
