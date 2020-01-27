package io.keyko.monitoring.model;

public class ValidatorAffiliatedAggregation {
  private long validatorsInValidatorGroup;
  private long percentage;
  private long numberOfValidators;

  public ValidatorAffiliatedAggregation(long validatorsInValidatorGroup, long percentage, long numberOfValidators) {
    this.validatorsInValidatorGroup = validatorsInValidatorGroup;
    this.percentage = percentage;
    this.numberOfValidators = numberOfValidators;
  }

  public long getValidatorsInValidatorGroup() {
    return validatorsInValidatorGroup;
  }

  public void setValidatorsInValidatorGroup(long validatorsInValidatorGroup) {
    this.validatorsInValidatorGroup = validatorsInValidatorGroup;
  }

  public long getPercentage() {
    return percentage;
  }

  public void setPercentage(long percentage) {
    this.percentage = percentage;
  }

  public long getNumberOfValidators() {
    return numberOfValidators;
  }

  public void setNumberOfValidators(long numberOfValidators) {
    this.numberOfValidators = numberOfValidators;
  }
}
