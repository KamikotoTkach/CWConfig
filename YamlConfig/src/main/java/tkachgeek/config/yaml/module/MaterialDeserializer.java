package tkachgeek.config.yaml.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bukkit.Material;

import java.io.IOException;

public class MaterialDeserializer extends JsonDeserializer<Material> {
  @Override
  public Material deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return Material.valueOf(p.getValueAsString());
  }
}
