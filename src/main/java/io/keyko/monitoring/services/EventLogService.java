package io.keyko.monitoring.services;

import io.keyko.monitoring.exceptions.AbiNotFoundException;
import io.keyko.monitoring.exceptions.EtherscanException;
import io.keyko.monitoring.exceptions.EventFromLogException;
import io.keyko.monitoring.model.ContractData;
import io.keyko.monitoring.schemas.*;
import org.apache.log4j.Logger;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.AbiDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventLogService {

  private static Logger log = Logger.getLogger(EventLogService.class);

  public static EventRecord getEventFromLog(LogRecord log, String getContractAbiUrl, String apiKey) throws EventFromLogException {

    String contractAddress = log.getDetails().getAddress();
    ContractData contractData = null;

    try {
      contractData = EtherscanService.getContractData(getContractAbiUrl, contractAddress, apiKey);
    } catch (EtherscanException e) {
        throw new EventFromLogException("Error getting the data from Contract", e);
    }

    List<AbiDefinition> eventsAbiDefinition = getAbiDefinitionByType("event", contractData.getAbiDefinitionList());

    AbiDefinition eventAbi = null;
    try {
      eventAbi = getEventAbi(log, eventsAbiDefinition);
    } catch (AbiNotFoundException e) {
        throw new EventFromLogException("Error getting the ABI Definition for the event", e);
    }

    Event event = getEventFromAbi(eventAbi);

    List<Type> indexed = getIndexedParametersFromLog(event, log);
    List<Type> nonIndexed = getNonIndexedParametersFromLog(event, log);

    return generateEventRecord(contractData.getName(),
                                                  event.getName(),
                                                  getEventHashFromAbi(eventAbi),
                                                  web3ToMonitoringType(indexed, eventAbi, true),
                                                  web3ToMonitoringType(nonIndexed, eventAbi, false),
                                                  log);
  }


  private static EventRecord generateEventRecord(String contractName, String eventName, String eventHash, List<Object> indexedParameters, List<Object> nonIndexedParameters, LogRecord log){

    EventRecord eventRecord = new EventRecord();
    eventRecord.setType("CONTRACT_EVENT");
    eventRecord.setRetries(log.getRetries());
    eventRecord.setId(log.getDetails().getId());

    EventDetailsRecord eventDetails = new EventDetailsRecord();
    eventDetails.setContractName(contractName);
    eventDetails.setBlockNumber(log.getDetails().getBlockNumber());
    eventDetails.setId(log.getDetails().getId());
    eventDetails.setName(eventName);
    eventDetails.setStatus(ContractEventStatus.CONFIRMED); // ??
    eventDetails.setBlockHash(log.getDetails().getBlockHash());
    eventDetails.setAddress(log.getDetails().getAddress());
    eventDetails.setEventSpecificationSignature(eventHash);
    eventDetails.setFilterId(eventName);
    eventDetails.setLogIndex(log.getDetails().getLogIndex());
    eventDetails.setNetworkName(log.getDetails().getNetworkName());
    eventDetails.setNodeName(log.getDetails().getNodeName());
    eventDetails.setTransactionHash(log.getDetails().getTransactionHash());

    eventDetails.setIndexedParameters(indexedParameters);
    eventDetails.setNonIndexedParameters(nonIndexedParameters);

    eventRecord.setDetails(eventDetails);

    return eventRecord;

  }

  private static List<Object> web3ToMonitoringType(List<Type> parameters,  AbiDefinition eventAbi, Boolean indexed) {

    List<Object> monitoringParameters = new ArrayList<>();

    List<AbiDefinition.NamedType> namedTypes = eventAbi.getInputs()
      .stream()
      .filter( input -> input.isIndexed() == indexed)
      .collect(Collectors.toList());

    for(int i=0; i<parameters.size();i++) {
      Type parameter = parameters.get(i);
      String name = namedTypes.get(i).getName();
      monitoringParameters.add(Web3ParameterConverter.convertWeb3Type(parameter, name));

    }

    return monitoringParameters;

  }


  private static List<Type> getNonIndexedParametersFromLog(Event event, LogRecord log) {

    if (event.getNonIndexedParameters().isEmpty() ) {
      return Collections.EMPTY_LIST;
    }

    return FunctionReturnDecoder.decode(
      log.getDetails().getData(), event.getNonIndexedParameters());
  }

  private static List<Type> getIndexedParametersFromLog(Event event, LogRecord log) {

    if (event.getIndexedParameters().isEmpty() ) {
      return Collections.EMPTY_LIST;
    }

    List<String> topics =  log.getDetails().getTopics().stream().map( o -> o.toString()).collect(Collectors.toList());
    final List<String> encodedParameters = topics.subList(1, topics.size());

    return IntStream.range(0, encodedParameters.size())
      .mapToObj(i -> FunctionReturnDecoder.decodeIndexedValue(topics.get(i), event.getIndexedParameters().get(i)))
      .collect(Collectors.toList());
  }


  private static String getEventHashFromAbi(AbiDefinition eventAbi) throws EventFromLogException {
    return EventEncoder.encode(getEventFromAbi(eventAbi));
  }

  public static Event getEventFromAbi(AbiDefinition eventAbi) throws EventFromLogException {

    List<AbiDefinition.NamedType> params = eventAbi.getInputs();
    List<org.web3j.abi.TypeReference<?>> typeReferences = new ArrayList<>();

    for(AbiDefinition.NamedType param: params){

      org.web3j.abi.TypeReference type = null;

      try {

        type = org.web3j.abi.TypeReference.makeTypeReference(param.getType(), param.isIndexed(), false);

      } catch (ClassNotFoundException e) {
        String message = "Error making type reference";
        log.error(message + " "+ e.getMessage());
        throw new EventFromLogException(message, e);
      }

      typeReferences.add(type);

    }

    return new Event(eventAbi.getName(), typeReferences);

  }

  public static AbiDefinition getEventAbi(LogRecord log, List<AbiDefinition> eventsAbiDefinition ) throws AbiNotFoundException{

    String eventLogHash = log.getDetails().getTopics().get(0).toString();

    AbiDefinition abiDefinition = eventsAbiDefinition
      .stream()
      .filter( eventAbi ->
      {
        // TODO HANDLE EXCEPTION
        Boolean found = false;
        try {
          found = getEventHashFromAbi(eventAbi).equalsIgnoreCase(eventLogHash);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return found;
      })
      .findFirst()
      .orElse(null);

    if (abiDefinition==null)
      throw new AbiNotFoundException("The ABI for event " + eventLogHash + "was not found in the current ABI Definition ");

    return abiDefinition;

  }


  public static List<AbiDefinition> getAbiDefinitionByType(String type, List<AbiDefinition> abiDefinitionList){

    return abiDefinitionList
      .stream()
      .filter(abi -> abi.getType().equalsIgnoreCase(type))
      .collect(Collectors.toList());
  }


}
