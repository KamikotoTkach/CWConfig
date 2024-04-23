package ru.cwcode.tkach.locale;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    
    for (String line : message) {
      audience.sendMessage(MiniLocale.getInstance().miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(line, null)));
    }
  }

  public void send(Audience audience, Placeholders placeholders) {
    MiniLocale ml = MiniLocale.getInstance();
    
    for (String line : message) {
      audience.sendMessage(MiniLocale.getInstance().miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(line, null), placeholders));
    }
  }

  public Collection<Component> get() {
    MiniLocale ml = MiniLocale.getInstance();
    
    List<Component> list = new ArrayList<>();
    for (String line : message) {
      list.add(MiniLocale.getInstance().miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(line, null)));
    }
    return list;
  }

  public Collection<Component> get(Placeholders placeholders) {
    MiniLocale ml = MiniLocale.getInstance();
    
    List<Component> list = new ArrayList<>();

    for (String line : message) {
      list.add(MiniLocale.getInstance().miniMessageWrapper().deserialize(ml.messagePreprocessor().preprocess(line, null), placeholders));
    }
    return list;
  }

  public Message toSingleMessage() {
    return new Message(message);
  }

  public List<String> toList() {
    return List.of(message);
  }
}
