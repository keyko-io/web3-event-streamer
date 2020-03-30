package io.keyko.monitoring.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.keyko.monitoring.cache.InfinispanCacheProvider;
import io.keyko.monitoring.exceptions.EtherscanException;
import io.keyko.monitoring.helpers.HttpHelper;
import io.keyko.monitoring.helpers.HttpResponse;
import io.keyko.monitoring.model.ContractCacheData;
import io.keyko.monitoring.model.ContractData;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.text.StringSubstitutor;
import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.web3j.protocol.core.methods.response.AbiDefinition;

import java.io.IOException;
import java.util.*;

public class EtherscanService {

  private static Logger log = Logger.getLogger(EtherscanService.class);
  private static ObjectMapper mapper = new ObjectMapper();
  private static Cache<String, ContractCacheData> cache = InfinispanCacheProvider.getCache("etherscanContract", ContractCacheData.class);


  public static ContractData getContractData(String getContractAbiUrl, String contractAddress, String apiKey) throws EtherscanException {

    JsonNode contractJson;
    String abiString;
    String contractName;

    if (cache.containsKey(contractAddress)){
      log.debug("Getting data from cache for addres: " + contractAddress);
      ContractCacheData cacheData = cache.get(contractAddress);
      abiString = cacheData.getRawAbi();
      contractName = cacheData.getName();
    }
    else {
      contractJson = getContractJson(getContractAbiUrl, contractAddress, apiKey);
      abiString = contractJson.get("ABI").asText();
      contractName = contractJson.get("ContractName").asText();
    }

    List<AbiDefinition> abiDefinitionList = null;

    try {
      abiDefinitionList = mapper.readValue(abiString, new TypeReference<List<AbiDefinition>>(){});
    } catch (IOException e) {
      String message = "Error parsing ABI for contract: " + contractAddress;
      log.error(message + " "+ e.getMessage());
      throw new EtherscanException(message, e);
    }

    if (!cache.containsKey(contractAddress)){
      cache.put(contractAddress, new ContractCacheData(contractName, abiString));
    }

    return new ContractData(contractName, abiDefinitionList);
  }

  public static JsonNode getContractJson(String getContractAbiUrl, String contractAddress, String apiKey) throws EtherscanException {

    log.debug("Getting contract Data form Etherscan for address: " + contractAddress);
    Map valuesMap = new HashMap();
    valuesMap.put("address", contractAddress);
    valuesMap.put("apikey", apiKey);

    StringSubstitutor sub = new StringSubstitutor(valuesMap);
    String url = sub.replace(getContractAbiUrl);

    HttpResponse response = null;
    try {
      response = HttpHelper.httpClientGet(url);
    } catch (HttpException e) {
      String message = "Error getting Contract data for: " + contractAddress + " .Response Code " + response.getStatusCode();
      log.error(message + " "+ e.getMessage());
      throw new EtherscanException(message, e);
    }

    if (response.getStatusCode() != 200) {
      String message = "Error getting ABI from Address: " + contractAddress + " Response Code " + response.getStatusCode();
      log.error(message);
      throw new EtherscanException(message);
    }

    String body = response.getBody();
    JsonNode jsonNode = null;

    try {
      jsonNode = mapper.readTree(body);
    } catch (IOException e) {
      String message = "Error parsing data for contract: " + contractAddress;
      log.error(message + " "+ e.getMessage());
      throw new EtherscanException(message, e);
    }

    if (jsonNode.get("status").asText().equalsIgnoreCase("0")){
      String message = "Error getting ABI from Address: " + contractAddress  + ". Message: " + jsonNode.get("message").asText() + ". Result: " + jsonNode.get("result").asText();
      log.error(message);
      throw new EtherscanException(message);
    }

    return jsonNode.get("result").get(0);

  }

}
