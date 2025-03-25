package ru.cwcode.tkach.locale.data;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class SoundData implements Serializable {
  private static final long serialVersionUID = 1L;
  
  String key = "ui.button.click";
  float pitch = 1;
  float volume = 1;
  Sound.Source source = Sound.Source.MASTER;
  
  public void play(Audience audience) {
    audience.playSound(getSound(), Sound.Emitter.self());
  }
  
  public void play(Audience audience, double x, double y, double z) {
    audience.playSound(getSound(), x, y, z);
  }
  
  public @NotNull Sound getSound() {
    return Sound.sound()
                .type(Key.key(key))
                .pitch(pitch)
                .volume(volume)
                .source(source)
                .build();
  }
  
  //<editor-fold desc="Getters">
  public String getKey() {
    return key;
  }
  
  public float getPitch() {
    return pitch;
  }
  
  public float getVolume() {
    return volume;
  }
  
  public Sound.Source getSource() {
    return source;
  }
  //</editor-fold>
  //<editor-fold desc="Setters">
  public void setKey(String key) {
    this.key = key;
  }
  
  public void setPitch(float pitch) {
    this.pitch = pitch;
  }
  
  public void setVolume(float volume) {
    this.volume = volume;
  }
  
  public void setSource(Sound.Source source) {
    this.source = source;
  }
  //</editor-fold>
  //<editor-fold desc="Constructors">
  public SoundData(String key, float pitch, float volume, Sound.Source source) {
    this.key = key;
    this.pitch = pitch;
    this.volume = volume;
    this.source = source;
  }
  
  public SoundData(String key, Sound.Source source) {
    this.key = key;
    this.source = source;
  }
  
  public SoundData(String key) {
    this.key = key;
  }
  
  public SoundData() {
  }
  //</editor-fold>
}
