package io.keyko.monitoring.cache;

import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.StorageType;
import org.infinispan.eviction.EvictionType;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.persistence.mongodb.configuration.MongoDBStoreConfigurationBuilder;

import java.util.concurrent.TimeUnit;

public class InfinispanCacheProvider {

  private static Logger log = Logger.getLogger(InfinispanCacheProvider.class);

  private static EmbeddedCacheManager cacheManager;
  private static InfinispanCacheProvider infinispanCacheProvider = null;
  private static Boolean enabled = false;
  private static Integer expiryTime = null;
  private static TimeUnit expiryTimeUnit = null;
  private static Boolean useMongoDb = false;
  private static String mongodbUrl;
  private static Long inMemoryMaxSize;

  private InfinispanCacheProvider(Boolean isEnabled, TimeUnit timeUnit, Integer time, Boolean useMongo, String mongoUrl, Long maxSize) {

    if (isEnabled)
      cacheManager = new DefaultCacheManager();
    enabled = isEnabled;
    expiryTime = time;
    expiryTimeUnit = timeUnit;
    useMongoDb = useMongo;
    mongodbUrl = mongoUrl;
    inMemoryMaxSize = maxSize;
  }


  public static void initCacheManagerService(Boolean isEnabled, TimeUnit timeUnit, Integer time,  Boolean useMongo, String mongoUrl, Long maxSize) {

    if (infinispanCacheProvider == null)
        infinispanCacheProvider = new InfinispanCacheProvider(isEnabled, timeUnit, time, useMongo, mongoUrl, maxSize);

  }

  public static Boolean isCacheEnabled(){
    return enabled;
  }

  public static void finishCacheManager() {
    if (enabled)
      cacheManager.stop();
  }

  public static <T> Cache<String, T> getCache(String name, Class<T> type){

    if (!enabled)
      return null;

    ConfigurationBuilder config = new ConfigurationBuilder();
    config
      .expiration()
      .lifespan(expiryTime, expiryTimeUnit);

    if (useMongoDb)
     config
      .persistence().addStore(MongoDBStoreConfigurationBuilder.class)
      .connectionURI( mongodbUrl)
      .collection("etherscan_cache");
    else
    // Enable memory based eviction
    config
      .memory()
      .storageType(StorageType.BINARY)
      .evictionType(EvictionType.MEMORY)
      .size(inMemoryMaxSize);


    cacheManager.defineConfiguration(name, config.build());
    Cache<String, T> cache = cacheManager.getCache(name);

    return cache;

  }

}
