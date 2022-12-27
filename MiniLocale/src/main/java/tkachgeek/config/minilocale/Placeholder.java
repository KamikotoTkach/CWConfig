package tkachgeek.config.minilocale;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class Placeholder {
    public static Placeholders add(String key, String value) {
        return new Placeholders(TagResolver.resolver(key, Tag.preProcessParsed(value)));
    }
    public static Placeholders add(String key, Component value) {
        return new Placeholders(TagResolver.resolver(key, Tag.inserting(value)));
    }

    public static Placeholders add(TagResolver resolver) {
        return new Placeholders(resolver);
    }
}
