package ru.cwcode.tkach.config.jackson.yaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.yaml.snakeyaml.LoaderOptions;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;
import ru.cwcode.tkach.config.jackson.JacksonConfigMapper;

import java.util.Optional;

public class YmlConfigMapper extends JacksonConfigMapper<YmlConfig> {
  public YmlConfigMapper() {
    super();
  }
  
  @Override
  public <V extends YmlConfig> MappingResult<V> map(String string, Class<V> configClass, ConfigPersistOptions persistOptions) {
    try {
      return new MappingResult<V>(mapper.readValue(string, configClass),null);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return new MappingResult<V>(null, new MappingException(e.getLocation().getLineNr(), e.getLocation().getColumnNr(), e.getMessage()));
    }
  }
  
  @Override
  public Optional<String> map(YmlConfig config, ConfigPersistOptions persistOptions) {
    try {
      return Optional.ofNullable(mapper.writeValueAsString(config));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
  
  @Override
  protected ObjectMapper createObjectMapper() {
    LoaderOptions loaderOptions = new LoaderOptions();
    loaderOptions.setCodePointLimit(100 * 1024 * 1024); //todo сделать установку этого где-то
    
    YAMLFactory yaml = YAMLFactory.builder()
                                  .disable(YAMLGenerator.Feature.SPLIT_LINES)
                                  .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
                                  .loaderOptions(loaderOptions)
                                  .build();
    
    return new ObjectMapper(yaml);
  }
}
