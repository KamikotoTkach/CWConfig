package ru.cwcode.tkach.config;

import ru.cwcode.tkach.config.base.manager.ConfigLoader;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
  private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{([\\w.]+)}");
  
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
    }
  }
  
  public static ConfigLoader.Preprocessor getEnvironmentReplacerPreprocessor() {
    return (input) -> {
      if (input == null || input.isEmpty()) {
        return input;
      }
      
      Matcher matcher = PLACEHOLDER_PATTERN.matcher(input);
      StringBuilder result = new StringBuilder();
      
      while (matcher.find()) {
        String envKey = matcher.group(1);
        String envValue = System.getenv(envKey);
        
        matcher.appendReplacement(result, envValue != null ? Matcher.quoteReplacement(envValue) : matcher.group(0));
      }
      
      matcher.appendTail(result);
      
      return result.toString();
    };
  }
}
