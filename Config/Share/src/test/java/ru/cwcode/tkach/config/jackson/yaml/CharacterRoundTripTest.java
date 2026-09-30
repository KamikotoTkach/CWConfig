package ru.cwcode.tkach.config.jackson.yaml;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;
import ru.cwcode.tkach.config.jackson.module.DigitCharacterDeserializer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CharacterRoundTripTest {
  private final ObjectMapper mapper = createMapper();
  
  @Test
  public void digitCharSurvivesSaveAndLoad() throws Exception {
    Chars chars = new Chars();
    chars.wrapper = '1';
    chars.primitive = '7';
    
    String yaml = mapper.writeValueAsString(chars);
    Chars loaded = mapper.readValue(yaml, Chars.class);
    
    assertTrue(yaml, yaml.contains("wrapper: \"1\""));
    assertEquals(Character.valueOf('1'), loaded.wrapper);
    assertEquals('7', loaded.primitive);
  }
  
  @Test
  public void unquotedDigitIsReadAsDigit() throws Exception {
    Chars loaded = mapper.readValue("wrapper: 1\nprimitive: 2\n", Chars.class);
    
    assertEquals(Character.valueOf('1'), loaded.wrapper);
    assertEquals('2', loaded.primitive);
  }
  
  @Test
  public void lettersAndQuotedDigitsStayAsIs() throws Exception {
    assertEquals(Character.valueOf('Y'), mapper.readValue("wrapper: Y\n", Chars.class).wrapper);
    assertEquals(Character.valueOf('1'), mapper.readValue("wrapper: '1'\n", Chars.class).wrapper);
  }
  
  @Test
  public void numericLookingStringKeepsItsText() throws Exception {
    Strings strings = new Strings();
    strings.values = List.of("1", "007", "1.50");
    
    Strings loaded = mapper.readValue(mapper.writeValueAsString(strings), Strings.class);
    
    assertEquals(strings.values, loaded.values);
  }
  
  private ObjectMapper createMapper() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Character.class, DigitCharacterDeserializer.WRAPPER);
    module.addDeserializer(Character.TYPE, DigitCharacterDeserializer.PRIMITIVE);
    
    ObjectMapper objectMapper = new YmlConfigMapper().createObjectMapper().registerModule(module);
    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    return objectMapper;
  }
  
  static class Chars {
    Character wrapper;
    char primitive;
  }
  
  static class Strings {
    List<String> values;
  }
}
