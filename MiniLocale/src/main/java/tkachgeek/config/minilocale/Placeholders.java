package tkachgeek.config.minilocale;

import net.kyori.adventure.text.minimessage.Template;

import java.util.ArrayList;
import java.util.List;

public class Placeholders {
  private final List<Template> templates = new ArrayList<>();
  
  public Placeholders(String key, String value) {
    add(key, value);
  }
  
  public Placeholders(Template template) {
    add(template);
  }
  
  public Placeholders add(String key, String value) {
    templates.add(Template.of(key, value));
    return this;
  }
  
  public Placeholders add(Template template) {
    templates.add(template);
    return this;
  }
  
  public List<Template> getTemplates() {
    return templates;
  }
}
