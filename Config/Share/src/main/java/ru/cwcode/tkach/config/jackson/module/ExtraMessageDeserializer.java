package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.cwcode.tkach.locale.ExtraMessage;

import java.io.IOException;

/**
 * Jackson looks deserializers up by the exact class, so an {@link ExtraMessage} field needs its own entry;
 * the format is the one of {@link MessageDeserializer}, a plain string included.
 */
public class ExtraMessageDeserializer extends JsonDeserializer<ExtraMessage> {
  @Override
  public ExtraMessage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    return MessageDeserializer.readExtraMessage(codec, node);
  }
}
