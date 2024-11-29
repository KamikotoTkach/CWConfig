package ru.cwcode.tkach.config.paper.data.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import ru.cwcode.cwutils.collections.CollectionUtils;
import ru.cwcode.tkach.config.paper.data.AbstractProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public class LivingEntityProperty<V> extends AbstractProperty<LivingEntity, V> {
  static Map<String, LivingEntityProperty<?>> properties = new HashMap<>();
  static Logger logger = Logger.getLogger(LivingEntityProperty.class.getSimpleName());
  
  static {
    properties.put("remove_when_far_away", new LivingEntityProperty<>(boolean.class, LivingEntity::setRemoveWhenFarAway));
    properties.put("ai", new LivingEntityProperty<>(boolean.class, LivingEntity::setAI));
    properties.put("collidable", new LivingEntityProperty<>(boolean.class, LivingEntity::setCollidable));
    properties.put("custom_name_visible", new LivingEntityProperty<>(boolean.class, Entity::setCustomNameVisible));
    properties.put("can_pickup_items", new LivingEntityProperty<>(boolean.class, LivingEntity::setCanPickupItems));
    properties.put("invisible", new LivingEntityProperty<>(boolean.class, LivingEntity::setInvisible));
    properties.put("invulnerable", new LivingEntityProperty<>(boolean.class, LivingEntity::setInvulnerable));
    properties.put("glowing", new LivingEntityProperty<>(boolean.class, Entity::setGlowing));
    properties.put("gravity", new LivingEntityProperty<>(boolean.class, Entity::setGravity));
    properties.put("persistent", new LivingEntityProperty<>(boolean.class, Entity::setPersistent));
    properties.put("silent", new LivingEntityProperty<>(boolean.class, Entity::setSilent));
  }
  
  protected LivingEntityProperty(Class<V> type, BiConsumer<LivingEntity, V> propertySetter) {
    super(type, propertySetter);
  }
  
  public static LivingEntityProperty<?> get(String key) {
    return properties.get(key);
  }
  
  public static Map<String, LivingEntityProperty<?>> getProperties() {
    return properties;
  }
  
  public static void apply(LivingEntity entity, Map<String, ?> properties) {
    properties.forEach((key, value) -> {
      LivingEntityProperty<?> prop = get(key);
      if (prop != null) {
        prop.apply(entity, value);
      } else {
        logger.warning("No such living entity property: %s, available properties: %s".formatted(key, CollectionUtils.toString(List.of(properties.keySet()))));
      }
    });
  }
}
