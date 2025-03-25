package ru.cwcode.tkach.locale.messageDirection;

import java.util.Map;

public class MessageDirections {
  public static ChatDirection CHAT = ChatDirection.INSTANCE;
  public static TitleDirection TITLE = TitleDirection.INSTANCE;
  public static SubtitleDirection SUBTITLE = SubtitleDirection.INSTANCE;
  public static ActionBarDirection ACTIONBAR = ActionBarDirection.INSTANCE;
  
  static final Map<String, MessageDirection> VALUES = Map.of("CHAT", CHAT,
                                                       "TITLE", TITLE,
                                                       "SUBTITLE", SUBTITLE,
                                                       "ACTIONBAR", ACTIONBAR);
  
  public static Map<String, MessageDirection> values() {
    return VALUES;
  }
}
