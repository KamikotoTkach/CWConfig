package ru.cwcode.tkach.locale;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LegacyToMiniMessageReplacer {
  public static final Pattern LEGACY_RGB = Pattern.compile("§x§([0-9A-Fa-f])§([0-9A-Fa-f])§([0-9A-Fa-f])§([0-9A-Fa-f])§([0-9A-Fa-f])§([0-9A-Fa-f])");
  public static final Pattern LEGACY_STYLE = Pattern.compile("[§&]([0-9A-Fa-fklnmorKLMNOR])");
  public static final Map<String, String> replacements = new HashMap<>() {{
    put("0", "<black>");
    put("1", "<dark_blue>");
    put("2", "<dark_green>");
    put("3", "<dark_aqua>");
    put("4", "<dark_red>");
    put("5", "<dark_purple>");
    put("6", "<gold>");
    put("7", "<gray>");
    put("8", "<dark_gray>");
    put("9", "<blue>");
    put("a", "<green>");
    put("b", "<aqua>");
    put("c", "<red>");
    put("d", "<light_purple>");
    put("e", "<yellow>");
    put("f", "<white>");
    put("k", "<o>");
    put("l", "<b>");
    put("m", "<st>");
    put("n", "<u>");
    put("o", "<i>");
    put("r", "<reset>");
  }};
  
  public static String replace(String message) {
    return replaceLegacyStyle(replaceLegacyRgb(message));
  }
  
  public static String replaceLegacyRgb(String message) {
    Matcher matcher = LEGACY_RGB.matcher(message);
    StringBuilder result = new StringBuilder();
    
    while (matcher.find()) {
      String color = "<#" + matcher.group(1) + matcher.group(2) + matcher.group(3) +
                     matcher.group(4) + matcher.group(5) + matcher.group(6) + ">";
      matcher.appendReplacement(result, color);
    }
    matcher.appendTail(result);
    
    return result.toString();
  }
  
  public static String replaceLegacyStyle(String message) {
    Matcher matcher = LEGACY_STYLE.matcher(message);
    StringBuilder result = new StringBuilder();
    
    while (matcher.find()) {
      matcher.appendReplacement(result, replacements.getOrDefault(matcher.group(1), ""));
    }
    matcher.appendTail(result);
    
    return result.toString();
  }
}
