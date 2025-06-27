package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.bukkit.util.BlockVector;

import java.io.IOException;

public class BlockVectorKeyDeserializer extends KeyDeserializer {
  @Override
  public BlockVector deserializeKey(String key, DeserializationContext ctxt) throws IOException {
    String[] parts = key.split(" ");
    if (parts.length != 3) throw new IOException("Invalid BlockVector key: " + key);
    double x = Integer.parseInt(parts[0]);
    double y = Integer.parseInt(parts[1]);
    double z = Integer.parseInt(parts[2]);
    return new BlockVector(x, y, z);
  }
}
