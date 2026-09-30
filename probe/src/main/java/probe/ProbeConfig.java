package probe;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfig;
import ru.cwcode.tkach.locale.ExtraMessage;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.TitleMessage;
import ru.cwcode.tkach.locale.data.SoundData;

public class ProbeConfig extends YmlConfig {
  public String text = "default";
  public int number = 5;
  public Message message = new Message("<green>Hello <name>");
  public ExtraMessage extra = extraMessage();
  public ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
  public PotionEffectType effect = PotionEffectType.SPEED;
  public Enchantment enchantment = Enchantment.DURABILITY;
  public NamespacedKey key = NamespacedKey.minecraft("probe");
  public Vector vector = new Vector(1, 2, 3);
  public Color color = Color.fromRGB(10, 20, 30);

  private static ExtraMessage extraMessage() {
    ExtraMessage extra = new ExtraMessage("<yellow>extra <name>");
    extra.setSound(new SoundData("ui.button.click"));
    extra.setTitle(new TitleMessage("<red>title", "<gray>subtitle <name>"));
    return extra;
  }
}
