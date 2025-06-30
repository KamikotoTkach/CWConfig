package ru.cwcode.tkach.config.paper.jackson.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import org.bukkit.util.BlockVector;

import java.io.IOException;

public class BlockVectorSerializer extends JsonSerializer<BlockVector> {
  public final boolean asField;
  
  public BlockVectorSerializer(boolean asField) {
    this.asField = asField;
  }
  
  @Override
  public void serialize(BlockVector value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    String builder =
      value.getBlockX() + " " +
      value.getBlockY() + " " +
      value.getBlockZ();
    
    if (asField) {
      gen.writeFieldName(builder);
    } else {
      gen.writeString(builder);
    }
  }
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    visitor.expectStringFormat(typeHint);
  }
}