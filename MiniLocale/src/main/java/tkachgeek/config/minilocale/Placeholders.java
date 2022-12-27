package tkachgeek.config.minilocale;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.List;

public class Placeholders {
    private final List<TagResolver> resolvers = new ArrayList<>();

    public Placeholders(String key, String value) {
        add(key, value);
    }

    public Placeholders(TagResolver resolver) {
        add(resolver);
    }
    
    public Placeholders add(String key, String value) {
        resolvers.add(TagResolver.resolver(key.toLowerCase(), Tag.preProcessParsed(value)));
        return this;
    }
    
    public Placeholders add(TagResolver TagResolver) {
        resolvers.add(TagResolver);
        return this;
    }
    
    public Placeholders add(String key, Component value) {
        resolvers.add(TagResolver.resolver(key.toLowerCase(), Tag.inserting(value)));
        return this;
    }
    
    public TagResolver[] getResolvers() {
        return resolvers.toArray(new TagResolver[0]);
    }
}
