package ru.cwcode.tkach.config.paper.data.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Boss;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import ru.cwcode.tkach.config.annotation.Fancy;
import ru.cwcode.tkach.config.paper.data.item.loot.AbstractLootData;
import ru.cwcode.tkach.config.paper.data.item.loot.ItemStackLootData;
import ru.cwcode.tkach.locale.Message;

import java.util.List;
import java.util.Map;

public class EntityData {
  Message name = new Message("Custom name");
  
  EntityType entityType = EntityType.ZOMBIE;
  
  Map<Attribute, Double> attributes = Map.of(Attribute.GENERIC_MAX_HEALTH, 20.0);
  
  @Fancy
  Map<EquipmentSlot, ItemStack> equipment = Map.of(EquipmentSlot.HAND, new ItemStack(Material.STICK));

  CreatureSpawnEvent.SpawnReason spawnReason = CreatureSpawnEvent.SpawnReason.CUSTOM;
  Map<String, Object> properties = Map.of();
  
  public LivingEntity spawn(Location location) {
    return (LivingEntity) location.getWorld().spawnEntity(location, entityType, spawnReason, entity -> {
      
      if (name != null) {
        entity.customName(name.get());
      }
      
      if (!(entity instanceof LivingEntity livingEntity)) return;
      
      attributes.forEach((attribute, value) -> {
        AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
        if (attributeInstance == null) return;
        attributeInstance.setBaseValue(value);
        
        if (attribute == Attribute.GENERIC_MAX_HEALTH) {
          livingEntity.setHealth(attributeInstance.getValue());
        }
      });
      
      EntityEquipment eq = livingEntity.getEquipment();
      if (eq != null) equipment.forEach(eq::setItem);
      
      LivingEntityProperty.apply(livingEntity, properties);
    });
  }
}
