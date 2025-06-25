package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.io.IOException;

public class BlockVectorDeserializer extends JsonDeserializer<BlockVector> {
  @Override
  public BlockVector deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String[] cords = p.getValueAsString().split(" ");
    
    double x = Integer.parseInt(cords[0]);
    double y = Integer.parseInt(cords[1]);
    double z = Integer.parseInt(cords[2]);
    
    return new BlockVector(x, y, z);
  }
}