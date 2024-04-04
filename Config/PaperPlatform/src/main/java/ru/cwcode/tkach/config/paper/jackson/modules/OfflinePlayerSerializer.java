package ru.cwcode.tkach.config.paper.jackson.modules;

import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.core.JsonGenerator;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.JsonSerializer;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.SerializerProvider;
import org.bukkit.OfflinePlayer;

import java.io.IOException;

public class OfflinePlayerSerializer extends JsonSerializer<OfflinePlayer> {
  @Override
  public void serialize(OfflinePlayer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getUniqueId().toString());
  }
}