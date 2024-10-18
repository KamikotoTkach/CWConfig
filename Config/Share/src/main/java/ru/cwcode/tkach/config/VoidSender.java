package ru.cwcode.tkach.config;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import ru.cwcode.commands.api.Sender;
import ru.cwcode.cwutils.messages.TargetableMessageReturn;

public class VoidSender implements Sender {
  public static final VoidSender INSTANCE = new VoidSender();
  
  @Override
  public boolean hasPermission(String str) {
    return false;
  }
  
  @Override
  public String getName() {
    return "";
  }
  
  @Override
  public void sendMessage(Component line) {
  
  }
  
  @Override
  public boolean isPlayer() {
    return false;
  }
  
  @Override
  public void sendMessage(TargetableMessageReturn targetable) {
  
  }
  
  @Override
  public void sendMessage(String message) {
  
  }
  
  @Override
  public Audience getAudience() {
    return null;
  }
  
  @Override
  public void confirm(String confirmableString, long timeToConfirm, Runnable onConfirm, Runnable onExpired) {
  
  }
}
