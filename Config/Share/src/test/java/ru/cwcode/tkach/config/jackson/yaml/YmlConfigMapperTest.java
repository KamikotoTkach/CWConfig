package ru.cwcode.tkach.config.jackson.yaml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YmlConfigMapperTest {
  @Test
  public void mapsExceptionWithoutLocation() throws Exception {
    YmlConfigMapper mapper = new YmlConfigMapper();
    setMapper(mapper, new ObjectMapper() {
      @Override
      public <T> T readValue(String content, Class<T> valueType) throws JsonProcessingException {
        throw new JsonProcessingException("Invalid config") {};
      }
    });

    var result = mapper.map("invalid", TestConfig.class, new ConfigPersistOptions());

    assertTrue(result.getConfig().isEmpty());
    assertTrue(result.getException().isPresent());
    assertEquals(0, result.getException().get().line());
    assertEquals(0, result.getException().get().column());
  }

  private void setMapper(YmlConfigMapper configMapper, ObjectMapper mapper) throws Exception {
    Field field = configMapper.getClass().getSuperclass().getDeclaredField("mapper");
    field.setAccessible(true);
    field.set(configMapper, mapper);
  }

  private static class TestConfig extends YmlConfig {
  }
}
