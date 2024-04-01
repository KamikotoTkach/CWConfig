package ru.cwcode.tkach.config.base;

import ru.cwcode.tkach.config.base.manager.ConfigManager;

public abstract class Config<C extends Config<C>> {
  protected transient boolean saveAllEnabled = true;
  transient protected ConfigManager<C> manager = null;
  transient String name;

  public void setManager(ConfigManager<C> manager) {
    this.manager = manager;
  }

  public boolean saveAllEnabled() {
    return saveAllEnabled;
  }

  public void setSaveAllEnabled(boolean isEnabled) {
    saveAllEnabled = isEnabled;
  }

  public void save() {
    manager.save((C) this);
  }

  public void save(boolean async) {
    manager.save((C) this, (o) -> o.async(async));
  }

  public String name() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return manager.toString((C) this);
  }
}
