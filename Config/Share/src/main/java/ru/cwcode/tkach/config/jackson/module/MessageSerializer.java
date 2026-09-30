package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import ru.cwcode.tkach.locale.ExtraMessage;
import ru.cwcode.tkach.locale.Message;

import java.io.IOException;

import static ru.cwcode.tkach.config.jackson.module.MessageDeserializer.*;

/**
 * Writes a string unless an {@link ExtraMessage} carries extras — then the object form of {@link MessageDeserializer},
 * so saving a config never drops a sound or a title.
 */
public class MessageSerializer extends JsonSerializer<Message> {
  @Override
  public void serialize(Message value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (!(value instanceof ExtraMessage extra) || !extra.hasExtras()) {
      gen.writeString(value.serialize());
      return;
    }

    gen.writeStartObject();
    if (extra.serialize() != null) gen.writeStringField(MESSAGE, extra.serialize());
    if (extra.getSound() != null) serializers.defaultSerializeField(SOUND, extra.getSound(), gen);
    if (extra.getTitle() != null) serializers.defaultSerializeField(TITLE, extra.getTitle(), gen);
    if (extra.getExtraDirections() != null) serializers.defaultSerializeField(EXTRA_DIRECTIONS, extra.getExtraDirections(), gen);
    gen.writeEndObject();
  }

  @Override
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
    visitor.expectStringFormat(typeHint);
  }
}
