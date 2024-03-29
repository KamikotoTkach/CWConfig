package ru.cwcode.tkach.config.jackson.yaml;

import ru.cwcode.tkach.config.base.Config;

public abstract class YmlConfig extends Config<YmlConfig> {
  public String[] header() {
    return new String[]{
       "    .oooooo.   oooo                      oooo                                            oooo        ",
       "   d8P'  `Y8b  `888                      `888                                            `888        ",
       "  888           888   .ooooo.   .ooooo.   888  oooo  oooo oooo    ooo  .ooooo.  oooo d8b  888  oooo  ",
       "  888           888  d88' `88b d88' `\"Y8  888 .8P'    `88. `88.  .8'  d88' `88b `888\"\"8P  888 .8P'   ",
       "  888           888  888   888 888        888888.      `88..]88..8'   888   888  888      888888.    ",
       "  `88b    ooo   888  888   888 888   .o8  888 `88b.     `888'`888'    888   888  888      888 `88b.  ",
       "   `Y8bood8P'  o888o `Y8bod8P' `Y8bod8P' o888o o888o     `8'  `8'     `Y8bod8P' d888b    o888o o888o ",
       "",
       "  Студия разработки плагинов для майнкрафт Clockwork vk.com/cwcode",
       ""
    };
  }
}
