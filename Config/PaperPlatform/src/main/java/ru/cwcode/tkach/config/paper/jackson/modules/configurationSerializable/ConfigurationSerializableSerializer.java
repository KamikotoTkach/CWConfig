package ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

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
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    JavaType mapType = visitor.getProvider()
                              .getTypeFactory()
                              .constructMapType(Map.class, String.class, Object.class);
    
    visitor.expectMapFormat(mapType);
  }
}
