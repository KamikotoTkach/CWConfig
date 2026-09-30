package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.cwcode.tkach.locale.ExtraMessage;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.data.SoundData;
import ru.cwcode.tkach.locale.messageDirection.MessageDirection;
import ru.cwcode.tkach.locale.placeholders.PlaceholderTypesRegistry;
import ru.cwcode.tkach.locale.platform.MiniLocale;
import ru.cwcode.tkach.locale.preprocessor.MessagePreprocessor;
import ru.cwcode.tkach.locale.preprocessor.MessagePreprocessors;
import ru.cwcode.tkach.locale.wrapper.adventure.MiniMessageWrapper;

import java.util.UUID;

import static org.junit.Assert.*;

public class MessageSerializationTest {
  private static ObjectMapper mapper;

  public static class Holder {
    public Message message;
    public ExtraMessage extra;
  }

  @BeforeClass
  public static void setUp() {
    // Message.<clinit> asks MiniLocale for empty placeholders; nothing here renders text
    MiniLocale.setInstance(new NoopMiniLocale());

    SimpleModule module = new SimpleModule();
    module.addDeserializer(Message.class, new MessageDeserializer());
    module.addDeserializer(ExtraMessage.class, new ExtraMessageDeserializer());
    module.addSerializer(Message.class, new MessageSerializer());
    module.addDeserializer(SoundData.class, new SoundDataDeserializer());
    module.addSerializer(SoundData.class, new SoundDataSerializer());

    mapper = new ObjectMapper(new YAMLFactory()).registerModule(module);
    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
    mapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
  }

  @Test
  public void stringStaysPlainMessage() throws Exception {
    Holder holder = mapper.readValue("message: '<red>Hi'\n", Holder.class);

    assertEquals(Message.class, holder.message.getClass());
    assertEquals("<red>Hi", holder.message.serialize());
    assertEquals("<red>Hi", mapper.readTree(mapper.writeValueAsString(holder)).get("message").asText());
  }

  @Test
  public void objectWithOnlyTextStaysPlainMessage() throws Exception {
    Holder holder = mapper.readValue("message:\n  message: '<red>Hi'\n", Holder.class);

    assertEquals(Message.class, holder.message.getClass());
    assertEquals("<red>Hi", holder.message.serialize());
  }

  @Test
  public void extrasInMessageFieldBecomeExtraMessage() throws Exception {
    Holder holder = mapper.readValue("""
                                       message:
                                         message: '<green>Got <amount>'
                                         sound: entity.player.levelup
                                         title:
                                           title: '<gold>Reward'
                                         extraDirections:
                                           actionbar: '<gray>+<amount>'
                                       """, Holder.class);

    ExtraMessage extra = assertInstanceOf(holder.message);
    assertEquals("<green>Got <amount>", extra.serialize());
    assertEquals("entity.player.levelup", extra.getSound().getKey());
    assertEquals(1f, extra.getSound().getVolume(), 0);
    assertEquals(Sound.Source.MASTER, extra.getSound().getSource());
    assertNotNull(extra.getTitle());
    assertEquals("<gray>+<amount>", extra.getExtraDirections().get("actionbar").serialize());
  }

  @Test
  public void soundOnlyMessageHasNoText() throws Exception {
    Holder holder = mapper.readValue("message:\n  sound: ui.button.click\n", Holder.class);

    ExtraMessage extra = assertInstanceOf(holder.message);
    assertNull(extra.serialize());
    assertTrue(extra.isEmpty());
    assertEquals("ui.button.click", extra.getSound().getKey());
  }

  @Test
  public void fullSoundForm() throws Exception {
    Holder holder = mapper.readValue("""
                                       message:
                                         sound:
                                           key: block.note_block.pling
                                           pitch: 1.5
                                           source: player
                                       """, Holder.class);

    SoundData sound = assertInstanceOf(holder.message).getSound();
    assertEquals("block.note_block.pling", sound.getKey());
    assertEquals(1.5f, sound.getPitch(), 0);
    assertEquals(1f, sound.getVolume(), 0);
    assertEquals(Sound.Source.PLAYER, sound.getSource());
  }

  @Test(expected = JsonMappingException.class)
  public void unknownSoundSourceFails() throws Exception {
    mapper.readValue("message:\n  sound:\n    key: a\n    source: nowhere\n", Holder.class);
  }

  @Test
  public void extraMessageFieldAcceptsString() throws Exception {
    Holder holder = mapper.readValue("extra: '<red>Hi'\n", Holder.class);

    assertEquals("<red>Hi", holder.extra.serialize());
    assertFalse(holder.extra.hasExtras());
    assertEquals("<red>Hi", mapper.readTree(mapper.writeValueAsString(holder)).get("extra").asText());
  }

  @Test
  public void saveKeepsExtrasAndShortSound() throws Exception {
    String yml = """
      message:
        message: '<green>Got'
        sound: entity.player.levelup
        title:
          title: '<gold>Reward'
          subtitle: '<gray>sub'
          fadeIn: 100
          stay: 1500
          fadeOut: 300
        extraDirections:
          actionbar: '<gray>+1'
      extra:
        message: '<red>Hi'
        sound:
          key: ui.button.click
          pitch: 2.0
          volume: 0.5
          source: MASTER
      """;

    Holder holder = mapper.readValue(yml, Holder.class);
    String saved = mapper.writeValueAsString(holder);

    assertEquals(mapper.readTree(yml), mapper.readTree(saved));
  }

  private static ExtraMessage assertInstanceOf(Message message) {
    assertTrue("expected ExtraMessage, got " + message.getClass(), message instanceof ExtraMessage);
    return (ExtraMessage) message;
  }

  private static class NoopMiniLocale extends MiniLocale {
    @Override public Component legacySection(String message) { return null; }
    @Override public String getLanguage(Audience receiver) { return null; }
    @Override public void send(Message message, MessageDirection direction, Iterable<? extends Audience> audiences, Placeholders placeholders) {}
    @Override public void showTitle(Audience audience, Component title, Component subtitle, int fadeIn, int stay, int fadeOut) {}
    @Override public boolean isPlayer(Audience audience) { return false; }
    @Override public Audience getOnlinePlayer(UUID uuid) { return null; }
    @Override public Audience getPlayer(String name) { return null; }
    @Override public String plain(Component component) { return null; }
    @Override public Audience getOnlinePlayers() { return null; }
    @Override public MiniMessageWrapper miniMessageWrapper() { return null; }
    @Override public MessagePreprocessor messagePreprocessor() { return null; }
    @Override public MessagePreprocessors messagePreprocessors() { return null; }
    @Override public Audience console() { return null; }
    @Override public String legacyAmpersand(Component component) { return null; }
    @Override public Component legacyAmpersand(String message) { return null; }
    @Override public Placeholders emptyPlaceholders() { return null; }
    @Override public PlaceholderTypesRegistry placeholderTypesRegistry() { return null; }
  }
}
