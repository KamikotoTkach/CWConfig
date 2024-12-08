package ru.cwcode.tkach.config.paper.data.item.loot;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.cwcode.cwutils.numbers.Rand;

public class MaterialLootData extends AbstractLootData {
  protected Material material;
  
  public MaterialLootData() {
  }
  
  public MaterialLootData(Material material) {
    this.material = material;
  }
  
  public Material getMaterial() {
    return material;
  }
  
  public void setMaterial(Material material) {
    this.material = material;
  }
  
  @Override
  public ItemStack getItem() {
    return new ItemStack(material, Rand.ofInt(min, max));
  }
}
