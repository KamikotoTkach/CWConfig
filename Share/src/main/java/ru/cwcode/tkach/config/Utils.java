package ru.cwcode.tkach.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class Utils {
  public static String readString(Path path) {
    try {
      return Files.readString(path, StandardCharsets.UTF_8);
    } catch (IOException ignored) {
    }
    return "";
  }
  
  public static boolean writeString(Path path, String text) {
    try {
      if (!Files.exists(path)) {
        createParentDirs(path.toFile());
      }
      
      Files.writeString(path, text, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
  
  public static void createParentDirs(File file) throws IOException {
    if (file == null) return;
    File parent = file.getCanonicalFile().getParentFile();
    if (parent == null) {
      return;
    }
    
    parent.mkdirs();
    
    if (!parent.isDirectory()) {
      throw new IOException("Unable to create parent directories of " + file);
    }
  }
  
  public static <T> Optional<T> getNewInstance(Class<T> type) {
    try {
      Constructor<T> declaredConstructor = type.getDeclaredConstructor();
      declaredConstructor.setAccessible(true);
      return Optional.of(declaredConstructor.newInstance());
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    
    return Optional.empty();
  }
  
  public static void copy(Path original, Path backup) {
    try {
      Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
