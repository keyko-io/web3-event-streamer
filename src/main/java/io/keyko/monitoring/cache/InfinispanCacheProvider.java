package io.keyko.monitoring.cache;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.manager.DefaultCacheManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class InfinispanCacheProvider {

  private static EmbeddedCacheManager cacheManager;
  private static InfinispanCacheProvider cacheManagerProvider = null;
  private static Integer expiryTime = null;
  private static TimeUnit expiryTimeUnit = null;

  private InfinispanCacheProvider(String serializerConfigPath) throws IOException {

    ClassLoader classLoader = InfinispanCacheProvider.class.getClassLoader();
    cacheManager = new DefaultCacheManager(classLoader.getResourceAsStream(serializerConfigPath));
  }

  private InfinispanCacheProvider()  {
    cacheManager = new DefaultCacheManager();
  }


  public static void initCacheManagerService(TimeUnit timeUnit, Integer time) {

    if (cacheManagerProvider ==null)
      cacheManagerProvider = new InfinispanCacheProvider();

    expiryTime = time;
    expiryTimeUnit = timeUnit;
  }

  public static void initCacheManagerService(String serializerConfigPath, TimeUnit timeUnit, Integer time) throws IOException {

    if (cacheManagerProvider ==null)
      cacheManagerProvider = new InfinispanCacheProvider(serializerConfigPath);

    expiryTime = time;
    expiryTimeUnit = timeUnit;
  }

  public static void finishCacheManager() {
    cacheManager.stop();
  }

  public static <T> Cache<String, T> getCache(String name, Class<T> type){

    ConfigurationBuilder config = new ConfigurationBuilder();
    config.expiration().lifespan(expiryTime, expiryTimeUnit);

    cacheManager.defineConfiguration(name, config.build());
    Cache<String, T> cache = cacheManager.getCache(name);

    return cache;

  }

}
