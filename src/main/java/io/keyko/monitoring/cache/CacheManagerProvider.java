package io.keyko.monitoring.cache;


import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class CacheManagerProvider {


  private static CachingProvider cachingProvider = Caching.getCachingProvider();
  private static CacheManager cacheManager;
  private static CacheManagerProvider cacheManagerProvider = null;
  private static Integer expiryTime = null;
  private static TimeUnit expiryTimeUnit = null;

  private CacheManagerProvider(String serializerConfigPath) throws URISyntaxException {

    ClassLoader classLoader = CacheManagerProvider.class.getClassLoader();
    cacheManager = cachingProvider.getCacheManager(classLoader.getResource(serializerConfigPath).toURI(), classLoader);

  }

  private CacheManagerProvider()  {
    cacheManager = cachingProvider.getCacheManager();
  }


  public static void initCacheManagerService(TimeUnit timeUnit, Integer time) {

    if (cacheManagerProvider ==null)
      cacheManagerProvider = new CacheManagerProvider();

    expiryTime = time;
    expiryTimeUnit = timeUnit;
  }

  public static void initCacheManagerService(String serializerConfigPath, TimeUnit timeUnit, Integer time) throws URISyntaxException {

    if (cacheManagerProvider ==null)
      cacheManagerProvider = new CacheManagerProvider(serializerConfigPath);

    expiryTime = time;
    expiryTimeUnit = timeUnit;
  }

  public static void finishCacheManager(){
    cacheManager.close();
  }

  public static <T> Cache<String, T> getCache(String name, Class<T> type){

    Factory expiryPolicyFactory = CreatedExpiryPolicy.factoryOf(new Duration(expiryTimeUnit, expiryTime));
    MutableConfiguration<String, T> mutableConfiguration = new MutableConfiguration<>();
    mutableConfiguration.setExpiryPolicyFactory(expiryPolicyFactory);

    return cacheManager.createCache(name, mutableConfiguration);

  }

}
