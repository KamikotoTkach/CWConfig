package tkachgeek.yaml.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bukkit.Location;

import java.io.IOException;

public class LocationSerializer extends JsonSerializer<Location> {
  @Override
  public void serialize(Location value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartObject();
    gen.writeNumberField("x", value.getX());
    gen.writeNumberField("y", value.getY());
    gen.writeNumberField("z", value.getZ());
    gen.writeNumberField("p", value.getPitch());
    gen.writeNumberField("a", value.getYaw());
    gen.writeStringField("w", value.getWorld().getName());
    gen.writeEndObject();
  }
}
