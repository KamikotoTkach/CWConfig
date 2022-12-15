package tkachgeek.config.yaml;

import tkachgeek.config.base.Config;

public abstract class YmlConfig extends Config {
  @Override
  public void store(String path) {
    YmlConfigManager.store(path, this);
  }
  
  @Override
  public void store() {
    YmlConfigManager.store(path, this);
  }
  
  @Override
  public String toString() {
    return YmlConfigManager.toString(this);
  }
}
