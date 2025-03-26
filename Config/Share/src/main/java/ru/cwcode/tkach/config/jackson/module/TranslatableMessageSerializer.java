package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import ru.cwcode.tkach.locale.translatable.TranslatableMessage;

import java.io.IOException;
import java.util.Map;

public class TranslatableMessageSerializer extends JsonSerializer<TranslatableMessage> {
  
  @Override
  public void serialize(TranslatableMessage value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName("default");
    gen.writeString(value.getText());
    gen.writeFieldName("translates");
    serializers.defaultSerializeValue(value.getTranslates(), gen);
    gen.writeEndObject();
  }
  
  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    JsonObjectFormatVisitor objectVisitor = visitor.expectObjectFormat(typeHint);
    objectVisitor.property("default", visitor.getProvider().findValueSerializer(String.class), typeHint);
    objectVisitor.property("translates", visitor.getProvider().findValueSerializer(Map.class), typeHint);
  }
}
