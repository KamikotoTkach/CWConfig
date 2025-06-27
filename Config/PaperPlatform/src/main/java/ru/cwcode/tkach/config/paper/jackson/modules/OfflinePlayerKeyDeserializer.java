package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.UUID;

public class OfflinePlayerKeyDeserializer extends KeyDeserializer {
  @Override
  public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
    return Bukkit.getOfflinePlayer(UUID.fromString(key));
  }
}
