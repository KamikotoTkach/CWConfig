package ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.core.JsonGenerator;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.JsonSerializer;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class ConfigurationSerializableSerializer<T extends ConfigurationSerializable> extends JsonSerializer<T> {
  
  @Override
  public void serialize(ConfigurationSerializable value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeObject(preprocessMap(value.serialize()));
  }
  
  protected Map<String, Object> preprocessMap(Map<String, Object> immutableMap) {
    return immutableMap;
  }
}
