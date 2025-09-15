package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.io.IOException;
import java.util.UUID;

public class NamespacedKeyKeyDeserializer extends KeyDeserializer {
  @Override
  public Object deserializeKey(String key, DeserializationContext ctxt) {
    return NamespacedKey.fromString(key);
  }
}
