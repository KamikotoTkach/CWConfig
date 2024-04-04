package ru.cwcode.tkach.config.paper.jackson.modules;

import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.core.JsonParser;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.DeserializationContext;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.util.UUID;

public class OfflinePlayerDeserializer extends JsonDeserializer<OfflinePlayer> {
  @Override
  public OfflinePlayer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return Bukkit.getOfflinePlayer(UUID.fromString(p.getValueAsString()));
  }
}