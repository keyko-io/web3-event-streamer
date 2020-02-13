package io.keyko.monitoring.examples.celo;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.keyko.monitoring.examples.basic.BasicStreamManager;
import io.keyko.monitoring.examples.celo.model.AccountCreatedAggregation;
import io.keyko.monitoring.postprocessing.Output;
import io.keyko.monitoring.preprocessing.Filters;
import io.keyko.monitoring.preprocessing.Transformations;
import io.keyko.monitoring.schemas.AlertEvent;
import io.keyko.monitoring.schemas.BlockEvent;
import io.keyko.monitoring.schemas.ContractEvent;
import io.keyko.monitoring.schemas.EventBlock;
import io.keyko.monitoring.serde.JsonPOJOSerde;
import io.keyko.monitoring.stream.BaseStreamManager;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CeloStreamManager extends BaseStreamManager {

  CeloConfig celoConfig;

  public CeloStreamManager(CeloConfig config) {
    super(config);
    this.celoConfig = config;
  }

  @Override
  protected void configureSerdes(String schemaRegistryUrl) {
   CeloSerdes.configureAllSerdes(schemaRegistryUrl);
  }

  @Override
  protected void processStreams(KStream<String, ContractEvent> eventStream, KTable<String, BlockEvent> blockTable) {

    final KStream<String, ContractEvent> eventAvroStream = Filters.filterConfirmed(eventStream);
    KStream<String, EventBlock> eventBlockStream = Transformations.joinEventWithBlock(eventAvroStream, blockTable);
    Output.splitByEvent(eventBlockStream);

    /* Celo demo */

    List<String> accountsTopics = Arrays.asList("AccountCreated".toLowerCase(), "ValidatorSignerAuthorized".toLowerCase());//, "VoteSignerAuthorized".toLowerCase(), "AttestationSignerAuthorized".toLowerCase());
    KStream<String, AccountCreatedAggregation> accountsCreatedDayStream = CeloProcessor.accountDailyAggregation(accountsTopics, builder);
    accountsCreatedDayStream.to(celoConfig.getAccountsAggregationTopic(), Produced.with(Serdes.String(), new JsonPOJOSerde<AccountCreatedAggregation>(AccountCreatedAggregation.class)));

    KStream<String, AlertEvent> alertEventKStream = CeloProcessor.alertNoEpochRewardsDistributed(builder, Collections.singletonList("EpochRewardsDistributedToVoters".toLowerCase()));
    alertEventKStream.to(celoConfig.getAlertsTopic(), Produced.with(Serdes.String(), CeloSerdes.getAlertAvroSerde()));

  }

  public static void main(final String[] args) throws Exception {

    Config config = args.length > 0 ? ConfigFactory.load(args[0]) : ConfigFactory.load();

    CeloConfig celoConfig = new CeloConfig(config);
    new BasicStreamManager(celoConfig).initStream();

  }
}
