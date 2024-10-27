package ru.cwcode.tkach.locale;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class MessageArr implements Serializable {
  private static final long serialVersionUID = 1L;

  String[] message;

  public MessageArr(String... message) {
    this.message = message;
  }

  public MessageArr() {
  }

  public void send(Audience audience) {
    MiniLocale ml = MiniLocale.getInstance();
    
    String[] preprocessed = ml.messagePreprocessor().preprocess(message, audience);
    
    for (Component component : ml.miniMessageWrapper().deserialize(preprocessed)) {
      audience.sendMessage(component);
    }
  }

  public void send(Audience audience, Placeholders placeholders) {
    MiniLocale ml = MiniLocale.getInstance();
    
    List<String> preprocessed = Arrays.asList(ml.messagePreprocessor().preprocess(message, audience));
    
    for (Component component : ml.miniMessageWrapper().deserialize(preprocessed, placeholders, false)) {
      audience.sendMessage(component);
    }
  }

  public List<Component> get() {
    MiniLocale ml = MiniLocale.getInstance();
    
    return ml.miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(message, null));
  }

  public List<Component> get(Placeholders placeholders) {
    MiniLocale ml = MiniLocale.getInstance();
    
    List<String> preprocessed = Arrays.asList(ml.messagePreprocessor().preprocess(message, null));
    
    return ml.miniMessageWrapper().deserialize(preprocessed, placeholders, false);
  }

  public Message toSingleMessage() {
    return new Message(message);
  }

  public List<String> toList() {
    return List.of(message);
  }
}
