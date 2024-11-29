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
  String id = NanoID.randomNanoId(5);
  double chance = 0;
  int min = 1;
  int max = 2;
  
  @Override
  public String getKey() {
    return id;
  }
  
  public boolean testChance() {
    return Rand.testChance(chance);
  }
  
  public abstract ItemStack getItem();
}
