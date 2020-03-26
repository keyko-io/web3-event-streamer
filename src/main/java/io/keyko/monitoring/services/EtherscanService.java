package io.keyko.monitoring.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.keyko.monitoring.exceptions.EtherscanException;
import io.keyko.monitoring.helpers.HttpHelper;
import io.keyko.monitoring.helpers.HttpResponse;
import io.keyko.monitoring.model.ContractData;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.text.StringSubstitutor;
import org.apache.log4j.Logger;
import org.web3j.protocol.core.methods.response.AbiDefinition;

import java.io.IOException;
import java.util.*;

public class EtherscanService {

  private static Logger log = Logger.getLogger(EtherscanService.class);
  private static ObjectMapper mapper = new ObjectMapper();


  public static ContractData getContractData(String getContractAbiUrl, String contractAddress, String apiKey) throws EtherscanException {

    JsonNode contractJson = getContractJson(getContractAbiUrl, contractAddress, apiKey);
    String abiString = contractJson.get(0).get("ABI").asText();
    String contractName = contractJson.get(0).get("ContractName").asText();
    List<AbiDefinition> abiDefinitionList = null;

    try {
      abiDefinitionList = mapper.readValue(abiString, new TypeReference<List<AbiDefinition>>(){});
    } catch (IOException e) {
      String message = "Error parsing ABI for contract: " + contractAddress;
      log.error(message + " "+ e.getMessage());
      throw new EtherscanException(message, e);
    }

    return new ContractData(contractName, abiDefinitionList);
  }

  public static JsonNode getContractJson(String getContractAbiUrl, String contractAddress, String apiKey) throws EtherscanException {

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
    JsonNode jsonResponse = null;
    try {
      jsonResponse = mapper.readTree(body);
    } catch (IOException e) {
      String message = "Error parsing data for contract: " + contractAddress;
      log.error(message + " "+ e.getMessage());
      throw new EtherscanException(message, e);
    }

    if (jsonResponse.get("status").asText().equalsIgnoreCase("0")){
      String message = "Error getting ABI from Address: " + contractAddress  + ". Message: " + jsonResponse.get("message").asText() + ". Result: " + jsonResponse.get("result").asText();
      log.error(message);
      throw new EtherscanException(message);
    }

    return jsonResponse.get("result");

  }

}
