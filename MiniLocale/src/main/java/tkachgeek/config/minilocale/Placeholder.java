package tkachgeek.config.minilocale;

import net.kyori.adventure.text.minimessage.Template;

public class Placeholder {
  public static Placeholders add(String key, String value) {
    return new Placeholders(Template.of(key, value));
  }
  
  public static Placeholders add(Template template) {
    return new Placeholders(template);
  }
}
