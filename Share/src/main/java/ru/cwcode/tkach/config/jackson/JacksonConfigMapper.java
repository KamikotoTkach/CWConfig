package ru.cwcode.tkach.config.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;
import ru.cwcode.tkach.config.base.manager.ConfigManager;
import ru.cwcode.tkach.config.base.manager.ConfigMapper;
import ru.cwcode.tkach.config.jackson.module.*;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.MessageArr;
import ru.cwcode.tkach.locale.translatable.TranslatableMessage;

import java.util.Optional;

public abstract class JacksonConfigMapper<C extends Config<C>> extends ConfigMapper<C> {
  protected ObjectMapper mapper;
  
  public JacksonConfigMapper() {
  }
  
  @Override
  public void setConfigManager(ConfigManager<C> configManager) {
    super.setConfigManager(configManager);
    
    configureObjectMapper();
  }
  
  protected abstract ObjectMapper createObjectMapper();
  
  @Override
  public <V extends C> Optional<V> map(String string, Class<V> configClass, ConfigPersistOptions persistOptions) {
    return Optional.empty();
  }
  
  @Override
  public Optional<String> map(C config, ConfigPersistOptions persistOptions) {
    return Optional.empty();
  }
  
  protected void configureObjectMapper() {
    mapper = createObjectMapper();
    
    mapper.findAndRegisterModules();
    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
    mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
    mapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
    
    SimpleModule module = new SimpleModule("cwconfigLocale");
    
    module.addDeserializer(TranslatableMessage.class, new TranslatableMessageDeserializer());
    module.addSerializer(TranslatableMessage.class, new TranslatableMessageSerializer());
    
    module.addDeserializer(Message.class, new MessageDeserializer());
    module.addSerializer(Message.class, new MessageSerializer());
    
    module.addDeserializer(MessageArr.class, new MessageArrDeserializer());
    module.addSerializer(MessageArr.class, new MessageArrSerializer());
    
    module(module);
    
    for (Module additionalJacksonModule : configManager.platform().additionalJacksonModules()) {
      module(additionalJacksonModule);
    }
  }
  
  public void module(Module module) {
    mapper.registerModule(module);
  }
}
