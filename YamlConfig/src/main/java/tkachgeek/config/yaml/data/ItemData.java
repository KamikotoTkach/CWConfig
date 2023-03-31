package tkachgeek.config.yaml.data;

import org.bukkit.Material;

public class ItemData {
  public Material material;
  String name;
  String description;
  
  public ItemData() {
  }
  
  public ItemData(Material material, String name, String description) {
    this.material = material;
    this.name = name;
    this.description = description;
  }
  
  public Material getMaterial() {
    return material;
  }
  
  public void setMaterial(Material material) {
    this.material = material;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
}
