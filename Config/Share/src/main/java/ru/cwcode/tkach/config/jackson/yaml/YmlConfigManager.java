package ru.cwcode.tkach.config.jackson.yaml;

import ru.cwcode.cwutils.collections.CollectionUtils;
import ru.cwcode.tkach.config.annotation.Description;
import ru.cwcode.tkach.config.base.ConfigPlatform;
import ru.cwcode.tkach.config.base.manager.ConfigCreator;
import ru.cwcode.tkach.config.base.manager.ConfigLoader;
import ru.cwcode.tkach.config.base.manager.ConfigMapper;
import ru.cwcode.tkach.config.base.manager.ConfigPersister;
import ru.cwcode.tkach.config.jackson.JacksonConfigManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class YmlConfigManager extends JacksonConfigManager<YmlConfig> {
  public YmlConfigManager(ConfigPlatform configPlatform,
                          ConfigLoader<YmlConfig> loader,
                          ConfigPersister<YmlConfig> persister,
                          ConfigMapper<YmlConfig> mapper,
                          ConfigCreator<YmlConfig> creator) {
    super(configPlatform, loader, persister, mapper, creator);
    
    persister.setPreprocessor(getConfigPreprocessor());
  }
  
  public YmlConfigManager(ConfigPlatform configPlatform, ConfigCreator<YmlConfig> creator) {
    this(configPlatform, new ConfigLoader<>(), new ConfigPersister<>(), new YmlConfigMapper(), creator);
  }
  
  public YmlConfigManager(ConfigPlatform configPlatform) {
    super(configPlatform, new ConfigPersister<>(), new YmlConfigMapper());
    
    persister.setPreprocessor(getConfigPreprocessor());
  }
  
  private ConfigPersister.Preprocessor<YmlConfig> getConfigPreprocessor() {
    return new ConfigPersister.Preprocessor<>() {
      @Override
      public String preprocess(YmlConfig config, String data) {
        String[] header = config.header();
        if (header != null) {
          data = CollectionUtils.toString(header, "#", "\n", false) + data;
        }
        
        for (var descriptionEntry : Arrays.stream(config.getClass().getDeclaredFields())
                                          .filter(x -> x.isAnnotationPresent(Description.class))
                                          .collect(Collectors.toMap(Field::getName, o -> o.getAnnotation(Description.class).value()))
                                          .entrySet()) {
          
          data = data.replaceFirst("(\n" + descriptionEntry.getKey() + ")",
                                   CollectionUtils.toString(descriptionEntry.getValue(), "\n#", "", false) + "$1");
        }
        
        return data;
      }
    };
  }
  
  @Override
  public YmlConfigMapper mapper() {
    return (YmlConfigMapper) super.mapper();
  }
}
