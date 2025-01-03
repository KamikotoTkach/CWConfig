package ru.cwcode.tkach.config.paper.data.item.loot;

import org.bukkit.inventory.ItemStack;
import ru.cwcode.cwutils.numbers.Rand;
import ru.cwcode.cwutils.text.nanoid.NanoID;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.annotation.JsonSubTypes;
import ru.cwcode.tkach.config.relocate.com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.cwcode.tkach.config.repository.RepositoryEntry;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MaterialLootData.class, name = "material"),
  @JsonSubTypes.Type(value = ItemStackLootData.class, name = "item"),
})
public abstract class AbstractLootData implements RepositoryEntry<String> {
  protected String id = NanoID.randomNanoId(5);
  protected double chance = 0;
  protected int min = 1;
  protected int max = 2;
  
  public abstract ItemStack getItem();
  
  @Override
  public String getKey() {
    return id;
  }
  
  public boolean testChance() {
    return Rand.testChance(chance);
  }
  
  public void setKey(String id) {
    this.id = id;
  }
  
  public double getChance() {
    return chance;
  }
  
  public void setChance(double chance) {
    this.chance = chance;
  }
  
  public int getMin() {
    return min;
  }
  
  public void setMin(int min) {
    this.min = min;
  }
  
  public int getMax() {
    return max;
  }
  
  public void setMax(int max) {
    this.max = max;
  }
}
