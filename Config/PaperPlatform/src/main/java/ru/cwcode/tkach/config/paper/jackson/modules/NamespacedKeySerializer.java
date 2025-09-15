package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;

import java.io.IOException;

public class NamespacedKeySerializer extends JsonSerializer<NamespacedKey> {
  public final boolean asField;
  
  public NamespacedKeySerializer(boolean asField) {
    this.asField = asField;
  }
  
  @Override
  public void serialize(NamespacedKey value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    String string = value.toString();
    
    if (asField) {
      gen.writeFieldName(string);
    } else {
      gen.writeString(string);
    }
  }
}