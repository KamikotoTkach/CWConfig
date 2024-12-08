package ru.cwcode.tkach.config.paper.data.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import ru.cwcode.tkach.config.annotation.Fancy;
import ru.cwcode.tkach.locale.Message;

import java.util.Map;

public class EntityData {
  protected Message name = new Message("Custom name");
  
  protected EntityType entityType = EntityType.ZOMBIE;
  
  protected Map<Attribute, Double> attributes = Map.of(Attribute.GENERIC_MAX_HEALTH, 20.0);
  
  @Fancy
  protected Map<EquipmentSlot, ItemStack> equipment = Map.of(EquipmentSlot.HAND, new ItemStack(Material.STICK));
  
  protected CreatureSpawnEvent.SpawnReason spawnReason = CreatureSpawnEvent.SpawnReason.CUSTOM;
  protected Map<String, Object> properties = Map.of();
  
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
  
  public Message getName() {
    return name;
  }
  
  public void setName(Message name) {
    this.name = name;
  }
  
  public EntityType getEntityType() {
    return entityType;
  }
  
  public void setEntityType(EntityType entityType) {
    this.entityType = entityType;
  }
  
  public Map<Attribute, Double> getAttributes() {
    return attributes;
  }
  
  public void setAttributes(Map<Attribute, Double> attributes) {
    this.attributes = attributes;
  }
  
  public Map<EquipmentSlot, ItemStack> getEquipment() {
    return equipment;
  }
  
  public void setEquipment(Map<EquipmentSlot, ItemStack> equipment) {
    this.equipment = equipment;
  }
  
  public CreatureSpawnEvent.SpawnReason getSpawnReason() {
    return spawnReason;
  }
  
  public void setSpawnReason(CreatureSpawnEvent.SpawnReason spawnReason) {
    this.spawnReason = spawnReason;
  }
  
  public Map<String, Object> getProperties() {
    return properties;
  }
  
  public void setProperties(Map<String, Object> properties) {
    this.properties = properties;
  }
}
