package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import net.kyori.adventure.sound.Sound;
import ru.cwcode.tkach.locale.data.SoundData;

import java.io.IOException;
import java.util.Locale;

/**
 * {@code sound: "ui.button.click"} is the short form with default pitch, volume and source;
 * the object form sets any of {@code key}, {@code pitch}, {@code volume}, {@code source}.
 */
public class SoundDataDeserializer extends JsonDeserializer<SoundData> {
  @Override
  public SoundData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    JsonNode node = p.getCodec().readTree(p);
    if (!node.isObject()) return new SoundData(node.asText());

    SoundData sound = new SoundData();
    if (node.hasNonNull("key")) sound.setKey(node.get("key").asText());
    if (node.hasNonNull("pitch")) sound.setPitch((float) node.get("pitch").asDouble());
    if (node.hasNonNull("volume")) sound.setVolume((float) node.get("volume").asDouble());
    if (node.hasNonNull("source")) {
      String source = node.get("source").asText();
      try {
        sound.setSource(Sound.Source.valueOf(source.toUpperCase(Locale.ROOT)));
      } catch (IllegalArgumentException e) {
        return (SoundData) ctxt.handleWeirdStringValue(Sound.Source.class, source, "unknown sound source");
      }
    }
    return sound;
  }
}
