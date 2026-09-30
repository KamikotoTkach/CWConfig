package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.cwcode.tkach.locale.ExtraMessage;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.TitleMessage;
import ru.cwcode.tkach.locale.data.SoundData;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A string is a plain {@link Message}; an object with {@code sound}, {@code title} or {@code extraDirections}
 * becomes an {@link ExtraMessage}, so any {@code Message} field can carry them and they play on every send.
 */
public class MessageDeserializer extends JsonDeserializer<Message> {
  static final String MESSAGE = "message";
  static final String SOUND = "sound";
  static final String TITLE = "title";
  static final String EXTRA_DIRECTIONS = "extraDirections";

  @Override
  public Message deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectCodec codec = p.getCodec();
    JsonNode node = codec.readTree(p);

    return hasExtras(node) ? readExtraMessage(codec, node) : new Message(text(node));
  }

  static boolean hasExtras(JsonNode node) {
    return node.isObject() && (node.has(SOUND) || node.has(TITLE) || node.has(EXTRA_DIRECTIONS));
  }

  static String text(JsonNode node) {
    JsonNode text = node.isObject() ? node.get(MESSAGE) : node;
    return text == null || text.isNull() ? null : text.asText();
  }

  static ExtraMessage readExtraMessage(ObjectCodec codec, JsonNode node) throws IOException {
    ExtraMessage message = new ExtraMessage(text(node));

    JsonNode sound = node.get(SOUND);
    if (sound != null && !sound.isNull()) {
      message.setSound(codec.treeToValue(sound, SoundData.class));
    }

    JsonNode title = node.get(TITLE);
    if (title != null && !title.isNull()) {
      message.setTitle(codec.treeToValue(title, TitleMessage.class));
    }

    JsonNode directions = node.get(EXTRA_DIRECTIONS);
    if (directions != null && directions.isObject()) {
      Map<String, Message> extraDirections = new LinkedHashMap<>();
      for (Iterator<Map.Entry<String, JsonNode>> it = directions.fields(); it.hasNext(); ) {
        Map.Entry<String, JsonNode> entry = it.next();
        extraDirections.put(entry.getKey(), codec.treeToValue(entry.getValue(), Message.class));
      }
      message.setExtraDirections(extraDirections);
    }

    return message;
  }
}
