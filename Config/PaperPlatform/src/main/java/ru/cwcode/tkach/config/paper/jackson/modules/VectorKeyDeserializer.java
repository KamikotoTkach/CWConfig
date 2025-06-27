package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.bukkit.util.Vector;

public class VectorKeyDeserializer extends KeyDeserializer {
  @Override
  public Object deserializeKey(String key, DeserializationContext ctxt) {
    String[] cords = key.split(" ");
    
    double x = Double.parseDouble(cords[0]);
    double y = Double.parseDouble(cords[1]);
    double z = Double.parseDouble(cords[2]);
    
    return new Vector(x, y, z);
  }
}
