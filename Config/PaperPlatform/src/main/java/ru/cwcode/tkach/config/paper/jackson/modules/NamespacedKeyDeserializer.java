package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.util.UUID;

public class NamespacedKeyDeserializer extends JsonDeserializer<NamespacedKey> {
  @Override
  public NamespacedKey deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return NamespacedKey.fromString(p.getValueAsString());
  }
}