package com.ourtimesheet.multitenant;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Created by hassan on 3/6/16.
 */
public class MultiTenantMongoDbFactory extends SimpleMongoDbFactory {

  private static final Logger logger = LoggerFactory.getLogger(MultiTenantMongoDbFactory.class);

  private static final ThreadLocal<String> dbName = new ThreadLocal<>();

  private final String defaultDatabaseName;

  public MultiTenantMongoDbFactory(MongoClient mongoClient, String databaseName) {
    super(mongoClient, databaseName);
    this.defaultDatabaseName = databaseName;
  }

  public static void setDatabaseNameForCurrentThread(final String databaseName) {
    dbName.set(databaseName);
  }

  public static void clearDatabaseNameForCurrentThread() {
    dbName.remove();
  }

  @Override
  public DB getDb() {
    final String tlName = dbName.get();
    final String dbToUse = (tlName != null ? tlName : this.defaultDatabaseName);
    return super.getDb(dbToUse);
  }

}