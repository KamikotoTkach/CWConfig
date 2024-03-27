package ru.cwcode.tkach.locale;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import ru.cwcode.tkach.locale.platform.MiniLocale;
import ru.cwcode.tkach.locale.wrapper.adventure.AudienceWrapper;

import java.io.Serializable;

public class TitleMessage implements Serializable {
  Message title;
  Message subtitle;
  int fadeIn = 100;
  int stay = 1500;
  int fadeOut = 300;
  
  public TitleMessage() {
  }
  
  public TitleMessage(Message title, Message subtitle) {
    this.title = title;
    this.subtitle = subtitle;
  }
  
  public TitleMessage(String title, String subtitle) {
    this.title = new Message(title);
    this.subtitle = new Message(subtitle);
  }
  
  public void show(Audience audience) {
    showTitle(audience, title.get(audience), subtitle.get(audience));
  }
  
  public void show(Audience audience, Placeholders placeholders) {
    showTitle(audience, title.get(audience, placeholders), subtitle.get(audience, placeholders));
  }
  
  public void broadcast(Placeholders placeholders) {
    show(AudienceWrapper.onlinePlayers(), placeholders);
  }
  
  public void broadcast() {
    show(AudienceWrapper.onlinePlayers());
  }
  
  private void showTitle(Audience audience, Component title, Component subtitle) {
    MiniLocale.getInstance().showTitle(audience, title, subtitle, fadeIn, stay, fadeOut);
  }
}
