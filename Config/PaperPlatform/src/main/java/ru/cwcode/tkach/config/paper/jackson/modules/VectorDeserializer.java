package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.util.Vector;

import java.io.IOException;

public class VectorDeserializer extends JsonDeserializer<Vector> {
  @Override
  public Vector deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String[] cords = p.getValueAsString().split(" ");
    
    double x = Double.parseDouble(cords[0]);
    double y = Double.parseDouble(cords[1]);
    double z = Double.parseDouble(cords[2]);
    
    return new Vector(x, y, z);
  }
}