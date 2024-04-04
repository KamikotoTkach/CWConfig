package ru.cwcode.tkach.config.paper.jackson.modules;

import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.core.JsonGenerator;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.JsonSerializer;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.SerializerProvider;
import org.bukkit.Location;

import java.io.IOException;

public class LocationSerializer extends JsonSerializer<Location> {
  @Override
  public void serialize(Location value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    String builder =
       value.getWorld().getName() + ", " +
          value.getX() + " " +
          value.getY() + " " +
          value.getZ() + " " +
          value.getPitch() + " " +
          value.getYaw();
    
    gen.writeString(builder);
  }
}