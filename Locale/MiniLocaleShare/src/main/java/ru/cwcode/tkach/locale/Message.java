package ru.cwcode.tkach.locale;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import ru.cwcode.cwutils.collections.CollectionUtils;
import ru.cwcode.cwutils.messages.TargetableMessageReturn;
import ru.cwcode.tkach.locale.messageDirection.*;
import ru.cwcode.tkach.locale.platform.MiniLocale;
import ru.cwcode.tkach.locale.wrapper.adventure.AudienceWrapper;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class Message implements Serializable {
  private static final long serialVersionUID = 1L;

  public static final Pattern LEGACY_AMPERSAND = Pattern.compile("&[\\d#abcdefklmnrx]");
  public static final Pattern LEGACY_SECTION = Pattern.compile("§[\\d#abcdefklmnrx]");
  public static final Placeholders EMPTY_PLACEHOLDERS = MiniLocale.getInstance().emptyPlaceholders();
  protected String message;

  //region constructors
  public Message() {
  }

  public Message(String... message) {
    this(CollectionUtils.toString(message, "", "\n", true));
  }

  public Message(String message) {
    this.message = message;
  }

  public Message(Component message) {
    this.message = LegacyComponentSerializer.legacySection().serialize(message);
  }

  public Message(Mode mode, String... message) {
    this(CollectionUtils.toString(message, "", "\n", true), mode);
  }

  public Message(String message, Mode mode) {
    this.message = message;

    switch (mode) {
      case MINI_MESSAGE:
        break;
      case LEGACY_AMPERSAND:
        this.message = MiniLocale.getInstance().miniMessageWrapper().serialize(MiniLocale.getInstance().legacyAmpersand(message)).replaceAll("\\\\", "");
        break;
      case LEGACY_SECTION:
        this.message = MiniLocale.getInstance().miniMessageWrapper().serialize((MiniLocale.getInstance().legacySection(message))).replaceAll("\\\\", "");
        break;
    }
  }
  //endregion

  //region base send methods
  public void send(MessageDirection direction, Audience audience) {
    send(direction, audience, EMPTY_PLACEHOLDERS);
  }

  public void send(MessageDirection direction, Iterable<? extends Audience> audiences) {
    send(direction, audiences, EMPTY_PLACEHOLDERS);
  }

  public void send(MessageDirection direction, Audience audience, Placeholders placeholders) {
    send(direction, List.of(audience), placeholders);
  }

  public void send(MessageDirection direction, Iterable<? extends Audience> audiences, Placeholders placeholders) {
    if (message == null) return;

    MiniLocale.getInstance().send(this, direction, audiences, placeholders);
  }
  //endregion

  //region get as component
  public Component get() {
    return MiniLocale.getInstance().miniMessageWrapper().deserialize(message);
  }

  public Component get(Placeholders placeholders) {
    return MiniLocale.getInstance().miniMessageWrapper().deserialize(message, placeholders);
  }

  public Component get(Audience receiver) {
    MiniLocale ml = MiniLocale.getInstance();

    return ml.miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(message, receiver));
  }

  public Component get(Placeholders placeholders, Audience receiver) {
    MiniLocale ml = MiniLocale.getInstance();

    return ml.miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(message, receiver), placeholders);
  }

  public Component get(Audience receiver, Placeholders placeholders) {
    return get(placeholders, receiver);
  }
  //endregion

  //region send
  public void send(Audience audience) {
    send(ChatDirection.INSTANCE, audience);
  }

  public void send(Audience audience, Placeholders placeholders) {
    send(ChatDirection.INSTANCE, audience, placeholders);
  }

  public void send(UUID maybeOfflinePlayer) {
    Audience player = MiniLocale.getInstance().getOnlinePlayer(maybeOfflinePlayer);

    if (player != null) {
      send(player);
    }
  }

  public void send(UUID maybeOfflinePlayer, Placeholders placeholders) {
    Audience player = MiniLocale.getInstance().getOnlinePlayer(maybeOfflinePlayer);

    if (player != null) {
      send(player, placeholders);
    }
  }

  public void send(String playerName) {
    Audience player = MiniLocale.getInstance().getPlayer(playerName);
    if (player != null) {
      send(player);
    }
  }

  public void send(String playerName, Placeholders placeholders) {
    Audience player = MiniLocale.getInstance().getPlayer(playerName);
    if (player != null) {
      send(player, placeholders);
    }
  }

  public void send(Iterable<String> playerNames) {
    for (String playerName : playerNames) {
      send(playerName);
    }
  }

  public void send(Iterable<String> playerNames, Placeholders placeholders) {
    for (String playerName : playerNames) {
      send(playerName, placeholders);
    }
  }
  //endregion

  //region title
  public void sendTitle(Audience audience) {
    send(TitleDirection.INSTANCE, audience);
  }

  public void sendTitle(Audience audience, Placeholders placeholders) {
    send(TitleDirection.INSTANCE, audience, placeholders);
  }

  public void sendTitle(Iterable<? extends Audience> audiences) {
    send(TitleDirection.INSTANCE, audiences);
  }

  public void sendTitle(Iterable<? extends Audience> audiences, Placeholders placeholders) {
    send(TitleDirection.INSTANCE, audiences, placeholders);
  }
  //endregion

  //region subtitle
  public void sendSubtitle(Audience audience) {
    send(SubtitleDirection.INSTANCE, audience);
  }

  public void sendSubtitle(Audience audience, Placeholders placeholders) {
    send(SubtitleDirection.INSTANCE, audience, placeholders);
  }

  public void sendSubtitle(Iterable<? extends Audience> audiences) {
    send(SubtitleDirection.INSTANCE, audiences);
  }

  public void sendSubtitle(Iterable<? extends Audience> audiences, Placeholders placeholders) {
    send(SubtitleDirection.INSTANCE, audiences, placeholders);
  }
  //endregion

  //region broadcast
  public void broadcast() {
    send(ChatDirection.INSTANCE, AudienceWrapper.onlinePlayers());
  }

  public void broadcast(MessageDirection direction) {
    send(direction, AudienceWrapper.onlinePlayers());
  }

  public void broadcast(MessageDirection direction, Placeholders placeholders) {
    send(direction, AudienceWrapper.onlinePlayers(), placeholders);
  }

  public void broadcast(Placeholders placeholders) {
    send(ChatDirection.INSTANCE, AudienceWrapper.onlinePlayers(), placeholders);
  }

  //endregion

  //region action bar
  public void sendActionBar(Audience audience) {
    send(ActionBarDirection.INSTANCE, audience);
  }

  public void sendActionBar(Audience audience, Placeholders placeholders) {
    send(ActionBarDirection.INSTANCE, audience, placeholders);
  }

  public void sendActionBar(Iterable<? extends Audience> audiences) {
    send(ActionBarDirection.INSTANCE, audiences);
  }

  public void sendActionBar(Iterable<? extends Audience> audiences, Placeholders placeholders) {
    send(ActionBarDirection.INSTANCE, audiences, placeholders);
  }
  //endregion

  //region console
  public void console() {
    send(ChatDirection.INSTANCE, MiniLocale.getInstance().console());
  }

  public void console(Placeholders placeholders) {
    send(ChatDirection.INSTANCE, MiniLocale.getInstance().console(), placeholders);
  }
  //endregion

  //region get as string
  public String getLegacy() {
    return MiniLocale.getInstance().legacyAmpersand(get());
  }

  public String getLegacy(Placeholders placeholders) {
    return MiniLocale.getInstance().legacyAmpersand(get(placeholders));
  }

  public String getLegacy(Audience receiver) {
    return MiniLocale.getInstance().legacyAmpersand(get(receiver));
  }

  public String getLegacy(Placeholders placeholders, Audience receiver) {
    return MiniLocale.getInstance().legacyAmpersand(get(placeholders, receiver));
  }

  public String getLegacySection() {
    return LegacyComponentSerializer.legacySection().serialize(get());
  }

  public String getLegacySection(Placeholders placeholders) {
    return LegacyComponentSerializer.legacySection().serialize(get(placeholders));
  }

  public String getLegacySection(Audience receiver) {
    return LegacyComponentSerializer.legacySection().serialize(get(receiver));
  }

  public String getLegacySection(Placeholders placeholders, Audience receiver) {
    return LegacyComponentSerializer.legacySection().serialize(get(placeholders, receiver));
  }

  public String getText() {
    return MiniLocale.getInstance().plain(get());
  }

  public String getText(Audience receiver) {
    return MiniLocale.getInstance().plain(get(receiver));
  }

  public String getText(Placeholders placeholders) {
    return MiniLocale.getInstance().plain(get(placeholders));
  }

  public String getText(Placeholders placeholders, Audience receiver) {
    return MiniLocale.getInstance().plain(get(placeholders, receiver));
  }

  public String serialize() {
    return message;
  }
  //endregion

  //region checks
  public boolean isNotEmpty() {
    return !message.isEmpty();
  }

  public boolean isEmpty() {
    return message.isEmpty();
  }
  //endregion

  //region utils
  public static Message parse(String message) {
    Mode mode;
    if (isAmpersand(message)) {
      mode = Mode.LEGACY_AMPERSAND;
    } else if (isSection(message)) {
      mode = Mode.LEGACY_SECTION;
    } else {
      mode = Mode.MINI_MESSAGE;
    }

    return new Message(message, mode);
  }

  public static boolean isAmpersand(String message) {
    return LEGACY_AMPERSAND.matcher(message).find();
  }
  
  public static boolean isSection(String message) {
    return LEGACY_SECTION.matcher(message).find();
  }
  //endregion
}
