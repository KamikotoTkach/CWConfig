package ru.cwcode.tkach.config.paper.data.item.loot;

import org.bukkit.inventory.ItemStack;
import ru.cwcode.cwutils.numbers.Rand;
import ru.cwcode.tkach.config.annotation.Fancy;

public class ItemStackLootData extends AbstractLootData {
  @Fancy
  protected ItemStack item;
  
  public ItemStackLootData() {
  }
  
  public ItemStackLootData(ItemStack item) {
    this.item = item;
  }
  
  public ItemStackLootData(ItemStack item, double chance) {
    this.item = item;
    this.chance = chance;
  }
  
  public ItemStackLootData(ItemStack item, double chance, int min, int max) {
    this.item = item;
    this.chance = chance;
    this.min = min;
    this.max = max;
  }
  
  public void setItem(ItemStack item) {
    this.item = item;
  }
  
  @Override
  public ItemStack getItem() {
    return item.clone().asQuantity(Rand.ofInt(min, max));
  }
}
