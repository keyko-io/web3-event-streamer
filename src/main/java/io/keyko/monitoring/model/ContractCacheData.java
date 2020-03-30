package io.keyko.monitoring.model;

import java.io.Serializable;

public class ContractCacheData implements Serializable {

  private String name;
  private String rawAbi;

  public ContractCacheData(String name, String rawAbi){
    this.name = name;
    this.rawAbi = rawAbi;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRawAbi() {
    return rawAbi;
  }

  public void setRawAbi(String rawAbi) {
    this.rawAbi = rawAbi;
  }
}
