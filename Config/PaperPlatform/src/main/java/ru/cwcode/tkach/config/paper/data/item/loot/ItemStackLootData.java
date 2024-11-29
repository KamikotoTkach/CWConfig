package ru.cwcode.tkach.config.paper.data.item.loot;

import org.bukkit.inventory.ItemStack;
import ru.cwcode.cwutils.numbers.Rand;
import ru.cwcode.tkach.config.annotation.Fancy;

public class ItemStackLootData extends AbstractLootData {
  @Fancy
  ItemStack item;
  
  public ItemStackLootData() {
  }
  
  public ItemStackLootData(ItemStack item) {
    this.item = item;
  }
  
  public ItemStackLootData(ItemStack item, double chance) {
    this.item = item;
    this.chance = chance;
  }
  
  @Override
  public ItemStack getItem() {
    return item.clone().asQuantity(Rand.ofInt(min, max));
  }
}
