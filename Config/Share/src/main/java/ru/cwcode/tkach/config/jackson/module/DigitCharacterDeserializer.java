package ru.cwcode.tkach.config.jackson.module;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;

import java.io.IOException;

/**
 * Jackson reads a number into a char field as a code point, so {@code menuChar: 1} became {@code '\u0001'} — and a
 * YAML without quotes on numbers wrote the char {@code '1'} exactly like that. A single digit is read as that digit.
 */
public class DigitCharacterDeserializer extends NumberDeserializers.CharacterDeserializer {
  public static final DigitCharacterDeserializer WRAPPER = new DigitCharacterDeserializer(Character.class, null);
  public static final DigitCharacterDeserializer PRIMITIVE = new DigitCharacterDeserializer(Character.TYPE, '\0');
  
  private DigitCharacterDeserializer(Class<Character> type, Character nullValue) {
    super(type, nullValue);
  }
  
  @Override
  public Character deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    if (p.hasToken(JsonToken.VALUE_NUMBER_INT)) {
      String text = p.getText();
      if (text.length() == 1) return text.charAt(0);
    }
    return super.deserialize(p, ctxt);
  }
}