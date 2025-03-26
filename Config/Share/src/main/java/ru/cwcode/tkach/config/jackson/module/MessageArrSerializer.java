package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import ru.cwcode.tkach.locale.MessageArr;

import java.io.IOException;
import java.util.List;

public class MessageArrSerializer extends JsonSerializer<MessageArr> {
  @Override
  public void serialize(MessageArr value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    List<String> list = value.toList();
    gen.writeArray(list.toArray(new String[0]), 0, list.size());
  }
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    visitor.expectArrayFormat(typeHint);
  }
}