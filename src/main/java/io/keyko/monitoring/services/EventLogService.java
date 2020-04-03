package io.keyko.monitoring.services;

import io.keyko.monitoring.exceptions.AbiNotFoundException;
import io.keyko.monitoring.exceptions.EtherscanException;
import io.keyko.monitoring.exceptions.EventFromLogException;
import io.keyko.monitoring.exceptions.TypeConversionException;
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

  public static EventRecord getEventFromLog(LogRecord log, String getContractAbiUrl, String apiKey) throws EventFromLogException, TypeConversionException {

    String contractAddress = log.getAddress();
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



  private static ContractEventStatus getEventStatus(LogStatus logStatus){
    switch(logStatus){
      case CONFIRMED: return ContractEventStatus.CONFIRMED;
      case UNCONFIRMED: return ContractEventStatus.UNCONFIRMED;
      case INVALIDATED: return ContractEventStatus.INVALIDATED;
    }

    return ContractEventStatus.UNCONFIRMED;
  }

  private static EventRecord generateEventRecord(String contractName, String eventName, String eventHash, List<Object> indexedParameters, List<Object> nonIndexedParameters, LogRecord logRecord){

    EventRecord eventRecord = new EventRecord();
    eventRecord.setRetries(logRecord.getRetries());
    eventRecord.setId(logRecord.getId());

    eventRecord.setContractName(contractName);
    eventRecord.setBlockNumber(logRecord.getBlockNumber());
    eventRecord.setName(eventName);
    eventRecord.setStatus(getEventStatus(logRecord.getStatus()));
    eventRecord.setBlockHash(logRecord.getBlockHash());
    eventRecord.setAddress(logRecord.getAddress());
    eventRecord.setEventSpecificationSignature(eventHash);
    eventRecord.setFilterId(eventName);
    eventRecord.setLogIndex(logRecord.getLogIndex());
    eventRecord.setNetworkName(logRecord.getNetworkName());
    eventRecord.setNodeName(logRecord.getNodeName());
    eventRecord.setTransactionHash(logRecord.getTransactionHash());

    eventRecord.setIndexedParameters(indexedParameters);
    eventRecord.setNonIndexedParameters(nonIndexedParameters);

    if (log.isDebugEnabled())
      printEventRecord(eventRecord);

    return eventRecord;

  }

  private static void printEventRecord(EventRecord eventRecord){

    log.debug("Generated EventRecord: ");
    log.debug("ID: " + eventRecord.getId());
    log.debug("Contract name: " + eventRecord.getContractName());
    log.debug("Block number : " + eventRecord.getBlockNumber());
    log.debug("Name: " + eventRecord.getName());
    log.debug("Status: " + eventRecord.getStatus());
    log.debug("Block haash : " + eventRecord.getBlockHash());
    log.debug("Address: " + eventRecord.getAddress());
    log.debug("EventSpecificationSignature: " + eventRecord.getEventSpecificationSignature());
    log.debug("FilterId: " + eventRecord.getFilterId());
    log.debug("LogIndex: " + eventRecord.getLogIndex());
    log.debug("NetworkName: " + eventRecord.getNetworkName());
    log.debug("NodeName: " + eventRecord.getNodeName());
    log.debug("TransactionHash: " + eventRecord.getTransactionHash());

    log.debug("Indexed Parameters: ");
    printParameters(eventRecord.getIndexedParameters());

    log.debug("Non Indexed Parameters: ");
    printParameters(eventRecord.getNonIndexedParameters());

  }

  private static void printParameters(List<Object> parameters){

    for(Object o:parameters){
      if (o instanceof NumberParameter) {
        NumberParameter np = (NumberParameter) o;
        log.debug("Parameter Name: " + np.getName());
        log.debug("Parameter Type: " +  np.getType());
        log.debug("Parameter Value: " + np.getValue());
        log.debug("Parameter Number Value: " + np.getNumberValue());
      }
      else if (o instanceof StringParameter) {
        StringParameter sp = (StringParameter) o;
        log.debug("Parameter Name: " + sp.getName());
        log.debug("Parameter Type: " +  sp.getType());
        log.debug("Parameter Value: " + sp.getValue());
      }
    }

  }

  private static List<Object> web3ToMonitoringType(List<Type> parameters,  AbiDefinition eventAbi, Boolean indexed) throws TypeConversionException {

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
      log.getData(), event.getNonIndexedParameters());
  }

  private static List<Type> getIndexedParametersFromLog(Event event, LogRecord log) {

    if (event.getIndexedParameters().isEmpty() ) {
      return Collections.EMPTY_LIST;
    }

    List<String> topics =  log.getTopics().stream().map( o -> o.toString()).collect(Collectors.toList());
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

    String eventLogHash = log.getTopics().get(0);

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
