package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.kyori.adventure.sound.Sound;
import ru.cwcode.tkach.locale.data.SoundData;

import java.io.IOException;

/**
 * Mirrors {@link SoundDataDeserializer}: a sound with default pitch, volume and source is saved as its key alone.
 */
public class SoundDataSerializer extends JsonSerializer<SoundData> {
  private static final SoundData DEFAULTS = new SoundData();

  @Override
  public void serialize(SoundData value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (value.getPitch() == DEFAULTS.getPitch() && value.getVolume() == DEFAULTS.getVolume() && value.getSource() == DEFAULTS.getSource()) {
      gen.writeString(value.getKey());
      return;
    }

    gen.writeStartObject();
    gen.writeStringField("key", value.getKey());
    gen.writeNumberField("pitch", value.getPitch());
    gen.writeNumberField("volume", value.getVolume());
    gen.writeStringField("source", sourceName(value.getSource()));
    gen.writeEndObject();
  }

  private static String sourceName(Sound.Source source) {
    return source == null ? null : source.name();
  }
}
