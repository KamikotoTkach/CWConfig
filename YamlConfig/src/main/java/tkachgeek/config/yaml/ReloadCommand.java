package tkachgeek.config.yaml;

import tkachgeek.commands.command.ArgumentSet;
import tkachgeek.commands.command.Command;
import tkachgeek.commands.command.arguments.executor.Executor;
import tkachgeek.tkachutils.messages.MessageReturn;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReloadCommand {
  public static Command get() {
    return new Command("reload", "*").arguments(new ArgumentSet(new ConfigReload(), "", new ConfigsArg()));
  }
  
  private static class ConfigReload extends Executor {
    @Override
    public void executeForPlayer() throws MessageReturn {
      Logger.getLogger(sender().getName()).log(Level.INFO, "Инициировал перезагрузку конфигу " + argS(0));
      
      YmlConfigManager.reloadByCommand(argS(0), sender());
    }
  }
}
