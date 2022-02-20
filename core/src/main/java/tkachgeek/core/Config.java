package tkachgeek.core;

public abstract class Config {
  transient public String path;
  public transient boolean saveOnDisabling = true;
  
  void setSaveOnDisabling(boolean bool) {
    saveOnDisabling = bool;
  }
  
  abstract public void store();
  abstract public void store(String path);
  abstract public String toString();
}
