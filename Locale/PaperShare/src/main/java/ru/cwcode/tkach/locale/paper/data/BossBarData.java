package ru.cwcode.tkach.locale.paper.data;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import ru.cwcode.tkach.locale.Message;
import ru.cwcode.tkach.locale.Placeholders;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public class BossBarData implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  
  protected Message title = new Message();
  protected BossBar.Color color = BossBar.Color.WHITE;
  protected BossBar.Overlay overlay = BossBar.Overlay.NOTCHED_12;
  protected Set<BossBar.Flag> flags = Set.of();
  
  public void setTitle(Message title) {
    this.title = title;
  }
  
  public void setColor(BossBar.Color color) {
    this.color = color;
  }
  
  public void setOverlay(BossBar.Overlay overlay) {
    this.overlay = overlay;
  }
  
  public void setFlags(Set<BossBar.Flag> flags) {
    this.flags = flags;
  }
  
  public Message getTitle() {
    return title;
  }
  
  public BossBar.Color getColor() {
    return color;
  }
  
  public BossBar.Overlay getOverlay() {
    return overlay;
  }
  
  public Set<BossBar.Flag> getFlags() {
    return flags;
  }
  
  public BossBar create(Audience audience, Placeholders placeholders) {
    return BossBar.bossBar(title.get(audience, placeholders), 1, color, overlay, flags);
  }
  
  public BossBar create(Placeholders placeholders) {
    return BossBar.bossBar(title.get(placeholders), 1, color, overlay, flags);
  }
  
  public BossBar create() {
    return BossBar.bossBar(title.get(), 1, color, overlay, flags);
  }
}
