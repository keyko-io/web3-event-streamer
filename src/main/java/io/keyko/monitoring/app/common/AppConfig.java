package io.keyko.monitoring.app.common;

import com.typesafe.config.Config;
import io.keyko.monitoring.config.StreamerConfig;

public class AppConfig extends StreamerConfig {

  private static final String ACCOUNTS_AGGREGATION_TOPIC = "kafka.accounts-aggregation-topic";
  private String accountsAggregationTopic;

  public AppConfig(Config config) {

    super(config);
    this.setAccountsAggregationTopic(config.getString(ACCOUNTS_AGGREGATION_TOPIC));

  }

  public String getAccountsAggregationTopic() {
    return accountsAggregationTopic;
  }

  public void setAccountsAggregationTopic(String accountsAggregationTopic) {
    this.accountsAggregationTopic = accountsAggregationTopic;
  }

}
