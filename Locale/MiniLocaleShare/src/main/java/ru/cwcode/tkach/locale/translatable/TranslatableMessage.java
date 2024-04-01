package ru.cwcode.tkach.locale.translatable;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Mode;
import ru.cwcode.tkach.locale.Placeholders;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.util.HashMap;

public class TranslatableMessage extends Message {
  HashMap<String, String> translates = new HashMap<>();

  public TranslatableMessage() {
    super();
  }

  public TranslatableMessage(String... message) {
    super(message);
  }

  public TranslatableMessage(Mode mode, String... message) {
    super(mode, message);
  }

  public TranslatableMessage(String message) {
    super(message);
  }

  public TranslatableMessage(String message, Mode mode) {
    super(message, mode);
  }

  public HashMap<String, String> getTranslates() {
    return translates;
  }

  public void setTranslates(HashMap<String, String> translates) {
    this.translates = translates;
  }

  @Override
  public Component get(Placeholders placeholders, Audience receiver) {
    MiniLocale ml = MiniLocale.getInstance();

    return ml.miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(getMessage(receiver), receiver), placeholders);
  }

  @Override
  public Component get(Audience receiver) {
    MiniLocale ml = MiniLocale.getInstance();

    return ml.miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(getMessage(receiver), receiver));
  }

  private String getMessage(Audience receiver) {
    String lang = MiniLocale.getInstance().getLanguage(receiver);

    if (lang != null) {
      if (translates.containsKey(lang)) {
        return translates.get(lang);
      }
    }
    return message;
  }
}
