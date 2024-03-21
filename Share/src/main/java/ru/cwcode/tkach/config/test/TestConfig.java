package ru.cwcode.tkach.config.test;

import ru.cwcode.tkach.config.base.ConfigPersistOptions;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfig;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfigManager;

public class TestConfig extends YmlConfig {
  public void test() {
    YmlConfigManager ymlConfigManager = new YmlConfigManager(null);
    
    TestConfig load = ymlConfigManager.load("", TestConfig.class);
    
    ymlConfigManager.save(this);
    ymlConfigManager.toString(load);
    ymlConfigManager.mapper().map("", TestConfig.class, ConfigPersistOptions.DEFAULT);
  }
}
