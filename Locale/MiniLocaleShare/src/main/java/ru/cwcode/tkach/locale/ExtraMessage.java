package ru.cwcode.tkach.locale;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import ru.cwcode.tkach.locale.data.SoundData;
import ru.cwcode.tkach.locale.messageDirection.MessageDirection;
import ru.cwcode.tkach.locale.messageDirection.MessageDirections;
import ru.cwcode.tkach.locale.platform.MiniLocale;

import java.io.Serializable;
import java.util.Map;

public class ExtraMessage extends Message implements Serializable {
  private static final long serialVersionUID = 1L;
  
  Map<String, Message> extraDirections;
  SoundData sound;
  TitleMessage title;
  
  @Override
  public void send(MessageDirection direction, Iterable<? extends Audience> audiences, Placeholders placeholders) {
    if (message != null) MiniLocale.getInstance().send(this, direction, audiences, placeholders);
    
    if (title != null) audiences.forEach(x -> title.show(x, placeholders));
    
    if (extraDirections != null) extraDirections.forEach((directionCandidate, msg) -> {
      MessageDirection messageDirection = MessageDirections.values().get(directionCandidate);
      if (messageDirection != null) {
        msg.send(messageDirection, audiences, placeholders);
      }
    });
    
    if (sound != null) {
      audiences.forEach(audience -> sound.play(audience));
    }
  }
  
  //<editor-fold desc="Getters">
  public Map<String, Message> getExtraDirections() {
    return extraDirections;
  }
  
  public void setExtraDirections(Map<String, Message> extraDirections) {
    this.extraDirections = extraDirections;
  }
  
  public SoundData getSound() {
    return sound;
  }
  //</editor-fold>
  //<editor-fold desc="Setters">
  public void setSound(SoundData sound) {
    this.sound = sound;
  }
  
  public TitleMessage getTitle() {
    return title;
  }
  
  public void setTitle(TitleMessage title) {
    this.title = title;
  }
  //</editor-fold>
  //<editor-fold desc="Constructors">
  public ExtraMessage() {
  }
  
  public ExtraMessage(String... message) {
    super(message);
  }
  
  public ExtraMessage(String message) {
    super(message);
  }
  
  public ExtraMessage(Component message) {
    super(message);
  }
  
  public ExtraMessage(Mode mode, String... message) {
    super(mode, message);
  }
  
  public ExtraMessage(String message, Mode mode) {
    super(message, mode);
  }
  
  
  //</editor-fold>
}
