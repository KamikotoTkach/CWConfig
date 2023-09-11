package tkachgeek.config.yaml.newCommands;

import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import ru.cwcode.commands.ArgumentSet;
import ru.cwcode.commands.Command;
import ru.cwcode.commands.paperplatform.executor.Executor;
import tkachgeek.config.yaml.YmlConfigManager;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PaperFlushCommand {
  public static Command get(YmlConfigManager manager) {
    return new Command("flush", "$*").arguments(
       new ArgumentSet(new ConfigFlush(manager), "", new PaperConfigsArg(manager))
    );
  }
  
  private static class ConfigFlush extends Executor {
    YmlConfigManager manager;
    
    public ConfigFlush(YmlConfigManager manager) {
      this.manager = manager;
    }
    
    @Override
    public void executeForPlayer() throws MessageReturn {
      Audience audience = sender.getAudience();
      
      if (audience instanceof CommandSender) {
        Logger.getLogger(((CommandSender) audience).getName()).log(Level.INFO, "Инициировал очистку конфига " + argS(0));
      }
      
      manager.flush(argS(0), audience);
    }
  }
}
