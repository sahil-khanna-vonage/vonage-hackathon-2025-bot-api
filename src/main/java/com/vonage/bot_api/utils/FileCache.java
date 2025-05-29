package com.vonage.bot_api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;

public class FileCache {
  private static final String CACHE_DIR = ".cache";
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final Logger logger = LoggerFactory.getLogger(FileCache.class);

  private FileCache() {
    // Do Nothing
  }

  private static File getCacheFile(String fileName) {
    return new File(CACHE_DIR, fileName);
  }

  public static void writeCache(String fileName, Object obj) {
    try {
      File dir = new File(CACHE_DIR);
      if (!dir.exists())
        dir.mkdirs();

      File file = getCacheFile(fileName);
      mapper.writerWithDefaultPrettyPrinter().writeValue(file, obj);
    } catch (IOException e) {
      logger.error("Failed to write cache to {}: {}", fileName, e.getMessage());
    }
  }

  public static <T> Optional<T> readCache(String fileName, TypeReference<T> typeRef) {
    try {
      File file = getCacheFile(fileName);
      if (!file.exists())
        return Optional.empty();
      return Optional.of(mapper.readValue(file, typeRef));
    } catch (IOException e) {
      logger.error("Failed to read cache from {}: {}", fileName, e.getMessage());
      return Optional.empty();
    }
  }

  public static void invalidateCache(String fileName) {
    try {
      if ("*".equals(fileName)) {
        File dir = new File(CACHE_DIR);
        if (dir.exists()) {
          try (Stream<Path> paths = Files.walk(dir.toPath())) {
            paths.map(Path::toFile)
                .sorted((a, b) -> b.compareTo(a)) // delete children before parent
                .forEach(File::delete);
          }
        }
      } else {
        File file = getCacheFile(fileName);
        if (file.exists() && !file.delete()) {
          logger.error("Failed to delete file {}", file.getAbsolutePath());
        }
      }
    } catch (IOException e) {
      logger.error("Failed to invalidate cache {}: {}", fileName, e.getMessage(), e);
    }
  }  

}
