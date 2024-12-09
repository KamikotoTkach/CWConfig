package ru.cwcode.tkach.config.paper.jackson.modules.configurationSerializable;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.core.JsonParser;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.DeserializationContext;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.JsonDeserializer;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.JsonNode;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConfigurationSerializableDeserializer<T extends ConfigurationSerializable> extends JsonDeserializer<T> {
  final Class<T> targetClass;
  
  public ConfigurationSerializableDeserializer(Class<T> targetClass) {
    this.targetClass = targetClass;
  }
  
  @Override
  public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectMapper mapper = (ObjectMapper) p.getCodec();
    JsonNode node = mapper.readTree(p);
    
    Map<String, Object> map = new HashMap<>();
    Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
    while (fields.hasNext()) {
      Map.Entry<String, JsonNode> field = fields.next();
      map.put(field.getKey(), mapper.treeToValue(field.getValue(), Object.class));
    }
    
    preprocessMap(map);
    
    return targetClass.cast(ConfigurationSerialization.deserializeObject(map, targetClass));
  }
  
  protected void preprocessMap(Map<String, Object> map) {
  }
}
