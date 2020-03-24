package io.keyko.monitoring.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.keyko.monitoring.helpers.HttpHelper;
import io.keyko.monitoring.helpers.HttpResponse;
import io.keyko.monitoring.model.ContractData;
import org.apache.commons.text.StringSubstitutor;
import org.apache.log4j.Logger;
import org.web3j.protocol.core.methods.response.AbiDefinition;

import java.util.*;

public class EtherscanService {

  private static Logger log = Logger.getLogger(EtherscanService.class);
  private static ObjectMapper mapper = new ObjectMapper();


  public static ContractData getContractData(String getContractAbiUrl, String contractAddress, String apiKey) throws Exception{

    JsonNode contractJson = getContractJson(getContractAbiUrl, contractAddress, apiKey);
    String abiString = contractJson.get(0).get("ABI").asText();
    String contractName = contractJson.get(0).get("ContractName").asText();
    return new ContractData(contractName, mapper.readValue(abiString, new TypeReference<List<AbiDefinition>>(){}));
  }

  public static JsonNode getContractJson(String getContractAbiUrl, String contractAddress, String apiKey) throws Exception{

    Map valuesMap = new HashMap();
    valuesMap.put("address", contractAddress);
    valuesMap.put("apikey", apiKey);

    StringSubstitutor sub = new StringSubstitutor(valuesMap);
    String url = sub.replace(getContractAbiUrl);

    HttpResponse response = HttpHelper.httpClientGet(url);

    if (response.getStatusCode() != 200) {
      String message = "Error getting ABI from Address: " + contractAddress + " Response Code " + response.getStatusCode();
      log.debug(message);
      throw new Exception(message);
    }

    String body = response.getBody();
    JsonNode jsonResponse = mapper.readTree(body);

    if (jsonResponse.get("status").asText().equalsIgnoreCase("0")){
      String message = "Error getting ABI from Address: " + contractAddress  + ". Message: " + jsonResponse.get("message").asText() + ". Result: " + jsonResponse.get("result").asText();
      log.debug(message);
      throw new Exception(message);
    }

    return jsonResponse.get("result");

  }

}
