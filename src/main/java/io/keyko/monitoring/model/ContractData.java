package io.keyko.monitoring.model;

import org.web3j.protocol.core.methods.response.AbiDefinition;

import java.util.List;

public class ContractData {

  private String name;
  private List<AbiDefinition> abiDefinitionList;

  public ContractData(String name, List<AbiDefinition> abiDefinitionList) {
    this.name = name;
    this.abiDefinitionList = abiDefinitionList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<AbiDefinition> getAbiDefinitionList() {
    return abiDefinitionList;
  }

  public void setAbiDefinitionList(List<AbiDefinition> abiDefinitionList) {
    this.abiDefinitionList = abiDefinitionList;
  }
}
