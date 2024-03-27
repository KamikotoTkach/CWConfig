package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bukkit.OfflinePlayer;

import java.io.IOException;

public class OfflinePlayerSerializer extends JsonSerializer<OfflinePlayer> {
  @Override
  public void serialize(OfflinePlayer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.getUniqueId().toString());
  }
}