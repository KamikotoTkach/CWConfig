package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import org.bukkit.OfflinePlayer;

import java.io.IOException;

public class OfflinePlayerSerializer extends JsonSerializer<OfflinePlayer> {
  public final boolean asField;
  
  public OfflinePlayerSerializer(boolean asField) {
    this.asField = asField;
  }
  
  @Override
  public void serialize(OfflinePlayer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    String string = value.getUniqueId().toString();
    
    if (asField) {
      gen.writeFieldName(string);
    } else {
      gen.writeString(string);
    }
  }
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    visitor.expectStringFormat(typeHint).format(JsonValueFormat.UUID);
  }
}