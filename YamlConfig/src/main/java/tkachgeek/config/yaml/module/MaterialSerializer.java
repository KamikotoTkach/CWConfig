package tkachgeek.config.yaml.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bukkit.Material;

import java.io.IOException;

public class MaterialSerializer extends JsonSerializer<Material> {
  @Override
  public void serialize(Material value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.toString());
  }
}
